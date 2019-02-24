package nl.amis.sig.graphql.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import nl.amis.sig.graphql.repository.PersonRepository;
import nl.amis.sig.graphql.service.dto.PersonDTO;
import nl.amis.sig.graphql.service.mapper.PersonMapper;

@Service
public class PersonService {
    private final PersonRepository personRepository;

    private final PersonMapper personMapper;

    public PersonService(PersonRepository personRepository, PersonMapper personMapper) {
        this.personRepository = personRepository;
        this.personMapper = personMapper;
    }

    public List<PersonDTO> getPeople() {
        return personMapper.toDto(personRepository.findAll());
    }

    public Optional<PersonDTO> getPerson(Integer id) {
        return personRepository.findById(id).map(person -> personMapper.toDto(person));
    }
}