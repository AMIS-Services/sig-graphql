package nl.amis.sig.graphql.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import nl.amis.sig.graphql.repository.ProjectRepository;
import nl.amis.sig.graphql.service.dto.ProjectDTO;
import nl.amis.sig.graphql.service.mapper.ProjectMapper;

@Service
public class ProjectService {

    private final ProjectRepository projectRepository;

    private final ProjectMapper projectMapper;

    public ProjectService(ProjectRepository projectRepository, ProjectMapper projectMapper) {
        this.projectRepository = projectRepository;
        this.projectMapper = projectMapper;
    }

    public List<ProjectDTO> getProjects() {
        return projectMapper.toDto(projectRepository.findAll());
    }

    public Optional<ProjectDTO> getProject(Integer id) {
        return projectRepository.findById(id).map(project -> projectMapper.toDto(project));
    }
}