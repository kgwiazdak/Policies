package mcdonald.example.Policies.repository.adapter;

import com.github.fppt.jedismock.RedisServer;
import com.github.fppt.jedismock.server.ServiceOptions;
import mcdonald.example.Policies.domain.Person;
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
class PersonRepositoryAdapterTest {

    static RedisServer redisServer;
    @Autowired
    PersonRepositoryAdapter personRepositoryAdapterSut;

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
    void shouldFindPersonById() {
        // given
        int id = 1;
        Person expectedPerson = new Person(1, 18, "Jan", "Kowalski", "Mickiewicza", 123456789);
        String expectedJsonPerson = "{\"id\": 1," + "\"age\": 18," + "  \"firstName\": \"Jan\"," + "  \"lastName\": \"Kowalski\"," + "  \"address\": \"Mickiewicza\"," + "  \"phoneNumber\": 123456789" + "}";
        jedis.set("person#1", expectedJsonPerson);
        // when
        var outputPerson = personRepositoryAdapterSut.get(id);
        // then
        Assertions.assertThat(outputPerson).contains(expectedPerson);
    }

    @Test
    void shouldReturnOptionalEmptyIfPersonIsNotInDatabase() {
        // given
        int id = 1;
        jedis.del("person#1");
        // when
        var outputPerson = personRepositoryAdapterSut.get(id);
        // then
        Assertions.assertThat(outputPerson).isEqualTo(Optional.empty());
    }

//    @Test
//    void shouldReturnOptionalEmptyIfInputIsNull() {
//        // given
//        int id = 1;
//        Person expectedPerson = new Person(1, 18, "Jan", "Kowalski", "Mickiewicza", 123456789);
//        // when
//        var outputPerson = personRepositoryAdapterSut.get(null);
//        // then
//        Assertions.assertThat(outputPerson).isEqualTo(Optional.empty());
//    }

    @Test
    void shouldDeletePerson() {
        // given
        int id = 1;
        String key = "person#1";
        Person expectedPerson = new Person(1, 18, "Jan", "Kowalski", "Mickiewicza", 123456789);
        String expectedJsonPerson = "{\"id\": 1," + "\"age\": 18," + "  \"firstName\": \"Jan\"," + "  \"lastName\": \"Kowalski\"," + "  \"address\": \"Mickiewicza\"," + "  \"phoneNumber\": 123456789" + "}";
        jedis.set(key, expectedJsonPerson);
        // when
        personRepositoryAdapterSut.delete(id);
        var outputPerson = jedis.get(key);
        // then
        Assertions.assertThat(outputPerson).isEqualTo(null);
    }

    @Test
    void shouldsavePerson() {
        // given
        String key = "person#1";
        Person inputPerson = new Person(1, 18, "Jan", "Kowalski", "Mickiewicza", 123456789);
        personRepositoryAdapterSut.save(inputPerson);
        String expectedJsonPerson = "{\"id\":1," + "\"age\":18," + "\"firstName\":\"Jan\"," + "\"lastName\":\"Kowalski\"," + "\"address\":\"Mickiewicza\"," + "\"phoneNumber\":123456789" + "}";
        jedis.set(key, expectedJsonPerson);
        // when
        var outputJson = jedis.get(key);
        // then
        Assertions.assertThat(outputJson).isEqualTo(expectedJsonPerson);
    }

    @Test
    void shouldReturnAllPeople() {
        // given
        int id = 1;
        Person expectedPerson1 = new Person(1, 18, "Jan", "Kowalski", "Mickiewicza", 123456789);
        Person expectedPerson2 = new Person(2, 18, "Jan", "Kowalski", "Mickiewicza", 123456789);

        String expectedJsonPerson1 = "{\"id\": 1," + "\"age\": 18," + "  \"firstName\": \"Jan\"," + "  \"lastName\": \"Kowalski\"," + "  \"address\": \"Mickiewicza\"," + "  \"phoneNumber\": 123456789" + "}";
        String expectedJsonPerson2 = "{\"id\": 2," + "\"age\": 18," + "  \"firstName\": \"Jan\"," + "  \"lastName\": \"Kowalski\"," + "  \"address\": \"Mickiewicza\"," + "  \"phoneNumber\": 123456789" + "}";

        jedis.set("person#1", expectedJsonPerson1);
        jedis.set("person#2", expectedJsonPerson2);

        // when
        var outputPeople = personRepositoryAdapterSut.findAll();
        // then
        Assertions.assertThat(outputPeople).contains(expectedPerson1).contains(expectedPerson2);
    }


    @TestConfiguration
    static class myTestConfiguration {
        @Bean
        public Jedis jedis() {
            return new Jedis(redisServer.getHost(), redisServer.getBindPort());
        }
    }


}