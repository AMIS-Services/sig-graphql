package nl.amis.sig.graphql.web.graphql;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import com.coxautodev.graphql.tools.GraphQLQueryResolver;

import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;

import nl.amis.sig.graphql.domain.Person;
import nl.amis.sig.graphql.repository.PersonRepository;

@Controller
@Transactional
public class Query implements GraphQLQueryResolver {

    private PersonRepository personRepository;

    public Query(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    public List<Person> people(Integer personId) {
        return Optional.ofNullable(personId).map(id -> personRepository.findById(id)
                .map(person -> Arrays.asList(person)).orElse(new ArrayList<Person>()))
                .orElse(personRepository.findAll());
    }

    // public List<Person> people() {
    // return personRepository.findAll();
    // }

    // public List<Person> people() {
    // Person person1 = new Person();
    // person1.setId(1);
    // person1.setName("Henk");
    // List<Person> people = new ArrayList<Person>();
    // people.add(person1);
    // return people;
    // }
}