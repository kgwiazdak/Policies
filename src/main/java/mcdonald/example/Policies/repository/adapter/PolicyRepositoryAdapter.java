package mcdonald.example.Policies.repository.adapter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import mcdonald.example.Policies.domain.Policy;
import mcdonald.example.Policies.repository.PolicyRepositoryPort;
import org.springframework.stereotype.Repository;
import redis.clients.jedis.Jedis;

import java.util.Optional;

@Repository
class PolicyRepositoryAdapter implements PolicyRepositoryPort {
    private final Jedis jedis;
    private final ObjectMapper objectMapper;

    public PolicyRepositoryAdapter(Jedis jedis, ObjectMapper objectMapper){
        this.jedis = jedis;
        this.objectMapper = objectMapper;
    }

    @Override
    public Policy save(Policy policy)  {
        String jsonObject = null;
        try {
            jsonObject = objectMapper.writeValueAsString(policy);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        jedis.set("p/"+ policy.getId(), jsonObject);
        return policy;
    }

    @Override
    public void delete(int id){
        jedis.del("p/"+ id);
    }

    @Override
    public Optional<Policy> get(int id)  {
        String jsonObject = jedis.get("p/"+ id);
        if (jsonObject==null) return Optional.empty();
        if (jsonObject.equals("nil")) {
            return Optional.empty();
        }
        try {
            return Optional.of(objectMapper.readValue(jsonObject, Policy.class));
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
