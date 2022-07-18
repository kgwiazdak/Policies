package mcdonald.example.Policies.service;

import mcdonald.example.Policies.domain.Person;

import java.util.Collection;

public interface PersonService {
    Person add(Person person);
    void update(Person person);
    void delete(int id);
    Person get(int id);

    Collection<Person> findAll();
}
