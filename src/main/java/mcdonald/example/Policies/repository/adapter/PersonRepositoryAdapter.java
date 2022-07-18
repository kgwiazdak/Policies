package mcdonald.example.Policies.repository.adapter;

import com.fasterxml.jackson.databind.ObjectMapper;
import mcdonald.example.Policies.domain.Person;
import mcdonald.example.Policies.domain.Entity;
import mcdonald.example.Policies.repository.PersonRepositoryPort;
import org.springframework.stereotype.Repository;
import redis.clients.jedis.Jedis;

import java.util.Optional;

@Repository
public class PersonRepositoryAdapter extends AbstractRepositoryAdapter<Person> implements PersonRepositoryPort{


    public PersonRepositoryAdapter(Jedis jedis, ObjectMapper objectMapper) {
        super(jedis, objectMapper);
    }


    @Override
    protected Class<Person> getEntityClass() {
        return Person.class;
    }

    @Override
    protected String getPrefix() {
        return "person#";
    }


}
