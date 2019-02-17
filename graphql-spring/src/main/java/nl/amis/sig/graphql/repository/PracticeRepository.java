package nl.amis.sig.graphql.repository;

import nl.amis.sig.graphql.domain.Practice;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

@Repository
public interface PracticeRepository extends JpaRepository<Practice, String> {
}
