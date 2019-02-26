package nl.amis.sig.graphql.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import nl.amis.sig.graphql.domain.Person;
import nl.amis.sig.graphql.repository.PersonRepository;

@Service
@Transactional
public class PersonService {
    private final PersonRepository personRepository;

    public PersonService(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    @Transactional(readOnly = true)
    public List<Person> getPeople() {
        return personRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Optional<Person> getPerson(Integer id) {
        return personRepository.findById(id);
    }

    public Person createPerson(Person newPerson) {
        newPerson.setCreatedAt(LocalDate.now());
        newPerson.setUpdatedAt(null);
        personRepository.save(newPerson);
        return newPerson;
    }

    public Optional<Person> updatePerson(Integer id, Person personUpdate) {
        return personRepository.findById(id).map(person -> {
            person.setName(personUpdate.getName());
            person.setPractice(personUpdate.getPractice());
            person.setProjects(personUpdate.getProjects());
            person.setUpdatedAt(LocalDate.now());
            personRepository.save(person);
            return person;
        });
    }

    public Optional<Integer> deletePerson(Integer id) {
        return personRepository.findById(id).map(person -> {
            personRepository.delete(person);
            return person.getId();
        });
    }
}