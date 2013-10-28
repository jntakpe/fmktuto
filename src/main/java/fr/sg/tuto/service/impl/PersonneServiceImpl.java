package fr.sg.tuto.service.impl;

import fr.sg.fmk.service.impl.GenericServiceImpl;
import fr.sg.tuto.domain.Personne;
import fr.sg.tuto.repository.PersonneRepository;
import fr.sg.tuto.service.PersonneService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;

/**
 * @author jntakpe
 */
@Service
public class PersonneServiceImpl extends GenericServiceImpl<Personne> implements PersonneService {

    @Autowired
    private PersonneRepository personneRepository;

    @Override
    public Personne findByEmail(String email) {
        return personneRepository.findByEmail(email);
    }

    @Override
    public CrudRepository<Personne, Long> getRepository() {
        return personneRepository;
    }
}
