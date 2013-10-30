package fr.sg.fmk.service;

import com.github.dandelion.datatables.core.ajax.DatatablesCriterias;
import fr.sg.fmk.domain.GenericDomain;
import org.springframework.data.domain.Page;

/**
 * Service permettant en plus des services usuels de filtrer, paginer et trier les listes
 *
 * @author jntakpe
 * @see GenericService
 */
public interface GenericSearchService<T extends GenericDomain> extends GenericService<T> {

    Page<T> paginateAndSort(DatatablesCriterias criterias);
}
