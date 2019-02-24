package nl.amis.sig.graphql.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import nl.amis.sig.graphql.domain.Person;
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

    public PersonDTO createPerson(PersonDTO personDTO) {
        Person person = new Person();
        person.setName(personDTO.getName());
        person.setPractice(personDTO.getPractice());
        person.setProjects(personDTO.getProjects());
        personRepository.save(person);
        return personMapper.toDto(person);
    }

    public Optional<PersonDTO> updatePerson(Integer id, PersonDTO personDTO) {
        return personRepository.findById(id).map(person -> {
            person.setName(personDTO.getName());
            person.setPractice(personDTO.getPractice());
            person.setProjects(personDTO.getProjects());
            personRepository.save(person);
            return person;
        }).map(personMapper::toDto);
    }

    public Optional<Person> deletePerson(Integer id) {
        return personRepository.findById(id).map(person -> {
            personRepository.delete(person);
            return person;
        });
    }
}