package nl.amis.sig.graphql.web;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import nl.amis.sig.graphql.repository.ProjectRepository;
import nl.amis.sig.graphql.service.dto.ProjectDTO;
import nl.amis.sig.graphql.service.mapper.ProjectMapper;

import java.util.List;

@RestController
@RequestMapping("/api")
public class ProjectResource {

    private final ProjectRepository projectRepository;

    private final ProjectMapper projectMapper;

    public ProjectResource(ProjectRepository projectRepository, ProjectMapper projectMapper) {
        this.projectRepository = projectRepository;
        this.projectMapper = projectMapper;
    }

    @GetMapping("/projects")
    public List<ProjectDTO> getAll() {
        return projectMapper.toDto(projectRepository.findAll());
    }
}
