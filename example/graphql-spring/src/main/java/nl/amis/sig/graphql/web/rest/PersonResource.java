package nl.amis.sig.graphql.web.rest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import nl.amis.sig.graphql.service.PersonService;
import nl.amis.sig.graphql.service.dto.PersonDTO;

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
}
