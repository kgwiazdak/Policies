package mcdonald.example.Policies.repository.adapter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import mcdonald.example.Policies.domain.Person;
import mcdonald.example.Policies.repository.PersonRepositoryPort;
import org.springframework.stereotype.Repository;
import redis.clients.jedis.Jedis;

import java.util.Optional;

@Repository
public class PersonRepositoryAdapter implements PersonRepositoryPort {

    private final Jedis jedis;
    private final ObjectMapper objectMapper;

    public PersonRepositoryAdapter(Jedis jedis, ObjectMapper objectMapper){
        this.jedis = jedis;
        this.objectMapper = objectMapper;
    }

    @Override
    public Person save(Person person)  {
        String jsonObject = null;
        try {
            jsonObject = objectMapper.writeValueAsString(person);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        jedis.set("l/"+ person.getId(), jsonObject);
        return person;
    }

    @Override
    public void delete(int id){
        jedis.del("l/"+ id);
    }

    @Override
    public Optional<Person> get(int id)  {
        String jsonObject = jedis.get("l/"+ id);
        if (jsonObject==null) return Optional.empty();
        if (jsonObject.equals("nil")) {
            return Optional.empty();
        }
        try {
            return Optional.of(objectMapper.readValue(jsonObject, Person.class));
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
