package mcdonald.example.Policies.repository.adapter;


import com.github.fppt.jedismock.RedisServer;
import com.github.fppt.jedismock.server.ServiceOptions;
import mcdonald.example.Policies.service.implementation.IdGenerator;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.ActiveProfiles;
import redis.clients.jedis.Jedis;

import java.io.IOException;

@SpringBootTest(properties = {"spring.main.allow-bean-definition-overriding=true"})
@ActiveProfiles("test")
public class IDGeneratorTest {
    static RedisServer redisServer;

    @Autowired
    IdGenerator idGeneratorSut;


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
    void shouldReturnIfThereIsNoDataInDatabase() {
        // given
        // when

        int generatedPersonId = idGeneratorSut.generatePersonID();
        // then
        Assertions.assertThat(generatedPersonId).isEqualTo(1);
    }

    @Test
    void shouldReturnIncrementedPersonID() {
        // given
        jedis.set("maxPersonId", "5");
        // when
        int generatedPersonId = idGeneratorSut.generatePersonID();
        // then
        Assertions.assertThat(generatedPersonId).isEqualTo(6);
    }

    @TestConfiguration
    static class myTestConfiguration {
        @Bean
        public Jedis jedis() {
            return new Jedis(redisServer.getHost(), redisServer.getBindPort());
        }
    }
}
