package nl.amis.sig.graphql.repository;

import nl.amis.sig.graphql.domain.Person;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PersonRepository extends JpaRepository<Person, Integer> {
    List<Person> findAll();

    Optional<Person> findById(Integer id);

    List<Person> findByPractice_Id(Integer practiceId);

    List<Person> findByProjects_Id(Integer projectId);
}
