package fr.sg.tuto.repository;

import fr.sg.fmk.repository.WrapRepository;
import fr.sg.tuto.domain.Personne;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;
import org.springframework.data.repository.CrudRepository;

/**
 * @author jntakpe
 */
public interface PersonneRepository extends WrapRepository<Personne> {

    Personne findByEmail(String email);

}
