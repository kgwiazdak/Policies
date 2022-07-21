package mcdonald.example.Policies.service.implementation;

import mcdonald.example.Policies.domain.Policy;
import mcdonald.example.Policies.repository.PolicyRepositoryPort;
import mcdonald.example.Policies.service.exceptions.DataAlreadyInDatabase;
import mcdonald.example.Policies.service.exceptions.DataNotInDatabase;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PolicyServiceImpTest {

    @InjectMocks
    private PolicyServiceImp policyServiceImpsSut;
    @Mock
    private PolicyRepositoryPort policyRepositoryPort;
    @Mock
    private IdGenerator idGenerator;


    @Test
    void shouldThrowAnExceptionWhileAddingIfPolicyIsInDatabase() {
        // given
        Policy policy = new Policy(0, "NameOfPolicy", 180, "Jan", "Kowalski");
        when(idGenerator.generatePolicyID()).thenReturn(1);
        when(policyRepositoryPort.get(1)).thenReturn(Optional.of(policy));
        // when & then
        Assertions.assertThatThrownBy(() -> policyServiceImpsSut.add(policy)).isInstanceOf(DataAlreadyInDatabase.class);
    }

    @Test
    void shouldSaveAPolicyIfDoesNotExistInDatabase() {
        // given
        Policy policyInput = new Policy(0, "NameOfPolicy", 180, "Jan", "Kowalski");
        Policy policyExpectedOutput = new Policy(1, "NameOfPolicy", 180, "Jan", "Kowalski");

        when(idGenerator.generatePolicyID()).thenReturn(1);
        when(policyRepositoryPort.get(1)).thenReturn(Optional.empty());
        when(policyRepositoryPort.save(policyInput)).thenReturn(policyExpectedOutput);
        // when
        Policy policyOutput = policyServiceImpsSut.add(policyInput);
        //then
        Assertions.assertThat(policyOutput).isEqualTo(policyExpectedOutput);
    }

    @Test
    void shouldThrowAnExceptionWhileUpdatingIfPolicyDoesNotExistInDatabase() {
        // given
        Policy policy = new Policy(0, "NameOfPolicy", 180, "Jan", "Kowalski");
        when(policyRepositoryPort.get(0)).thenReturn(Optional.empty());
        // when & then
        Assertions.assertThatThrownBy(() -> policyServiceImpsSut.update(policy)).isInstanceOf(DataNotInDatabase.class);
    }

    @Test
    void shouldUpdatePolicyIfPolicyIsInDatabase() {
        // given
        Policy policyOld = new Policy(0, "NameOfPolicy", 180, "Jan", "Kowalski");
        Policy policyNew = new Policy(0, "NameOfPolicy", 180, "Jan", "Kowalski");

        when(policyRepositoryPort.get(0)).thenReturn(Optional.of(policyOld));
        when(policyRepositoryPort.save(policyNew)).thenReturn(policyNew);
        // when && then
        policyServiceImpsSut.update(policyNew);
    }

    @Test
    void shouldThrowAnExceptionWhileDeletingIfPolicyDoesNotExistInDatabase() {
        //given
        int id = 0;
        when(policyRepositoryPort.get(0)).thenReturn(Optional.empty());
        // when && then
        Assertions.assertThatThrownBy(() -> policyServiceImpsSut.delete(id)).isInstanceOf(DataNotInDatabase.class);
    }

    @Test
    void shouldDeletePolicyIfPolicyIsInDatabase() {
        // given
        int id = 0;
        Policy policy = new Policy(0, "NameOfPolicy", 180, "Jan", "Kowalski");
        when(policyRepositoryPort.get(0)).thenReturn(Optional.of(policy));
        // when
        policyServiceImpsSut.delete(id);
        // then
        verify(policyRepositoryPort).delete(id);
    }

    @Test
    void shouldThrowAnExceptionIfPolicyDeosNotExistInDatabase() {
        // given
        int id = 0;
        when(policyRepositoryPort.get(0)).thenReturn(Optional.empty());
        // when && then
        Assertions.assertThatThrownBy(() -> policyServiceImpsSut.delete(id)).isInstanceOf(DataNotInDatabase.class);
    }

    @Test
    void shouldGetPolicyIfPolicyIsInDatabase() {
        // given
        int id = 0;
        Policy expectedOutputpolicy = new Policy(0, "NameOfPolicy", 180, "Jan", "Kowalski");
        when(policyRepositoryPort.get(id)).thenReturn(Optional.of(expectedOutputpolicy));
        // when
        Policy outputPolicy = policyServiceImpsSut.get(id);
        // then
        Assertions.assertThat(outputPolicy).isEqualTo(expectedOutputpolicy);
    }

    @Test
    void shouldThrowAnExceptionWhileGetingIfPolicyDeosNotExistInDatabase() {
        // given
        int id = 0;
        when(policyRepositoryPort.get(0)).thenReturn(Optional.empty());
        // when && then
        Assertions.assertThatThrownBy(() -> policyServiceImpsSut.get(id)).isInstanceOf(DataNotInDatabase.class);
    }


    @Test
    void shouldReturnAllPeopleInDatabase() {
        // given
        Policy policy1 = new Policy(0, "NameOfPolicy", 180, "Jan", "Kowalski");
        when(policyRepositoryPort.findAll()).thenReturn(List.of(policy1));
        // when
        Collection<Policy> listOfPeople = policyServiceImpsSut.findAll();
        // then
        Assertions.assertThat(List.of(policy1)).isEqualTo(listOfPeople);
    }
}