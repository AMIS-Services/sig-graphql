package nl.amis.sig.graphql.service.dto;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Set;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import nl.amis.sig.graphql.domain.Practice;
import nl.amis.sig.graphql.domain.Project;

public class PersonDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer id;

    private String name;

    private LocalDate createdAt;

    private LocalDate updatedAt;

    private Practice practice;

    private Set<Project> projects;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDate createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDate getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDate updatedAt) {
        this.updatedAt = updatedAt;
    }

    @JsonIgnoreProperties({ "name", "createdAt", "updatedAt", "people", "projects" })
    public Practice getPractice() {
        return practice;
    }

    public void setPractice(Practice practice) {
        this.practice = practice;
    }

    @JsonIgnoreProperties({ "name", "createdAt", "updatedAt", "people", "practices" })
    public Set<Project> getProjects() {
        return projects;
    }

    public void setProjects(Set<Project> projects) {
        this.projects = projects;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        PersonDTO person = (PersonDTO) o;

        return !(id != null ? !id.equals(person.id) : person.id != null);
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }

    @Override
    public String toString() {
        //@formatter:off
        return "PersonDTO{" + 
            "id=" + id + 
            ", name='" + name + "'" + 
            ", createdAt=" + createdAt +
            ", updatedAt=" + updatedAt +
            ", practice=" + practice.getId() +
            ", projects=" + projects.stream().map(Project::getId).collect(Collectors.toSet()) +
            "}";
        //@formatter:on
    }
}
