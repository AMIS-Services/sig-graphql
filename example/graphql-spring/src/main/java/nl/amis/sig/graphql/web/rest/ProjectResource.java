package nl.amis.sig.graphql.web.rest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import nl.amis.sig.graphql.service.ProjectService;
import nl.amis.sig.graphql.service.dto.ProjectDTO;

import java.net.URI;
import java.net.URISyntaxException;
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

    @PostMapping("/projects")
    public ResponseEntity<ProjectDTO> createProject(@RequestBody ProjectDTO project) throws URISyntaxException {
        ProjectDTO createdProject = projectService.createProject(project);
        return ResponseEntity.created(new URI("/api/projects/" + createdProject.getId())).body(createdProject);
    }

    @PutMapping("/projects/{id}")
    public ResponseEntity<ProjectDTO> updateProject(@PathVariable Integer id, @RequestBody ProjectDTO project) {
        return projectService.updateProject(id, project)
                .map(updatedProject -> new ResponseEntity<ProjectDTO>(updatedProject, HttpStatus.OK))
                .orElse(new ResponseEntity<ProjectDTO>(HttpStatus.NOT_FOUND));
    }

    @DeleteMapping("/projects/{id}")
    public ResponseEntity<Void> deleteProject(@PathVariable Integer id) {
        return projectService.deleteProject(id).map(deletedProject -> new ResponseEntity<Void>(HttpStatus.NO_CONTENT))
                .orElse(new ResponseEntity<Void>(HttpStatus.NOT_FOUND));
    }
}
