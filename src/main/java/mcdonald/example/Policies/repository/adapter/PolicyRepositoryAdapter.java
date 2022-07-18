package mcdonald.example.Policies.repository.adapter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import mcdonald.example.Policies.domain.Entity;
import mcdonald.example.Policies.domain.Person;
import mcdonald.example.Policies.domain.Policy;
import mcdonald.example.Policies.repository.PolicyRepositoryPort;
import org.springframework.stereotype.Repository;
import redis.clients.jedis.Jedis;

import java.util.Optional;

@Repository
class PolicyRepositoryAdapter extends AbstractRepositoryAdapter<Policy> implements PolicyRepositoryPort {
    public PolicyRepositoryAdapter(Jedis jedis, ObjectMapper objectMapper) {
        super(jedis, objectMapper);
    }


    @Override
    protected Class<Policy> getEntityClass() {
        return Policy.class;
    }

    @Override
    protected String getPrefix() {
        return "policy\\";
    }

}
