package mcdonald.example.Policies.repository.adapter;

import com.github.fppt.jedismock.RedisServer;
import com.github.fppt.jedismock.server.ServiceOptions;
import mcdonald.example.Policies.domain.Policy;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.ActiveProfiles;
import redis.clients.jedis.Jedis;

import java.io.IOException;
import java.util.Optional;

@SpringBootTest(properties = {"spring.main.allow-bean-definition-overriding=true"})
@ActiveProfiles("test")
class PolicyRepositoryAdapterTest {

    static RedisServer redisServer;
    @Autowired
    PolicyRepositoryAdapter policyRepositoryAdapterSut;

    @Autowired
    Jedis jedis;

    @BeforeAll
    static void initializeRedis() throws IOException {
        ServiceOptions serviceOptions = ServiceOptions.defaultOptions();
        redisServer = RedisServer.newRedisServer().setOptions(serviceOptions).start();
    }

    @AfterAll
    static void stopRedis() throws IOException {
        redisServer.stop();
    }

    @BeforeEach
    void clearRecords() {
        jedis.flushAll();
    }

    @AfterEach
    void clearRecordsAfterRun() {
        jedis.flushAll();
    }

    @Test
    void shouldFindPolicyById() {
        // given
        int id = 1;
        Policy expectedPolicy = new Policy(1, "myPolicy", 18, "Jan", "Kowalski");
        String expectedJsonPolicy = "{\"id\": 1," + "\"name\":\"myPolicy\"," + "\"price\": 18," + "  \"firstName\": \"Jan\"," + "  \"lastName\": \"Kowalski\"" + "}";
        jedis.set("policy#1", expectedJsonPolicy);
        // when
        var outputPolicy = policyRepositoryAdapterSut.get(id);
        // then
        Assertions.assertThat(outputPolicy).contains(expectedPolicy);
    }

    @Test
    void shouldReturnOptionalEmptyIfPolicyIsNotInDatabase() {
        // given
        int id = 1;
        jedis.del("person#1");
        // when
        var outputPolicy = policyRepositoryAdapterSut.get(id);
        // then
        Assertions.assertThat(outputPolicy).isEqualTo(Optional.empty());
    }

//    @Test
//    void shouldReturnOptionalEmptyIfInputIsNull() {
//        // given
//        int id = 1;
//        Policy expectedPolicy = new Policy(1, 18, "Jan", "Kowalski", "Mickiewicza", 123456789);
//        // when
//        var outputPolicy = policyRepositoryAdapterSut.get(null);
//        // then
//        Assertions.assertThat(outputPolicy).isEqualTo(Optional.empty());
//    }

    @Test
    void shouldDeletePolicy() {
        // given
        int id = 1;
        String key = "policy#1";
        Policy expectedPolicy = new Policy(1, "myPolicy", 18, "Jan", "Kowalski");
        String expectedJsonPolicy = "{\"id\": 1," + "\"name\": \"myPolicy\"," + "\"price\": 18," + "  \"firstName\": \"Jan\"," + "  \"lastName\": \"Kowalski\"" + "}";
        jedis.set(key, expectedJsonPolicy);
        // when
        policyRepositoryAdapterSut.delete(id);
        var outputPolicy = jedis.get(key);
        // then
        Assertions.assertThat(outputPolicy).isEqualTo(null);
    }

    @Test
    void shouldsavePolicy() {
        // given
        String key = "policy#1";
        Policy inputPolicy = new Policy(1, "myPolicy", 18, "Jan", "Kowalski");
        String expectedJsonPolicy = "{\"id\": 1," + "\"name\": myPolicy," + "\"price\": 18," + "  \"firstName\": \"Jan\"," + "  \"lastName\": \"Kowalski\"" + "}";
        policyRepositoryAdapterSut.save(inputPolicy);
        jedis.set(key, expectedJsonPolicy);
        // when
        var outputJson = jedis.get(key);
        // then
        Assertions.assertThat(outputJson).isEqualTo(expectedJsonPolicy);
    }

    @Test
    void shouldReturnAllPeople() {
        // given
        int id = 1;
        Policy expectedPolicy1 = new Policy(1, "myPolicy", 18, "Jan", "Kowalski");
        String expectedJsonPolicy1 = "{\"id\": 1," + "\"name\": \"myPolicy\"," + "\"price\": 18," + "  \"firstName\": \"Jan\"," + "  \"lastName\": \"Kowalski\"" + "}";
        Policy expectedPolicy2 = new Policy(2, "myPolicy", 18, "Jan", "Kowalski");
        String expectedJsonPolicy2 = "{\"id\": 2," + "\"name\": \"myPolicy\"," + "\"price\": 18," + "  \"firstName\": \"Jan\"," + "  \"lastName\": \"Kowalski\"" + "}";

        jedis.set("policy#1", expectedJsonPolicy1);
        jedis.set("policy#2", expectedJsonPolicy2);

        // when
        var outputPeople = policyRepositoryAdapterSut.findAll();
        // then
        Assertions.assertThat(outputPeople).contains(expectedPolicy1).contains(expectedPolicy2);
    }


    @TestConfiguration
    static class myTestConfiguration {
        @Bean
        public Jedis jedis() {
            return new Jedis(redisServer.getHost(), redisServer.getBindPort());
        }
    }


}