package nl.amis.sig.graphql.web;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
    public ResponseEntity<List<ProjectDTO>> getAll() {
        final List<ProjectDTO> projects = projectMapper.toDto(projectRepository.findAll());
        return new ResponseEntity<List<ProjectDTO>>(projects, HttpStatus.OK);
    }

    @GetMapping("/projects/{id}")
    public ResponseEntity<ProjectDTO> getOne(@PathVariable Integer id) {
        return projectRepository.findById(id)
                .map(project -> new ResponseEntity<ProjectDTO>(projectMapper.toDto(project), HttpStatus.OK))
                .orElse(new ResponseEntity<ProjectDTO>(HttpStatus.NOT_FOUND));
    }
}
