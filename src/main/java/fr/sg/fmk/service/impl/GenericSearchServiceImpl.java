package fr.sg.fmk.service.impl;

import com.github.dandelion.datatables.core.ajax.ColumnDef;
import com.github.dandelion.datatables.core.ajax.DatatablesCriterias;
import fr.sg.fmk.domain.GenericDomain;
import fr.sg.fmk.service.GenericSearchService;
import fr.sg.fmk.repository.WrapRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.ArrayList;
import java.util.List;

/**
 * Implémentation des services de filtrage et de pagination des listes
 *
 * @author jntakpe
 */
public abstract class GenericSearchServiceImpl<T extends GenericDomain> extends GenericServiceImpl<T>
        implements GenericSearchService<T> {

    /**
     * Renvoi le repository à utiliser pour filtrer et paginer la liste
     * @return repository de filtrage et pagination
     */
    public abstract WrapRepository<T> getWrapRepository();

    @Override
    public Page<T> paginateAndSort(DatatablesCriterias criterias) {
        return getWrapRepository().findAll(buildPageRequest(criterias));
    }

    /**
     * Construit un objet gérant utilisé pour faire une requête de pagination
     * @param dc état de la liste DataTables
     * @return Objet contenant les informations de pagination
     */
    private Pageable buildPageRequest(DatatablesCriterias dc) {
        if (dc.hasOneSortedColumn() || dc.hasOneFilteredColumn()) {
            List<Sort.Order> orders = new ArrayList<Sort.Order>();
            for (ColumnDef columnDef : dc.getColumnDefs()) {
                if (columnDef.isSortable() && columnDef.isSorted()) {
                    Sort.Direction direction;
                    if (columnDef.getSortDirection().equals(ColumnDef.SortDirection.ASC))
                        direction = Sort.Direction.ASC;
                    else
                        direction = Sort.Direction.DESC;
                    orders.add(new Sort.Order(direction, columnDef.getName()));
                }
            }
        }
        return new PageRequest(dc.getDisplayStart(), dc.getDisplayStart());
    }
}
