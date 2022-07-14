package mcdonald.example.Policies.repository;

import mcdonald.example.Policies.domain.Person;

import java.util.Optional;

public interface PersonRepositoryPort {
    Person save(Person person);
    void delete(int id);
    Optional<Person> get(int id);
}
