package fr.sg.fmk.service.impl;

import com.github.dandelion.datatables.core.ajax.ColumnDef;
import com.github.dandelion.datatables.core.ajax.DatatablesCriterias;
import fr.sg.fmk.domain.GenericDomain;
import fr.sg.fmk.service.GenericSearchService;
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

    @Override
    public Page<T> paginateAndSort(DatatablesCriterias criterias) {
        return getRepository().findAll(buildPageRequest(criterias));
    }

    /**
     * Construit un objet gérant utilisé pour faire une requête de pagination et de tri
     *
     * @param dc état de la liste DataTables
     * @return Objet contenant les informations de pagination
     */
    private Pageable buildPageRequest(DatatablesCriterias dc) {
        int pageNumber = dc.getDisplayStart() / dc.getDisplaySize();
        int size = dc.getDisplaySize();
        if (dc.hasOneSortedColumn()) return new PageRequest(pageNumber, size, resolveSort(dc.getSortingColumnDefs()));
        else return new PageRequest(pageNumber, size);
    }

    /**
     * Renvoi un objet contenant les propriétés de tri des colonnes
     *
     * @param columnDefs propriétés de chaque colonne
     * @return propriétés de tri des colonnes
     */
    private Sort resolveSort(List<ColumnDef> columnDefs) {
        List<Sort.Order> orders = new ArrayList<Sort.Order>();
        for (ColumnDef columnDef : columnDefs) {
            if (columnDef.isSortable()) {
                Sort.Direction direction;
                if (columnDef.getSortDirection().equals(ColumnDef.SortDirection.ASC))
                    direction = Sort.Direction.ASC;
                else
                    direction = Sort.Direction.DESC;
                orders.add(new Sort.Order(direction, columnDef.getName()));
            }
        }
        return new Sort(orders);
    }
}
