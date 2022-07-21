package mcdonald.example.Policies.repository.adapter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import mcdonald.example.Policies.domain.Entity;
import org.springframework.stereotype.Repository;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.params.ScanParams;
import redis.clients.jedis.resps.ScanResult;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;


@Repository
public abstract class AbstractRepositoryAdapter<T extends Entity> {
    private final Jedis jedis;
    private final ObjectMapper objectMapper;

    public AbstractRepositoryAdapter(Jedis jedis, ObjectMapper objectMapper) {
        this.jedis = jedis;
        this.objectMapper = objectMapper;
    }

    public T save(T entity) {
        String jsonObject = null;
        try {
            jsonObject = objectMapper.writeValueAsString(entity);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        jedis.set(getPrefix() + entity.getId(), jsonObject);
        return entity;
    }

    public void delete(int id) {
        jedis.del(getPrefix() + id);
    }

    protected abstract Class<T> getEntityClass();

    public Optional<T> get(int id) {
        String jsonObject = jedis.get(getPrefix() + id);
        if (jsonObject == null) return Optional.empty();
        if (jsonObject.equals("nil")) {
            return Optional.empty();
        }
        try {
            return Optional.of(objectMapper.readValue(jsonObject, getEntityClass()));
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }


    protected abstract String getPrefix();

    public Collection<T> findAll() {
        Collection<String> entitiesKeys = new ArrayList<>();
        ScanParams scanParams = new ScanParams().match(getPrefix() + "*");
        String cursor = ScanParams.SCAN_POINTER_START;
        do {
            ScanResult<String> scanResult = jedis.scan(cursor, scanParams);
            entitiesKeys.addAll(scanResult.getResult().stream().map(jedis::get).collect(Collectors.toList()));
            cursor = scanResult.getCursor();
        } while (!cursor.equals("0"));
        return entitiesKeys.stream().map(e -> {
            try {
                return objectMapper.readValue(e, getEntityClass());
            } catch (JsonProcessingException ex) {
                throw new RuntimeException(ex);
            }
        }).collect(Collectors.toList());
    }
}
