package fr.sg.tuto.web;

import fr.sg.fmk.service.GenericService;
import fr.sg.fmk.web.GenericController;
import fr.sg.tuto.domain.Personne;
import fr.sg.tuto.service.PersonneService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author jntakpe
 */
@Controller
@RequestMapping("/personne")
public class PersonneController extends GenericController<Personne> {

    @Autowired
    private PersonneService personneService;

    @Override
    protected GenericService<Personne> getService() {
        return personneService;
    }
}
