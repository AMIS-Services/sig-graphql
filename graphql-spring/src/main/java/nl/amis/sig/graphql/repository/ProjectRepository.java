package nl.amis.sig.graphql.repository;

import nl.amis.sig.graphql.domain.Project;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

@Repository
public interface ProjectRepository extends JpaRepository<Project, String> {
}
