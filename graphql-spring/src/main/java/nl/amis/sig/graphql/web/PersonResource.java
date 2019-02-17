package nl.amis.sig.graphql.web;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import nl.amis.sig.graphql.domain.Person;
import nl.amis.sig.graphql.repository.PersonRepository;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
@RequestMapping("/api")
public class PersonResource {

    private final Logger log = LoggerFactory.getLogger(PersonResource.class);

    private final PersonRepository personRepository;

    public PersonResource(PersonRepository personRepository) {

        this.personRepository = personRepository;
    }

    @GetMapping("/people")
    public List<Person> getAll() {
        log.debug("Rest call to get all people");
        return personRepository.findAll();
    }
}
