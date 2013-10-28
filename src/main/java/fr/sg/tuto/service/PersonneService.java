package fr.sg.tuto.service;

import fr.sg.fmk.service.GenericService;
import fr.sg.tuto.domain.Personne;

/**
 * @author jntakpe
 */
public interface PersonneService extends GenericService<Personne> {

    Personne findByEmail(String email);
}
