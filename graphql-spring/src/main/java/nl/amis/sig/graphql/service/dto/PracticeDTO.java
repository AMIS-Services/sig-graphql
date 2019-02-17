package nl.amis.sig.graphql.service.dto;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Set;

import nl.amis.sig.graphql.domain.Person;
import nl.amis.sig.graphql.domain.Project;

public class PracticeDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer id;

    private String name;

    private LocalDate createdAt;

    private LocalDate updatedAt;

    private Set<Person> people;

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
        return "PracticeDTO{" + "id=" + id + ", name='" + name + "'" + "}";
    }
}
