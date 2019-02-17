package nl.amis.sig.graphql.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "practices")
public class Practice implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private Integer id;

    private String name;

    // databases usually are case-insensitive
    // apparently hibernate interprets column names that way
    // use backticks to interpret column names as-is
    @Column(name = "`createdAt`")
    private LocalDate createdAt;

    @Column(name = "`updatedAt`")
    private LocalDate updatedAt;

    @JsonIgnoreProperties({ "name", "createdAt", "updatedAt", "practice", "projects" }) // ignore all but ID
    @OneToMany(mappedBy = "practice") // reference Practice.java
    private Set<Person> people = new HashSet<Person>();

    @JsonIgnoreProperties({ "name", "createdAt", "updatedAt", "people", "practices" }) // ignore all but ID
    @ManyToMany
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
        return "Practice{" + "id=" + id + ", name='" + name + "'" + "}";
    }
}
