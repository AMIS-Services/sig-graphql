package nl.amis.sig.graphql.web;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import nl.amis.sig.graphql.repository.PersonRepository;
import nl.amis.sig.graphql.service.dto.PersonDTO;
import nl.amis.sig.graphql.service.mapper.PersonMapper;

import java.util.List;

@RestController
@RequestMapping("/api")
public class PersonResource {

    private final PersonRepository personRepository;

    private final PersonMapper personMapper;

    public PersonResource(PersonRepository personRepository, PersonMapper personMapper) {
        this.personRepository = personRepository;
        this.personMapper = personMapper;
    }

    @GetMapping("/people")
    public ResponseEntity<List<PersonDTO>> getAll() {
        final List<PersonDTO> people = personMapper.toDto(personRepository.findAll());
        return new ResponseEntity<List<PersonDTO>>(people, HttpStatus.OK);
    }

    @GetMapping("/people/{id}")
    public ResponseEntity<PersonDTO> getOne(@PathVariable Integer id) {
        return personRepository.findById(id)
                .map(person -> new ResponseEntity<PersonDTO>(personMapper.toDto(person), HttpStatus.OK))
                .orElse(new ResponseEntity<PersonDTO>(HttpStatus.NOT_FOUND));
    }
}
