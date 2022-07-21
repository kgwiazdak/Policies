package mcdonald.example.Policies.service.implementation;


import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;

@Service
public class IdGenerator {
    private final Jedis jedis;

    public IdGenerator(Jedis jedis) {
        this.jedis = jedis;
    }

    public int generatePolicyID() {
        return (int) jedis.incr("maxPolicyId");
    }

    public int generatePersonID() {
        return (int) jedis.incr("maxPersonId");
    }

}
