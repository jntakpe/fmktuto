package fr.sg.fmk.web;

import com.github.dandelion.datatables.core.ajax.DataSet;
import com.github.dandelion.datatables.core.ajax.DatatablesCriterias;
import com.github.dandelion.datatables.core.ajax.DatatablesResponse;
import com.github.dandelion.datatables.extras.spring3.ajax.DatatablesParams;
import fr.sg.fmk.domain.GenericDomain;
import fr.sg.fmk.service.GenericSearchService;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;

/**
 * Contr�lleur permettant de cr�er des listes g�r�es c�t� serveur.
 *
 * @author jntakpe
 */
public abstract class ServerListController<T extends GenericDomain> extends BasicList<T> {


    public abstract GenericSearchService<T> getGenericSearchService();

    /**
     * Envoi des donn�es � afficher dans la liste
     *
     * @return entit�s � afficher
     */
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    @ResponseBody
    public DatatablesResponse<T> paginateAndSort(@DatatablesParams DatatablesCriterias criterias) {
        Page<T> page = getGenericSearchService().paginateAndSort(criterias);
        List<T> list = new ArrayList<T>();
        for (T t : page.getContent()) {
            list.add(t);
        }
        DataSet<T> dataSet = new DataSet<T>(list, (long) page.getNumberOfElements(), page.getTotalElements());
        DatatablesResponse<T> response = DatatablesResponse.build(dataSet, criterias);
        return response;
    }
}
