package fr.sg.tuto.web;

import fr.sg.fmk.service.GenericSearchService;
import fr.sg.fmk.service.GenericService;
import fr.sg.fmk.web.ListController;
import fr.sg.fmk.web.ServerListController;
import fr.sg.tuto.domain.Personne;
import fr.sg.tuto.service.PersonneService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

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

    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView display() {
        return new ModelAndView("lists/" + getListViewName());
    }

    @Override
    protected GenericService<Personne> getService() {
        return personneService;
    }
}
