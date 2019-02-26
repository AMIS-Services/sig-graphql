package nl.amis.sig.graphql.web.graphql;

import java.util.List;

import com.coxautodev.graphql.tools.GraphQLResolver;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import nl.amis.sig.graphql.domain.Person;
import nl.amis.sig.graphql.domain.Practice;
import nl.amis.sig.graphql.domain.Project;
import nl.amis.sig.graphql.repository.PersonRepository;
import nl.amis.sig.graphql.repository.ProjectRepository;

@Component
@Transactional
public class PracticeResolver implements GraphQLResolver<Practice> {

    private PersonRepository personRepository;
    private ProjectRepository projectRepository;

    public PracticeResolver(PersonRepository personRepository, ProjectRepository projectRepository) {
        this.personRepository = personRepository;
        this.projectRepository = projectRepository;
    }

    @Transactional(readOnly = true)
    public List<Person> getPeople(Practice practice) {
        return personRepository.findByPractice_Id(practice.getId());
    }

    @Transactional(readOnly = true)
    public List<Project> getProjects(Practice practice) {
        return projectRepository.findByPractices_Id(practice.getId());
    }
}