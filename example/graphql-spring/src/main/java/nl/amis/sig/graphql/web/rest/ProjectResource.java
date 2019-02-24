package nl.amis.sig.graphql.web.rest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import nl.amis.sig.graphql.service.ProjectService;
import nl.amis.sig.graphql.service.dto.ProjectDTO;

import java.util.List;

@RestController
@RequestMapping("/api")
public class ProjectResource {

    private final ProjectService projectService;

    public ProjectResource(ProjectService projectService) {
        this.projectService = projectService;
    }

    @GetMapping("/projects")
    public ResponseEntity<List<ProjectDTO>> getProjects() {
        return new ResponseEntity<List<ProjectDTO>>(projectService.getProjects(), HttpStatus.OK);
    }

    @GetMapping("/projects/{id}")
    public ResponseEntity<ProjectDTO> getProject(@PathVariable Integer id) {
        return projectService.getProject(id).map(project -> new ResponseEntity<ProjectDTO>(project, HttpStatus.OK))
                .orElse(new ResponseEntity<ProjectDTO>(HttpStatus.NOT_FOUND));
    }
}
