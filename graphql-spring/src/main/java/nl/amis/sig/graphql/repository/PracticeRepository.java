package nl.amis.sig.graphql.repository;

import nl.amis.sig.graphql.domain.Practice;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PracticeRepository extends JpaRepository<Practice, Integer> {
    List<Practice> findAll();

    Optional<Practice> findById(Integer id);

    Optional<Practice> findByName(String name);

    Optional<Practice> findByPeople_Id(Integer personId);

    List<Practice> findByProjects_Id(Integer projectId);
}
