package fr.sg.fmk.web;

import fr.sg.fmk.constant.LogLevel;
import fr.sg.fmk.domain.GenericDomain;
import fr.sg.fmk.dto.ResponseMessage;
import fr.sg.fmk.service.GenericService;
import fr.sg.fmk.service.MessageManager;
import fr.sg.fmk.util.FmkUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import java.lang.reflect.ParameterizedType;

/**
 * Classe apportant les m�thodes basiques aux contr�leurs g�rant les listes du framework.
 * Cette classe n'a pas vocation a �tre �tendue par les d�veloppeurs.
 *
 * @author jntakpe
 */
public abstract class BasicList<T extends GenericDomain> {
    /**
     * Encapsulation des appels aux loggers
     */
    @Autowired
    protected MessageManager messageManager;

    /**
     * M�thode permettant de r�cup�rer le service � utiliser.
     *
     * @return interface du service.
     */
    protected abstract GenericService<T> getService();

    /**
     * Affiche la page liste
     *
     * @return le nom de la page � afficher. Pour modifier le nom de la vue de la liste � afficher,
     * veuillez utiliser {@link fr.sg.fmk.web.ListController#getListViewName()}
     */
    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView display() {
        return new ModelAndView("lists/" + getListViewName());
    }

    /**
     * Supprime l'entit� correspondante � l'identifiant lors d'un appel non-AJAX.
     * La page sera donc recharg�e � l'issue de la suppression de l'entit�.
     *
     * @param id identifiant de l'entit� � supprimer
     * @return page � afficher
     */
    @RequestMapping(value = "/{id}/delete", method = RequestMethod.GET)
    public ModelAndView delete(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        ModelAndView mv = new ModelAndView(getRedirectListView());
        T domain = getService().findOne(id);
        if (domain == null)
            return mv.addObject(ResponseMessage.getErrorMessage(messageManager.getMessage("access.notexist", id)));
        getService().delete(domain);
        messageManager.logMessage("MSG00003", LogLevel.INFO, FmkUtils.getCurrentUsername(), domain);
        String msg = messageManager.getMessage("delete.success", domain);
        redirectAttributes.addFlashAttribute(ResponseMessage.getSuccessMessage(msg));
        return mv;
    }

    /**
     * Supprime l'entit� correspondante � l'identifiant lors d'un appel AJAX.
     * L'entit� sera supprim�e c�t� serveur (Database).
     * Le client(JavaScript) se chargera de la suppression de l'entit� dans la table.
     *
     * @param id identifiant de l'�l�ment � supprimer
     * @return message indiquant le r�sultat de la suppression
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    @ResponseBody
    public ResponseMessage delete(@PathVariable Long id) {
        T domain = getService().findOne(id);
        if (domain == null) return ResponseMessage.getErrorMessage(messageManager.getMessage("access.notexist", id));
        getService().delete(domain);
        messageManager.logMessage("MSG00003", LogLevel.INFO, FmkUtils.getCurrentUsername(), domain);
        return ResponseMessage.getSuccessMessage(messageManager.getMessage("delete.success", domain));
    }

    /**
     * Renvoi le message de confirmation de suppression d'une ligne
     *
     * @param id identifiant de l'entit�
     * @return le message confirmation de suppression de cette entit�
     */
    @RequestMapping(value = "/{id}/message", method = RequestMethod.GET)
    @ResponseBody
    public ResponseMessage displayConfirmMsg(@PathVariable Long id) {
        T domain = getService().findOne(id);
        if (domain == null) return ResponseMessage.getErrorMessage(messageManager.getMessage("access.notexist", id));
        return ResponseMessage.getSuccessMessage(messageManager.getMessage("popup.confirm.delete.msg", domain));
    }

    /**
     * R�cup�re le nom de la vue de la liste � afficher.
     * A surcharger si le nom de la vue est diff�rent de NOM_ENTITE + '_list'.
     *
     * @return le nom de la vue � afficher
     */
    public String getListViewName() {
        return getDomainClass().getSimpleName().toLowerCase() + "_list";
    }

    /**
     * Renvoi la page de la liste depuis un d�tail
     *
     * @return page liste
     */
    protected final RedirectView getRedirectListView() {
        String baseUri = this.getClass().getAnnotation(RequestMapping.class).value()[0];
        if (baseUri.charAt(0) != '/') baseUri = "/" + baseUri;
        return new RedirectView(baseUri, true);
    }

    /**
     * M�thode renvoyant l'entit� de la couche domain/model
     *
     * @return ressource utilis�e par le contr�lleur
     */
    protected final Class<T> getDomainClass() {
        return (Class<T>) ((ParameterizedType) this.getClass().getGenericSuperclass()).getActualTypeArguments()[0];
    }
}
