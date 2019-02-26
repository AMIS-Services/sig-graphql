package nl.amis.sig.graphql.web.graphql;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.coxautodev.graphql.tools.GraphQLQueryResolver;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import nl.amis.sig.graphql.domain.Person;
import nl.amis.sig.graphql.domain.Practice;
import nl.amis.sig.graphql.domain.Project;
import nl.amis.sig.graphql.repository.PersonRepository;
import nl.amis.sig.graphql.repository.PracticeRepository;
import nl.amis.sig.graphql.repository.ProjectRepository;

@Component
@Transactional
public class Query implements GraphQLQueryResolver {

    private PersonRepository personRepository;
    private PracticeRepository practiceRepository;
    private ProjectRepository projectRepository;

    public Query(PersonRepository personRepository, PracticeRepository practiceRepository,
            ProjectRepository projectRepository) {
        this.personRepository = personRepository;
        this.practiceRepository = practiceRepository;
        this.projectRepository = projectRepository;
    }

    @Transactional(readOnly = true)
    public List<Project> projects(String projectName) {
        // @formatter:off
        return Optional.ofNullable(projectName)
            .map(id -> 
                projectRepository.findByName(projectName)
                    .map(project -> Arrays.asList(project))
                    .orElse(new ArrayList<Project>()))
            .orElse(projectRepository.findAll());
        // @formatter:on
    }

    @Transactional(readOnly = true)
    public List<Practice> practices(List<Integer> practiceIds) {
        // @formatter:off
        return Optional.ofNullable(practiceIds)
            .map(ids -> 
                ids.stream()
                    .map(id -> practiceRepository.findById(id))
                    .filter(Optional::isPresent)
                    .map(Optional::get)
                    .collect(Collectors.toList()))
            .orElse(practiceRepository.findAll());
        // @formatter:on
    }

    @Transactional(readOnly = true)
    public List<Person> people(Integer personId) {
        // @formatter:off
        return Optional.ofNullable(personId)
            .map(id -> 
                personRepository.findById(id)
                    .map(person -> Arrays.asList(person))
                    .orElse(new ArrayList<Person>()))
            .orElse(personRepository.findAll());
        // @formatter:on
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