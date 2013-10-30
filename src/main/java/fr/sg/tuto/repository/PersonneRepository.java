package fr.sg.tuto.repository;

import fr.sg.fmk.repository.FmkRepository;
import fr.sg.tuto.domain.Personne;

/**
 * @author jntakpe
 */
public interface PersonneRepository extends FmkRepository<Personne> {

    Personne findByEmail(String email);

}
