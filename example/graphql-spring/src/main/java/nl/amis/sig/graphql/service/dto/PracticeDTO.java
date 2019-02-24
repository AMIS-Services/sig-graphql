package nl.amis.sig.graphql.service.dto;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import nl.amis.sig.graphql.domain.Person;
import nl.amis.sig.graphql.domain.Project;

public class PracticeDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer id;

    private String name;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate createdAt;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate updatedAt;

    @JsonIgnoreProperties({ "name", "createdAt", "updatedAt", "projects", "practice" })
    private Set<Person> people = new HashSet<Person>();

    @JsonIgnoreProperties({ "name", "createdAt", "updatedAt", "people", "practices" })
    private Set<Project> projects = new HashSet<Project>();

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

    public Set<Person> getPeople() {
        return people;
    }

    public void setPeople(Set<Person> people) {
        this.people = people;
    }

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

        PracticeDTO practice = (PracticeDTO) o;

        return !(id != null ? !id.equals(practice.id) : practice.id != null);
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
            ", people=" + people.stream().map(Person::getId).collect(Collectors.toSet()) +
            ", projects=" + projects.stream().map(Project::getId).collect(Collectors.toSet()) +
            "}";
        //@formatter:on
    }
}
