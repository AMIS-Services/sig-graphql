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

import nl.amis.sig.graphql.service.PersonService;
import nl.amis.sig.graphql.service.dto.PersonDTO;

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
    public ResponseEntity<List<PersonDTO>> getPeople() {
        return new ResponseEntity<List<PersonDTO>>(personService.getPeople(), HttpStatus.OK);
    }

    @GetMapping("/people/{id}")
    public ResponseEntity<PersonDTO> getPerson(@PathVariable Integer id) {
        return personService.getPerson(id).map(person -> new ResponseEntity<PersonDTO>(person, HttpStatus.OK))
                .orElse(new ResponseEntity<PersonDTO>(HttpStatus.NOT_FOUND));
    }

    @PostMapping("/people")
    public ResponseEntity<PersonDTO> createPerson(@RequestBody PersonDTO person) throws URISyntaxException {
        PersonDTO createdPerson = personService.createPerson(person);
        return ResponseEntity.created(new URI("/api/people/" + createdPerson.getId())).body(createdPerson);
    }

    @PutMapping("/people/{id}")
    public ResponseEntity<PersonDTO> updatePerson(@PathVariable Integer id, @RequestBody PersonDTO person) {
        return personService.updatePerson(id, person)
                .map(updatedPerson -> new ResponseEntity<PersonDTO>(updatedPerson, HttpStatus.OK))
                .orElse(new ResponseEntity<PersonDTO>(HttpStatus.NOT_FOUND));
    }

    @DeleteMapping("/people/{id}")
    public ResponseEntity<Void> deletePerson(@PathVariable Integer id) {
        return personService.deletePerson(id).map(deletedPerson -> new ResponseEntity<Void>(HttpStatus.NO_CONTENT))
                .orElse(new ResponseEntity<Void>(HttpStatus.NOT_FOUND));
    }
}
