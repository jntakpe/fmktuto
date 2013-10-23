package fr.sg.fmk.web;

import fr.sg.fmk.domain.GenericDomain;
import fr.sg.fmk.dto.ResponseMessage;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.View;

/**
 * Contrôleur permettant d'afficher les détails correspondants aux entités de la liste pour effectuer les opérations
 * de création et modification.
 * Cette classe ne doit pas être directement appelée (package local).
 * Veuillez utiliser ces classes filles {@link ListeDetailServerController} ou {@link ListeDetailClientController}.
 *
 * @author jntakpe
 * @see ListeDetailServerController
 * @see ListeDetailClientController
 * @see ListController
 */
abstract class ListeDetailController<T extends GenericDomain> extends ListController<T> {

    /**
     * Affiche l'écran détail de création d'un nouveau élément
     *
     * @return page détail
     */
    @RequestMapping(value = "/new", method = RequestMethod.GET)
    public ModelAndView displayDetail() throws Exception {
        return new ModelAndView(getDetailViewName()).addObject("domain", getDomainClass().newInstance());
    }

    /**
     * Affiche l'écran détail correspondant à l'élément possédant cette id
     *
     * @param id identifiant de l'élément à afficher
     * @return page détail
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ModelAndView displayDetail(@PathVariable Long id) {
        ModelAndView mv = new ModelAndView(getDetailViewName());
        T domain = getService().findOne(id);
        if (domain == null) {
            mv.setViewName(getListViewName());
            return mv.addObject(ResponseMessage.getErrorMessage(messageManager.getMessage("access.notexist")));
        }
        return mv.addObject("domain", domain);
    }

    /**
     * Récupère le nom de la vue du détail à afficher.
     * A surcharger si le nom de la vue est différent de NOM_ENTITE + '_detail'.
     *
     * @return le nom de la vue à afficher
     */
    public String getDetailViewName() {
        return getDomainClass().getSimpleName().toLowerCase() + "_detail";
    }

    /**
     * Vue à afficher après la sauvegarde de l'entité. Par défaut, on retourne à la liste.
     *
     * @return vue de la liste
     */
    public View getAfterSaveView() {
        return getRedirectListView();
    }
}
