package fr.sg.tuto.web;

import fr.sg.fmk.service.GenericSearchService;
import fr.sg.fmk.service.GenericService;
import fr.sg.fmk.web.ServerListController;
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
public class PersonneController extends ServerListController<Personne> {

    @Autowired
    private PersonneService personneService;

    @Override
    public GenericSearchService<Personne> getGenericSearchService() {
        return personneService;
    }

    @Override
    protected GenericService<Personne> getService() {
        return personneService;
    }
}
