package nl.amis.sig.graphql.service.dto;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import nl.amis.sig.graphql.domain.Person;
import nl.amis.sig.graphql.domain.Practice;

public class ProjectDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer id;

    private String name;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate createdAt;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate updatedAt;

    @JsonIgnoreProperties({ "name", "createdAt", "updatedAt", "practice", "projects" })
    private Set<Person> people = new HashSet<Person>();

    @JsonIgnoreProperties({ "name", "createdAt", "updatedAt", "people", "projects" })
    private Set<Practice> practices = new HashSet<Practice>();

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

    public Set<Practice> getPractices() {
        return practices;
    }

    public void setPractices(Set<Practice> practices) {
        this.practices = practices;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ProjectDTO project = (ProjectDTO) o;

        return !(id != null ? !id.equals(project.id) : project.id != null);
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }

    @Override
    public String toString() {
        //@formatter:off
        return "ProjectDTO{" + 
            "id=" + id + 
            ", name='" + name + "'" + 
            ", createdAt=" + createdAt +
            ", updatedAt=" + updatedAt +
            ", people=" + people.stream().map(Person::getId).collect(Collectors.toSet()) +
            ", practices=" + practices.stream().map(Practice::getId).collect(Collectors.toSet()) +
            "}";
        //@formatter:on
    }
}
