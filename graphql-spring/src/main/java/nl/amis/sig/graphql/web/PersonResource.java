package nl.amis.sig.graphql.web;

import org.springframework.web.bind.annotation.GetMapping;
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
    public List<PersonDTO> getAll() {
        return personMapper.toDto(personRepository.findAll());
    }
}
