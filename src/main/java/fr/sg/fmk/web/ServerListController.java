package fr.sg.fmk.web;

import com.github.dandelion.datatables.core.ajax.DatatablesCriterias;
import com.github.dandelion.datatables.core.ajax.DatatablesResponse;
import com.github.dandelion.datatables.extras.spring3.ajax.DatatablesParams;
import fr.sg.fmk.domain.GenericDomain;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Contrôlleur permettant de créer des listes gérées côté serveur.
 *
 * @author jntakpe
 */
public abstract class ServerListController<T extends GenericDomain> extends BasicList<T> {

    /**
     * Envoi des données à afficher dans la liste
     *
     * @return entités à afficher
     */
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    @ResponseBody
    public DatatablesResponse<T> listAll(@DatatablesParams DatatablesCriterias criterias) {
        PageRequest
        return getService().findAll();
    }
}
