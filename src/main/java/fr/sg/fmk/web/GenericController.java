package fr.sg.fmk.web;


import fr.sg.fmk.constant.LogLevel;
import fr.sg.fmk.domain.GenericDomain;
import fr.sg.fmk.dto.ResponseMessage;
import fr.sg.fmk.service.GenericService;
import fr.sg.fmk.service.MessageManager;
import fr.sg.fmk.util.FmkUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.ParameterizedType;

/**
 * Contr�leur abstrait des �crans liste et d�tail.
 * Les m�thodes non 'private' doivent �tre surcharg�es si elles ne correspondent pas au fonctionnement souhait�.
 *
 * @author cegiraud
 * @author jntakpe
 */
public abstract class GenericController<T extends GenericDomain> {

    /**
     * Suffixe des vues de type liste
     */
    public static final String SUFFIXE_PAGE_HTML_LIST = "_list";

    /**
     * Suffixe des vues de type detail
     */
    public static final String SUFFIXE_PAGE_HTML_DETAIL = "_form";

    /**
     * URI pour les m�thodes de contr�les
     */
    public static final String CONTROL_URI = "/control";

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
     * @return le nom de la page � afficher
     */
    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView display() {
        return new ModelAndView(constructNomPageHTMLList());
    }

    /**
     * Renvoi les donn�es de la liste � afficher
     *
     * @return entit�s � afficher
     */
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    @ResponseBody
    public Iterable<T> listAll() {
        return getService().findAll();
    }

    /**
     * Affiche l'�cran d�tail de cr�ation d'un nouveau �l�ment
     *
     * @return page d�tail
     */
    @RequestMapping(value = "/new", method = RequestMethod.GET)
    public ModelAndView detail() throws Exception {
        ModelAndView mv = new ModelAndView(constructNomPageHTMLDetail());
        return mv.addObject("domain", getDomainClass().newInstance()).addObject("saveUri", constructNomPageHTMLList());
    }

    /**
     * Affiche l'�cran d�tail correspondant � l'�l�ment poss�dant cette id
     *
     * @param id identifiant de l'�l�ment � afficher
     * @return page d�tail
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ModelAndView detail(@PathVariable Long id) {
        ModelAndView mv = new ModelAndView(constructNomPageHTMLDetail());
        T domain = getService().findOne(id);
        if (domain == null) {
            mv.setViewName(constructNomPageHTMLList());
            return mv.addObject(ResponseMessage.getErrorMessage(messageManager.getMessage("access.notexist")));
        }
        return mv.addObject("domain", domain).addObject("saveUri", constructNomPageHTMLList());
    }

    /**
     * Renvoi une entit� en fonction de l'identifiant pour l'affichage de popup
     *
     * @param id identifiant de l'entit�
     * @return message indiquant si l'entit� a bien �t� r�cup�r�e
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.GET, consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseMessage populate(@PathVariable Long id) {
        T domain = getService().findOne(id);
        if (domain == null) return ResponseMessage.getErrorMessage(messageManager.getMessage("access.notexist"));
        return ResponseMessage.getSuccessMessage(domain);
    }

    /**
     * Cr�� ou modifie l'entit�
     *
     * @param domain             entit� � sauvegarder
     * @param redirectAttributes attributs de redirection lus sur la page suivante
     * @return page � afficher
     */
    @RequestMapping(method = RequestMethod.POST)
    public ModelAndView save(@ModelAttribute T domain, RedirectAttributes redirectAttributes) {
        boolean isNew = domain.getId() == null;
        T entity = getService().save(domain);
        String msg = messageManager.getMessage(isNew ? "create.success" : "update.success", entity);
        redirectAttributes.addFlashAttribute(ResponseMessage.getSuccessMessage(msg));
        String username = FmkUtils.getCurrentUsername();
        messageManager.logMessage(isNew ? "MSG00001" : "MSG00002", LogLevel.INFO, username, entity);
        return new ModelAndView(getRedirectListView());
    }

    /**
     * Cr�� ou modifie l'entit�
     *
     * @param domain entit� � sauvegarder
     * @return message indiquant le r�sultat de l'op�ration
     */
    @RequestMapping(method = RequestMethod.PUT)
    @ResponseBody
    public ResponseMessage save(@ModelAttribute T domain) {
        boolean isNew = domain.getId() == null;
        T entity = getService().save(domain);
        String username = FmkUtils.getCurrentUsername();
        String msg = messageManager.getMessage(isNew ? "create.success" : "update.success", entity);
        messageManager.logMessage(isNew ? "MSG00001" : "MSG00002", LogLevel.INFO, username, entity);
        return ResponseMessage.getSuccessMessage(msg, entity);
    }

    /**
     * Supprime l'�l�ment poss�dant l'id de la liste
     *
     * @param id identifiant de l'�l�ment � supprimer
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
     * Supprime l'�l�ment poss�dant l'id de la liste lors d'un appel AJAX
     *
     * @param id identifiant de l'�l�ment � supprimer
     * @return message indiquant le r�sultat de la suppression
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    @ResponseBody
    public ResponseMessage ajaxDelete(@PathVariable Long id) {
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
    public ResponseMessage deleteMsg(@PathVariable Long id) {
        T domain = getService().findOne(id);
        if (domain == null) return ResponseMessage.getErrorMessage(messageManager.getMessage("access.notexist", id));
        return ResponseMessage.getSuccessMessage(messageManager.getMessage("popup.confirm.delete.msg", domain));
    }

    /**
     * M�thode permettant de r�cup�rer le nom de la page � afficher.
     *
     * @return le nom de la page
     */
    protected String pageName() {
        return getDomainClass().getSimpleName().toLowerCase();
    }

    /**
     * Renvoi la base de l'url correspondant � la valeur du request mapping
     *
     * @return valeur du request mapping
     */
    protected String baseUri() {
        return this.getClass().getAnnotation(RequestMapping.class).value()[0];
    }

    /**
     * Renvoi la page de la liste depuis un d�tail
     *
     * @return page liste
     */
    private RedirectView getRedirectListView() {
        String baseUri = baseUri();
        if (baseUri.charAt(0) != '/') baseUri = "/" + baseUri;
        return new RedirectView(baseUri, true);
    }

    /**
     * M�thode renvoyant l'entit� de la couche domain/model
     *
     * @return ressource utilis�e par le contr�lleur
     */
    private Class<T> getDomainClass() {
        return (Class<T>) ((ParameterizedType) this.getClass().getGenericSuperclass()).getActualTypeArguments()[0];
    }

    /**
     * Construit le nom standard d'une page HTML liste.
     *
     * @return nom de la vue � afficher
     */
    private String constructNomPageHTMLList() {
        return pageName() + SUFFIXE_PAGE_HTML_LIST;
    }

    /**
     * Construit le nom standard d'une page HTML d�tail.
     *
     * @return nom de la vue � afficher
     */
    private String constructNomPageHTMLDetail() {
        return pageName() + SUFFIXE_PAGE_HTML_DETAIL;
    }
}