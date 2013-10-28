package fr.sg.fmk.web;

import fr.sg.fmk.constant.LogLevel;
import fr.sg.fmk.domain.GenericDomain;
import fr.sg.fmk.dto.ResponseMessage;
import fr.sg.fmk.dto.Unicity;
import fr.sg.fmk.util.FmkUtils;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

/**
 * Contrôleur permettant d'afficher et éditer un détail chargé à partir d'une entité de liste.
 * Les contrôles seront effectués en AJAX avant ou pendant la soumission du formulaire.
 * Si les contrôles doivent être effectués côté serveur utiliser le contrôleur {@link ListeDetailServerController}
 *
 * @author jntakpe
 * @see ListeDetailController
 */
public abstract class ListeDetailClientController<T extends GenericDomain> extends ListeDetailController<T> {

    /**
     * Créé ou modifie l'entité sans contrôle. Les contrôles doivent avoir été faits côté client (JavaScript).
     *
     * @param domain             entité à sauvegarder
     * @param redirectAttributes attributs de redirection lus à la page suivante
     * @return vue à afficher après la sauvegarde de l'entité.
     *         Pour modifier la vue utiliser {@link fr.sg.fmk.web.ListeDetailController#getAfterSaveView()}
     */
    @RequestMapping(method = RequestMethod.POST)
    public ModelAndView save(@ModelAttribute T domain, RedirectAttributes redirectAttributes) {
        boolean isNew = domain.getId() == null;
        T entity = getService().save(domain);
        String msg = messageManager.getMessage(isNew ? "create.success" : "update.success", entity);
        View view = getAfterSaveView();
        ModelAndView mv = new ModelAndView(view);
        if (view instanceof RedirectView)
            redirectAttributes.addFlashAttribute(ResponseMessage.getSuccessMessage(msg));
        else
            mv.addObject(ResponseMessage.getSuccessMessage(msg));
        String username = FmkUtils.getCurrentUsername();
        messageManager.logMessage(isNew ? "MSG00001" : "MSG00002", LogLevel.INFO, username, entity);
        return mv;
    }

    /**
     * Contrôle ajax d'une contrainte d'unicité
     *
     * @param unicity objet contenant le nom du champ, l'id de l'entité et la valeur du champ surlequel contrôler
     *                l'unicité
     * @return true si cette valeur de champ est disponnible
     */
    @RequestMapping(value = {"/*/control*"})
    @ResponseBody
    public boolean unicity(@ModelAttribute Unicity unicity) {
        return getService().isAvaillable(unicity);
    }

}
