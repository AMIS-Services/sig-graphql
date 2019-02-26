package nl.amis.sig.graphql.web.graphql;

import java.util.List;

import com.coxautodev.graphql.tools.GraphQLResolver;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import nl.amis.sig.graphql.domain.Person;
import nl.amis.sig.graphql.domain.Practice;
import nl.amis.sig.graphql.domain.Project;
import nl.amis.sig.graphql.repository.PersonRepository;
import nl.amis.sig.graphql.repository.PracticeRepository;

@Component
@Transactional
public class ProjectResolver implements GraphQLResolver<Project> {

    private PersonRepository personRepository;
    private PracticeRepository practiceRepository;

    public ProjectResolver(PersonRepository personRepository, PracticeRepository practiceRepository) {
        this.personRepository = personRepository;
        this.practiceRepository = practiceRepository;
    }

    @Transactional(readOnly = true)
    public List<Person> getPeople(Project project) {
        return personRepository.findByProjects_Id(project.getId());
    }

    @Transactional(readOnly = true)
    public List<Practice> getPractices(Project project) {
        return practiceRepository.findByProjects_Id(project.getId());
    }
}