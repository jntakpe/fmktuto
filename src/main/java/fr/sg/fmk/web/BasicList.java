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
 * Classe apportant les méthodes basiques aux contrôleurs gérant les listes du framework.
 * Cette classe n'a pas vocation a être étendue par les développeurs.
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
     * Méthode permettant de récupérer le service à utiliser.
     *
     * @return interface du service.
     */
    protected abstract GenericService<T> getService();

    /**
     * Affiche la page liste
     *
     * @return le nom de la page à afficher. Pour modifier le nom de la vue de la liste à afficher,
     * veuillez utiliser {@link fr.sg.fmk.web.ListController#getListViewName()}
     */
    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView display() {
        return new ModelAndView("lists/" + getListViewName());
    }

    /**
     * Supprime l'entité correspondante à l'identifiant lors d'un appel non-AJAX.
     * La page sera donc rechargée à l'issue de la suppression de l'entité.
     *
     * @param id identifiant de l'entité à supprimer
     * @return page à afficher
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
     * Supprime l'entité correspondante à l'identifiant lors d'un appel AJAX.
     * L'entité sera supprimée côté serveur (Database).
     * Le client(JavaScript) se chargera de la suppression de l'entité dans la table.
     *
     * @param id identifiant de l'élément à supprimer
     * @return message indiquant le résultat de la suppression
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
     * @param id identifiant de l'entité
     * @return le message confirmation de suppression de cette entité
     */
    @RequestMapping(value = "/{id}/message", method = RequestMethod.GET)
    @ResponseBody
    public ResponseMessage displayConfirmMsg(@PathVariable Long id) {
        T domain = getService().findOne(id);
        if (domain == null) return ResponseMessage.getErrorMessage(messageManager.getMessage("access.notexist", id));
        return ResponseMessage.getSuccessMessage(messageManager.getMessage("popup.confirm.delete.msg", domain));
    }

    /**
     * Récupère le nom de la vue de la liste à afficher.
     * A surcharger si le nom de la vue est différent de NOM_ENTITE + '_list'.
     *
     * @return le nom de la vue à afficher
     */
    public String getListViewName() {
        return getDomainClass().getSimpleName().toLowerCase() + "_list";
    }

    /**
     * Renvoi la page de la liste depuis un détail
     *
     * @return page liste
     */
    protected final RedirectView getRedirectListView() {
        String baseUri = this.getClass().getAnnotation(RequestMapping.class).value()[0];
        if (baseUri.charAt(0) != '/') baseUri = "/" + baseUri;
        return new RedirectView(baseUri, true);
    }

    /**
     * Méthode renvoyant l'entité de la couche domain/model
     *
     * @return ressource utilisée par le contrôlleur
     */
    protected final Class<T> getDomainClass() {
        return (Class<T>) ((ParameterizedType) this.getClass().getGenericSuperclass()).getActualTypeArguments()[0];
    }
}
