package nl.amis.sig.graphql.web.graphql;

import java.util.List;
import java.util.Optional;

import com.coxautodev.graphql.tools.GraphQLResolver;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import nl.amis.sig.graphql.domain.Person;
import nl.amis.sig.graphql.domain.Practice;
import nl.amis.sig.graphql.domain.Project;
import nl.amis.sig.graphql.repository.PracticeRepository;
import nl.amis.sig.graphql.repository.ProjectRepository;

@Component
@Transactional
public class PersonResolver implements GraphQLResolver<Person> {

    private PracticeRepository practiceRepository;
    private ProjectRepository projectRepository;

    public PersonResolver(PracticeRepository practiceRepository, ProjectRepository projectRepository) {
        this.practiceRepository = practiceRepository;
        this.projectRepository = projectRepository;
    }

    @Transactional(readOnly = true)
    public Optional<Practice> getPractice(Person person) {
        return practiceRepository.findByPeople_Id(person.getId());
    }

    @Transactional(readOnly = true)
    public List<Project> getProjects(Person person) {
        return projectRepository.findByPeople_Id(person.getId());
    }
}