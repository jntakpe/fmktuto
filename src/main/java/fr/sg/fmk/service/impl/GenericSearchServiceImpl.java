package fr.sg.fmk.service.impl;

import fr.sg.fmk.domain.GenericDomain;
import fr.sg.fmk.dto.ColumnProp;
import fr.sg.fmk.dto.DatatablesRequest;
import fr.sg.fmk.service.GenericSearchService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.ArrayList;
import java.util.List;

/**
 * Impl�mentation des services de filtrage et de pagination des listes
 *
 * @author jntakpe
 */
public abstract class GenericSearchServiceImpl<T extends GenericDomain> extends GenericServiceImpl<T>
        implements GenericSearchService<T> {

    @Override
    public Page<T> paginateAndSort(DatatablesRequest dr) {
        return getRepository().findAll(buildPageRequest(dr));
    }

    /**
     * Construit un objet g�rant utilis� pour faire une requ�te de pagination et de tri
     *
     * @param dr �tat de la liste DataTables
     * @return Objet contenant les informations de pagination
     */
    private Pageable buildPageRequest(DatatablesRequest dr) {
        int pageNumber = dr.getDisplayStart() / dr.getDisplaySize();
        int size = dr.getDisplaySize();
        if (dr.hasSortedColumn()) return new PageRequest(pageNumber, size, resolveSort(dr.getColumnProps()));
        else return new PageRequest(pageNumber, size);
    }

    /**
     * Renvoi un objet contenant les propri�t�s de tri des colonnes
     *
     * @param columnProps propri�t�s de chaque colonne
     * @return propri�t�s de tri des colonnes
     */
    private Sort resolveSort(List<ColumnProp> columnProps) {
        List<Sort.Order> orders = new ArrayList<Sort.Order>();
        for (ColumnProp columnProp : columnProps)
            if (columnProp.isSorted()) orders.add(new Sort.Order(columnProp.getSortDirection(), columnProp.getName()));
        return new Sort(orders);
    }
}
