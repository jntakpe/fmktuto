package fr.sg.fmk.web;

import fr.sg.fmk.domain.GenericDomain;
import fr.sg.fmk.dto.ResponseMessage;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.View;

/**
 * Contr�leur permettant d'afficher les d�tails correspondants aux entit�s de la liste pour effectuer les op�rations
 * de cr�ation et modification.
 * Cette classe ne doit pas �tre directement appel�e (package local).
 * Veuillez utiliser ces classes filles {@link ListeDetailServerController} ou {@link ListeDetailClientController}.
 *
 * @author jntakpe
 * @see ListeDetailServerController
 * @see ListeDetailClientController
 * @see ListController
 */
abstract class ListeDetailController<T extends GenericDomain> extends ListController<T> {

    /**
     * Affiche l'�cran d�tail de cr�ation d'un nouveau �l�ment
     *
     * @return page d�tail
     */
    @RequestMapping(value = "/new", method = RequestMethod.GET)
    public ModelAndView displayDetail() throws Exception {
        return new ModelAndView(getDetailViewName()).addObject("domain", getDomainClass().newInstance());
    }

    /**
     * Affiche l'�cran d�tail correspondant � l'�l�ment poss�dant cette id
     *
     * @param id identifiant de l'�l�ment � afficher
     * @return page d�tail
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
     * R�cup�re le nom de la vue du d�tail � afficher.
     * A surcharger si le nom de la vue est diff�rent de NOM_ENTITE + '_detail'.
     *
     * @return le nom de la vue � afficher
     */
    public String getDetailViewName() {
        return getDomainClass().getSimpleName().toLowerCase() + "_detail";
    }

    /**
     * Vue � afficher apr�s la sauvegarde de l'entit�. Par d�faut, on retourne � la liste.
     *
     * @return vue de la liste
     */
    public View getAfterSaveView() {
        return getRedirectListView();
    }
}
