package mcdonald.example.Policies.service.implementation;

import mcdonald.example.Policies.domain.Person;
import mcdonald.example.Policies.repository.PersonRepositoryPort;
import mcdonald.example.Policies.service.PersonService;
import mcdonald.example.Policies.service.exceptions.DataAlreadyInDatabase;
import mcdonald.example.Policies.service.exceptions.DataNotInDatabase;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class PersonServiceImp implements PersonService {
    private final PersonRepositoryPort personRepositoryPort;
    private final IdGenerator idGenerator;

    public PersonServiceImp(PersonRepositoryPort personRepositoryPort, IdGenerator idGenerator) {
        this.personRepositoryPort = personRepositoryPort;
        this.idGenerator = idGenerator;
    }

    public Person add(Person person)  {
        if (personRepositoryPort.get(person.getId()).isPresent()) {
            throw new DataAlreadyInDatabase(
                "person", person.getId()
            );
        }
        return personRepositoryPort.save(person);
    }
    public void update(Person person) {
        if (personRepositoryPort.get(person.getId()).isEmpty()){
            throw new DataNotInDatabase(
                "person", person.getId()
            );
        }
        personRepositoryPort.save(person);
    }
    public void delete(int id) {
        if (personRepositoryPort.get(id).isEmpty()){
            throw new DataNotInDatabase(
                "person",id
            );
        }
    }

    public Person get(int id){
        return personRepositoryPort.get(id).orElseThrow(()
            -> new DataNotInDatabase(
            "person", id
        ));
    }

    @Override
    public Collection<Person> findAll() {
        return personRepositoryPort.findAll();
    }


}
