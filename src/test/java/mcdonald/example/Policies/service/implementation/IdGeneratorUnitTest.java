package mcdonald.example.Policies.service.implementation;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import redis.clients.jedis.Jedis;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class IdGeneratorUnitTest {
    @InjectMocks
    private IdGenerator idGeneratorSut;
    @Mock
    private Jedis jedis;

    @Test
    void shouldGenerateNewPolicyID() {
        // given
        String key = "maxPolicyId";
        when(jedis.incr(key)).thenReturn(2L);
        // when && then
        Assertions.assertThat(idGeneratorSut.generatePolicyID()).isEqualTo(2);
    }

    @Test
    void shouldGenerateNewPersonID() {
        // given
        String key = "maxPersonId";
        when(jedis.incr(key)).thenReturn(2L);
        // when && then
        Assertions.assertThat(idGeneratorSut.generatePersonID()).isEqualTo(2);
    }
}