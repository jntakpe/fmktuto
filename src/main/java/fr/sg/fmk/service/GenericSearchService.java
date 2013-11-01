package fr.sg.fmk.service;

import fr.sg.fmk.domain.GenericDomain;
import fr.sg.fmk.dto.DatatablesRequest;
import org.springframework.data.domain.Page;

/**
 * Service permettant en plus des services usuels de filtrer, paginer et trier les listes
 *
 * @author jntakpe
 * @see GenericService
 */
public interface GenericSearchService<T extends GenericDomain> extends GenericService<T> {

    Page<T> paginateAndSort(DatatablesRequest dr);
}
