package nl.amis.sig.graphql.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
@Table(name = "projects")
public class Project implements Serializable {

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

    @ManyToMany
    // these postgresql table and columns are case-sensitive
    @JoinTable(name = "`PersonProject`", joinColumns = { @JoinColumn(name = "`projectId`") }, inverseJoinColumns = {
            @JoinColumn(name = "`personId`") })
    private Set<Person> people = new HashSet<Person>();

    @ManyToMany
    @JoinTable(name = "`PracticeProject`", joinColumns = { @JoinColumn(name = "`projectId`") }, inverseJoinColumns = {
            @JoinColumn(name = "`practiceId`") })
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

        Project project = (Project) o;

        return !(id != null ? !id.equals(project.id) : project.id != null);
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }

    @Override
    public String toString() {
        //@formatter:off
        return "Project{" + 
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
