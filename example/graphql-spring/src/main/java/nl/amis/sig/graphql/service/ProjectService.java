package nl.amis.sig.graphql.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import nl.amis.sig.graphql.domain.Project;
import nl.amis.sig.graphql.repository.ProjectRepository;

@Service
@Transactional
public class ProjectService {

    private final ProjectRepository projectRepository;

    public ProjectService(ProjectRepository projectRepository) {
        this.projectRepository = projectRepository;
    }

    @Transactional(readOnly = true)
    public List<Project> getProjects() {
        return projectRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Optional<Project> getProject(Integer id) {
        return projectRepository.findById(id);
    }

    public Project createProject(Project newProject) {
        newProject.setCreatedAt(LocalDate.now());
        newProject.setUpdatedAt(null);
        projectRepository.save(newProject);
        return newProject;
    }

    public Optional<Project> updateProject(Integer id, Project projectUpdate) {
        return projectRepository.findById(id).map(project -> {
            project.setName(projectUpdate.getName());
            project.setPeople(projectUpdate.getPeople());
            project.setPractices(projectUpdate.getPractices());
            project.setUpdatedAt(LocalDate.now());
            projectRepository.save(project);
            return project;
        });
    }

    public Optional<Integer> deleteProject(Integer id) {
        return projectRepository.findById(id).map(project -> {
            projectRepository.delete(project);
            return project.getId();
        });
    }
}