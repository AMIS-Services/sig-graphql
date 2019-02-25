package nl.amis.sig.graphql.web.graphql;

import com.coxautodev.graphql.tools.GraphQLQueryResolver;

import org.springframework.stereotype.Controller;

import nl.amis.sig.graphql.domain.Person;
import nl.amis.sig.graphql.repository.PersonRepository;

@Controller
public class Query implements GraphQLQueryResolver {

    private PersonRepository personRepository;

    public Query(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    public Iterable<Person> people() {
        return personRepository.findAll();
    }
}