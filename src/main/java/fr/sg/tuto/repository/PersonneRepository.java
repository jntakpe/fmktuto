package fr.sg.tuto.repository;

import fr.sg.tuto.domain.Personne;
import org.springframework.data.repository.CrudRepository;

/**
 * @author jntakpe
 */
public interface PersonneRepository extends CrudRepository<Personne, Long> {

    Personne findByEmail(String email);

}
