package nl.amis.sig.graphql.repository;

import nl.amis.sig.graphql.domain.Project;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProjectRepository extends JpaRepository<Project, Integer> {
    List<Project> findAll();

    Optional<Project> findById(Integer id);
}
