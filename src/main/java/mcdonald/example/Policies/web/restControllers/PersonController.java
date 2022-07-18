package mcdonald.example.Policies.web.restControllers;

import mcdonald.example.Policies.domain.Person;
import mcdonald.example.Policies.service.PersonService;
import mcdonald.example.Policies.service.implementation.IdGenerator;
import mcdonald.example.Policies.web.request.PersonRequest;
import mcdonald.example.Policies.web.response.PersonResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Collection;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/person")
public class PersonController {
    private Person fromRequestToPerson(@RequestBody PersonRequest request, @PathVariable int id ){
        return new Person(
            id,
            request.getAge(),
            request.getFirstName(),
            request.getLastName(),
            request.getAddress(),
            request.getPhoneNumber()
        );
    }

    private PersonResponse fromPersonToResponse(Person person){
        return new PersonResponse(
            person.getId(),
            person.getAge(),
            person.getFirstName(),
            person.getLastName(),
            person.getAddress(),
            person.getPhoneNumber()
        );
    }

    private final PersonService personService;
    private final IdGenerator idGenerator;
    public PersonController(PersonService personService, IdGenerator idGenerator) {
        this.personService = personService;
        this.idGenerator = idGenerator;
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<PersonResponse> get(@PathVariable int id){
        return new ResponseEntity<>(fromPersonToResponse(personService.get(id)), HttpStatus.OK);
    }

    @PutMapping(path = "/{id}")
    public ResponseEntity<Void> update(@PathVariable int id, @RequestBody @Valid PersonRequest request){
        personService.update(fromRequestToPerson(request,id));
        return ResponseEntity.noContent().build();
    }

    @PostMapping
    public ResponseEntity<PersonResponse> post(@RequestBody @Valid PersonRequest request){
        return new ResponseEntity<>(fromPersonToResponse( personService.add(fromRequestToPerson(request, idGenerator.generatePersonID()))), HttpStatus.CREATED);
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<Void> delete(@PathVariable int id){
        personService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<Collection<PersonResponse>> findAll(){
        return ResponseEntity.ok(personService.findAll().stream().map(this::fromPersonToResponse).collect(Collectors.toList()));
    }
}
