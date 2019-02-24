package nl.amis.sig.graphql.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
@Table(name = "people")
public class Person implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue
    private Integer id;

    private String name;

    @Column(name = "`createdAt`") // this postgresql column is case-sensitive
    @CreationTimestamp
    private LocalDate createdAt;

    @Column(name = "`updatedAt`") // this postgresql column is case-sensitive
    @UpdateTimestamp
    private LocalDate updatedAt;

    @ManyToOne
    @JoinColumn(name = "`practiceId`") // this postgresql column is case-sensitive
    private Practice practice;

    @ManyToMany
    // these postgresql table and columns are case-sensitive
    @JoinTable(name = "`PersonProject`", joinColumns = { @JoinColumn(name = "`personId`") }, inverseJoinColumns = {
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

    public Practice getPractice() {
        return practice;
    }

    public void setPractice(Practice practice) {
        this.practice = practice;
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

        Person person = (Person) o;

        return !(id != null ? !id.equals(person.id) : person.id != null);
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
            ", practice=" + Optional.ofNullable(practice).map(Practice::getId).orElse(null) +
            ", projects=" + projects.stream().map(Project::getId).collect(Collectors.toSet()) +
            "}";
        //@formatter:on
    }
}
