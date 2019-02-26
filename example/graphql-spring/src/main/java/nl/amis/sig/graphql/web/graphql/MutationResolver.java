package nl.amis.sig.graphql.web.graphql;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.coxautodev.graphql.tools.GraphQLMutationResolver;

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
public class MutationResolver implements GraphQLMutationResolver {

    private PersonRepository personRepository;
    private PracticeRepository practiceRepository;
    private ProjectRepository projectRepository;

    public MutationResolver(PersonRepository personRepository, PracticeRepository practiceRepository,
            ProjectRepository projectRepository) {
        this.personRepository = personRepository;
        this.practiceRepository = practiceRepository;
        this.practiceRepository = practiceRepository;
    }

    public Person createPerson(String name, Integer practiceId, List<Integer> projectIds) {
        Person person = new Person();
        person.setName(name);
        person.setCreatedAt(LocalDate.now());
        person.setUpdatedAt(LocalDate.now());
        person.setPractice(
        // @formatter:off
            Optional.ofNullable(practiceId)
                .map(id -> new Practice().id(id))
                .orElse(null));
            // @formatter:on
        person.setProjects(
        // @formatter:off
            Optional.ofNullable(projectIds)
                .map(ids -> 
                    ids.stream()
                        .map(id -> new Project().id(id))
                        .collect(Collectors.toSet()))
                .orElse(null));
            // @formatter:on
        personRepository.save(person);
        return person;
    }

    public Optional<Person> updatePerson(Integer personId, String name, Integer practiceId, List<Integer> projectIds) {
        return personRepository.findById(personId).map(person -> {
            person.setName(name);
            person.setUpdatedAt(LocalDate.now());
            person.setPractice(
            // @formatter:off
                Optional.ofNullable(practiceId)
                    .map(id -> new Practice().id(id))
                    .orElse(null));
                // @formatter:on
            person.setProjects(
            // @formatter:off
                Optional.ofNullable(projectIds)
                    .map(ids -> 
                        ids.stream()
                            .map(id -> new Project().id(id))
                            .collect(Collectors.toSet()))
                    .orElse(null));
                // @formatter:on
            personRepository.save(person);
            return person;
        });
    }

    public Optional<Integer> deletePerson(Integer id) {
        return personRepository.findById(id).map(person -> {
            personRepository.delete(person);
            return id;
        });
    }

    public Practice createPractice(String name, List<Integer> personIds, List<Integer> projectIds) {
        Practice practice = new Practice();
        practice.setName(name);
        practice.setCreatedAt(LocalDate.now());
        practice.setUpdatedAt(LocalDate.now());
        practice.setPeople(
        // @formatter:off
            Optional.ofNullable(personIds)
            .map(ids -> 
                ids.stream()
                    .map(id -> new Person().id(id))
                    .collect(Collectors.toSet()))
            .orElse(null));
            // @formatter:on
        practice.setProjects(
        // @formatter:off
            Optional.ofNullable(projectIds)
                .map(ids -> 
                    ids.stream()
                        .map(id -> new Project().id(id))
                        .collect(Collectors.toSet()))
                .orElse(null));
            // @formatter:on
        practiceRepository.save(practice);
        return practice;
    }

    public Optional<Practice> updatepractice(Integer practiceId, String name, List<Integer> personIds,
            List<Integer> projectIds) {
        return practiceRepository.findById(practiceId).map(practice -> {
            practice.setName(name);
            practice.setUpdatedAt(LocalDate.now());
            practice.setPeople(
            // @formatter:off
                Optional.ofNullable(personIds)
                .map(ids -> 
                    ids.stream()
                        .map(id -> new Person().id(id))
                        .collect(Collectors.toSet()))
                .orElse(null));
                // @formatter:on
            practice.setProjects(
            // @formatter:off
                Optional.ofNullable(projectIds)
                    .map(ids -> 
                        ids.stream()
                            .map(id -> new Project().id(id))
                            .collect(Collectors.toSet()))
                    .orElse(null));
                // @formatter:on
            practiceRepository.save(practice);
            return practice;
        });
    }

    public Optional<Integer> deletePractice(Integer id) {
        return practiceRepository.findById(id).map(practice -> {
            practiceRepository.delete(practice);
            return id;
        });
    }

    public Project createProject(String name, List<Integer> personIds, List<Integer> practiceIds) {
        Project project = new Project();
        project.setName(name);
        project.setCreatedAt(LocalDate.now());
        project.setUpdatedAt(LocalDate.now());
        project.setPeople(
        // @formatter:off
            Optional.ofNullable(personIds)
            .map(ids -> 
                ids.stream()
                    .map(id -> new Person().id(id))
                    .collect(Collectors.toSet()))
            .orElse(null));
            // @formatter:on
        project.setPractices(
        // @formatter:off
            Optional.ofNullable(practiceIds)
                .map(ids -> 
                    ids.stream()
                        .map(id -> new Practice().id(id))
                        .collect(Collectors.toSet()))
                .orElse(null));
            // @formatter:on
        projectRepository.save(project);
        return project;
    }

    public Optional<Project> updateProject(Integer projectId, String name, List<Integer> personIds,
            List<Integer> practiceIds) {
        return projectRepository.findById(projectId).map(project -> {
            project.setName(name);
            project.setUpdatedAt(LocalDate.now());
            project.setPeople(
            // @formatter:off
                Optional.ofNullable(personIds)
                .map(ids -> 
                    ids.stream()
                        .map(id -> new Person().id(id))
                        .collect(Collectors.toSet()))
                .orElse(null));
                // @formatter:on
            project.setPractices(
            // @formatter:off
                Optional.ofNullable(practiceIds)
                    .map(ids -> 
                        ids.stream()
                            .map(id -> new Practice().id(id))
                            .collect(Collectors.toSet()))
                    .orElse(null));
                // @formatter:on
            projectRepository.save(project);
            return project;
        });
    }

    public Optional<Integer> deleteProject(Integer id) {
        return projectRepository.findById(id).map(project -> {
            projectRepository.delete(project);
            return id;
        });
    }
}