package nl.amis.sig.graphql.repository;

import nl.amis.sig.graphql.domain.Person;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

@Repository
public interface PersonRepository extends JpaRepository<Person, String> {
}
