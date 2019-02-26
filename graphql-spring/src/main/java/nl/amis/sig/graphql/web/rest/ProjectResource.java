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

import nl.amis.sig.graphql.domain.Project;
import nl.amis.sig.graphql.service.ProjectService;

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
    public ResponseEntity<List<Project>> getProjects() {
        return new ResponseEntity<List<Project>>(projectService.getProjects(), HttpStatus.OK);
    }

    @GetMapping("/projects/{id}")
    public ResponseEntity<Project> getProject(@PathVariable Integer id) {
        return projectService.getProject(id).map(project -> new ResponseEntity<Project>(project, HttpStatus.OK))
                .orElse(new ResponseEntity<Project>(HttpStatus.NOT_FOUND));
    }

    @PostMapping("/projects")
    public ResponseEntity<Project> createProject(@RequestBody Project newProject) throws URISyntaxException {
        Project project = projectService.createProject(newProject);
        return ResponseEntity.created(new URI("/api/projects/" + project.getId())).body(project);
    }

    @PutMapping("/projects/{id}")
    public ResponseEntity<Project> updateProject(@PathVariable Integer id, @RequestBody Project projectUpdate) {
        return projectService.updateProject(id, projectUpdate)
                .map(project -> new ResponseEntity<Project>(project, HttpStatus.OK))
                .orElse(new ResponseEntity<Project>(HttpStatus.NOT_FOUND));
    }

    @DeleteMapping("/projects/{id}")
    public ResponseEntity<Void> deleteProject(@PathVariable Integer id) {
        return projectService.deleteProject(id).map(deletedProject -> new ResponseEntity<Void>(HttpStatus.NO_CONTENT))
                .orElse(new ResponseEntity<Void>(HttpStatus.NOT_FOUND));
    }
}
