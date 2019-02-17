package nl.amis.sig.graphql.web;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import nl.amis.sig.graphql.domain.Project;
import nl.amis.sig.graphql.repository.ProjectRepository;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
@RequestMapping("/api")
public class ProjectResource {

    private final Logger log = LoggerFactory.getLogger(ProjectResource.class);

    private final ProjectRepository projectRepository;

    public ProjectResource(ProjectRepository projectRepository) {

        this.projectRepository = projectRepository;
    }

    @GetMapping("/projects")
    public List<Project> getAll() {
        log.debug("Rest call to get all projects");
        return projectRepository.findAll();
    }
}
