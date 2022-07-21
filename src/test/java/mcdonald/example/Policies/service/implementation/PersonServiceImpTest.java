package mcdonald.example.Policies.service.implementation;

import mcdonald.example.Policies.domain.Person;
import mcdonald.example.Policies.repository.PersonRepositoryPort;
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
class PersonServiceImpTest {

    @InjectMocks
    private PersonServiceImp personServiceImpsSut;
    @Mock
    private PersonRepositoryPort personRepositoryPort;
    @Mock
    private IdGenerator idGenerator;


    @Test
    void shouldThrowAnExceptionWhileAddingIfPersonIsInDatabase() {
        // given
        Person person = new Person(0, 18, "Jan", "Kowalski", "Mickiewicza", 123456789);
        when(idGenerator.generatePersonID()).thenReturn(1);
        when(personRepositoryPort.get(1)).thenReturn(Optional.of(person));
        // when & then
        Assertions.assertThatThrownBy(() -> personServiceImpsSut.add(person)).isInstanceOf(DataAlreadyInDatabase.class);
    }

    @Test
    void shouldSaveAPersonIfDoesNotExistInDatabase() {
        // given
        Person personInput = new Person(0, 18, "Jan", "Kowalski", "Mickiewicza", 123456789);
        Person personExpectedOutput = new Person(1, 18, "Jan", "Kowalski", "Mickiewicza", 123456789);

        when(idGenerator.generatePersonID()).thenReturn(1);
        when(personRepositoryPort.get(1)).thenReturn(Optional.empty());
        when(personRepositoryPort.save(personInput)).thenReturn(personExpectedOutput);
        // when
        Person personOutput = personServiceImpsSut.add(personInput);
        //then
        Assertions.assertThat(personOutput).isEqualTo(personExpectedOutput);
    }

    @Test
    void shouldThrowAnExceptionWhileUpdatingIfPersonDoesNotExistInDatabase() {
        // given
        Person person = new Person(0, 18, "Jan", "Kowalski", "Mickiewicza", 123456789);
        when(personRepositoryPort.get(0)).thenReturn(Optional.empty());
        // when & then
        Assertions.assertThatThrownBy(() -> personServiceImpsSut.update(person)).isInstanceOf(DataNotInDatabase.class);
    }

    @Test
    void shouldUpdatePersonIfPersonIsInDatabase() {
        // given
        Person personOld = new Person(0, 18, "Jan", "Kowalski", "Mickiewicza", 123456789);
        Person personNew = new Person(0, 18, "Jan", "Kowalski", "Mickiewicza", 123456789);

        when(personRepositoryPort.get(0)).thenReturn(Optional.of(personOld));
        when(personRepositoryPort.save(personNew)).thenReturn(personNew);
        // when && then
        personServiceImpsSut.update(personNew);
    }

    @Test
    void shouldThrowAnExceptionWhileDeletingIfPersonDoesNotExistInDatabase() {
        //given
        int id = 0;
        when(personRepositoryPort.get(0)).thenReturn(Optional.empty());
        // when && then
        Assertions.assertThatThrownBy(() -> personServiceImpsSut.delete(id)).isInstanceOf(DataNotInDatabase.class);
    }

    @Test
    void shouldDeletePersonIfPersonIsInDatabase() {
        // given
        int id = 0;
        Person person = new Person(0, 18, "Jan", "Kowalski", "Mickiewicza", 123456789);
        when(personRepositoryPort.get(0)).thenReturn(Optional.of(person));
        // when
        personServiceImpsSut.delete(id);
        // then
        verify(personRepositoryPort).delete(id);
    }

    @Test
    void shouldThrowAnExceptionWhileDeletingIfPersonDeosNotExistInDatabase() {
        // given
        int id = 0;
        when(personRepositoryPort.get(0)).thenReturn(Optional.empty());
        // when && then
        Assertions.assertThatThrownBy(() -> personServiceImpsSut.delete(id)).isInstanceOf(DataNotInDatabase.class);
    }

    @Test
    void shouldThrowAnExceptionWhileGetingIfPersonDeosNotExistInDatabase() {
        // given
        int id = 0;
        when(personRepositoryPort.get(0)).thenReturn(Optional.empty());
        // when && then
        Assertions.assertThatThrownBy(() -> personServiceImpsSut.get(id)).isInstanceOf(DataNotInDatabase.class);
    }

    @Test
    void shouldGetPersonIfPersonIsInDatabase() {
        // given
        int id = 0;
        Person expectedOutputperson = new Person(0, 18, "Jan", "Kowalski", "Mickiewicza", 123456789);
        when(personRepositoryPort.get(id)).thenReturn(Optional.of(expectedOutputperson));
        // when
        Person outputPerson = personServiceImpsSut.get(id);
        // then
        Assertions.assertThat(outputPerson).isEqualTo(expectedOutputperson);
    }


    @Test
    void shouldReturnAllPeopleInDatabase() {
        // given
        Person person1 = new Person(0, 18, "Jan", "Kowalski", "Mickiewicza", 123456789);
        when(personRepositoryPort.findAll()).thenReturn(List.of(person1));
        // when
        Collection<Person> listOfPeople = personServiceImpsSut.findAll();
        // then
        Assertions.assertThat(List.of(person1)).isEqualTo(listOfPeople);
    }
}