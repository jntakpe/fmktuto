package fr.sg.fmk.web;

import fr.sg.fmk.domain.GenericDomain;
import fr.sg.fmk.dto.DatatablesRequest;
import fr.sg.fmk.dto.DatatablesResponse;
import fr.sg.fmk.service.DatatablesParams;
import fr.sg.fmk.service.GenericSearchService;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Contrôlleur permettant de créer des listes gérées côté serveur.
 *
 * @author jntakpe
 */
public abstract class ServerListController<T extends GenericDomain> extends BasicList<T> {


    public abstract GenericSearchService<T> getGenericSearchService();

    /**
     * Envoi des données à afficher dans la liste
     *
     * @return entités à afficher
     */
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    @ResponseBody
    public DatatablesResponse<T> list(@DatatablesParams DatatablesRequest dr) {
        Page<T> page = getGenericSearchService().paginateAndSort(dr);
        return new DatatablesResponse<T>(page, dr.getCallCounter());
    }

}
