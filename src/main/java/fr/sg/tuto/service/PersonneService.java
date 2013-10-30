package fr.sg.tuto.service;

import fr.sg.fmk.service.GenericSearchService;
import fr.sg.fmk.service.GenericService;
import fr.sg.tuto.domain.Personne;

/**
 * @author jntakpe
 */
public interface PersonneService extends GenericSearchService<Personne> {

    Personne findByEmail(String email);
}
