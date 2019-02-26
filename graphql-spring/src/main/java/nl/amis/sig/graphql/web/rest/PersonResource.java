package nl.amis.sig.graphql.web.rest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import nl.amis.sig.graphql.domain.Person;
import nl.amis.sig.graphql.service.PersonService;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

@RestController
@RequestMapping("/api")
public class PersonResource {

    private final PersonService personService;

    public PersonResource(PersonService personService) {
        this.personService = personService;
    }

    @GetMapping("/people")
    public ResponseEntity<List<Person>> getPeople() {
        return new ResponseEntity<List<Person>>(personService.getPeople(), HttpStatus.OK);
    }

    @GetMapping("/people/{id}")
    public ResponseEntity<Person> getPerson(@PathVariable Integer id) {
        return personService.getPerson(id).map(person -> new ResponseEntity<Person>(person, HttpStatus.OK))
                .orElse(new ResponseEntity<Person>(HttpStatus.NOT_FOUND));
    }

    @PostMapping("/people")
    public ResponseEntity<Person> createPerson(@RequestBody Person newPerson) throws URISyntaxException {
        Person person = personService.createPerson(newPerson);
        return ResponseEntity.created(new URI("/api/people/" + person.getId())).body(person);
    }

    @PutMapping("/people/{id}")
    public ResponseEntity<Person> updatePerson(@PathVariable Integer id, @RequestBody Person personUpdate) {
        return personService.updatePerson(id, personUpdate)
                .map(person -> new ResponseEntity<Person>(person, HttpStatus.OK))
                .orElse(new ResponseEntity<Person>(HttpStatus.NOT_FOUND));
    }

    @DeleteMapping("/people/{id}")
    public ResponseEntity<Void> deletePerson(@PathVariable Integer id) {
        return personService.deletePerson(id).map(deletedPerson -> new ResponseEntity<Void>(HttpStatus.NO_CONTENT))
                .orElse(new ResponseEntity<Void>(HttpStatus.NOT_FOUND));
    }
}
