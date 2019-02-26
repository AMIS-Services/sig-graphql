package nl.amis.sig.graphql.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
@Table(name = "practices")
public class Practice implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue
    private Integer id;

    private String name;

    @JsonFormat(pattern = "yyyy-MM-dd")
    @Column(name = "`createdAt`") // this postgresql column is case-sensitive
    private LocalDate createdAt;

    @JsonFormat(pattern = "yyyy-MM-dd")
    @Column(name = "`updatedAt`") // this postgresql column is case-sensitive
    private LocalDate updatedAt;

    @JsonIgnoreProperties({ "name", "createdAt", "updatedAt", "projects", "practice" })
    @OneToMany(mappedBy = "practice") // maps to the practice attribute of the Person object
    private Set<Person> people = new HashSet<Person>();

    @JsonIgnoreProperties({ "name", "createdAt", "updatedAt", "people", "practices" })
    @ManyToMany
    // these postgresql table and columns are case-sensitive
    @JoinTable(name = "`PracticeProject`", joinColumns = { @JoinColumn(name = "`practiceId`") }, inverseJoinColumns = {
            @JoinColumn(name = "`projectId`") })
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

        Practice practice = (Practice) o;

        return !(id != null ? !id.equals(practice.id) : practice.id != null);
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }

    @Override
    public String toString() {
        //@formatter:off
        return "Person{" + 
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
