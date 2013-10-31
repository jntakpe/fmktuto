package fr.sg.fmk.dto;

import org.springframework.data.domain.Sort;

/**
 * Propriété d'une colonne de table DataTables
 *
 * @author jntakpe
 */
public class ColumnProps {

    /**
     * Nom de la colonne
     */
    private String name;

    /**
     * Colonne triable
     */
    private boolean sortable;

    /**
     * Colonne triée
     */
    private boolean sorted;

    /**
     * Colonne filtrable
     */
    private boolean filterable;

    /**
     * Colonne filtrée
     */
    private boolean filtered;

    /**
     * Expression regulière
     */
    private String regex;

    /**
     * Valeur du champ de recherche
     */
    private String search;

    /**
     * Direction du tri de la colonne
     */
    private Sort.Direction sortDirection;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isSortable() {
        return sortable;
    }

    public void setSortable(boolean sortable) {
        this.sortable = sortable;
    }

    public boolean isSorted() {
        return sorted;
    }

    public void setSorted(boolean sorted) {
        this.sorted = sorted;
    }

    public boolean isFilterable() {
        return filterable;
    }

    public void setFilterable(boolean filterable) {
        this.filterable = filterable;
    }

    public boolean isFiltered() {
        return filtered;
    }

    public void setFiltered(boolean filtered) {
        this.filtered = filtered;
    }

    public String getRegex() {
        return regex;
    }

    public void setRegex(String regex) {
        this.regex = regex;
    }

    public String getSearch() {
        return search;
    }

    public void setSearch(String search) {
        this.search = search;
    }

    public Sort.Direction getSortDirection() {
        return sortDirection;
    }

    public void setSortDirection(Sort.Direction sortDirection) {
        this.sortDirection = sortDirection;
    }

    @Override
    public String toString() {
        return "ColumnProps{" +
                "name='" + name + '\'' +
                ", sortable=" + sortable +
                ", sorted=" + sorted +
                ", filterable=" + filterable +
                ", filtered=" + filtered +
                ", regex='" + regex + '\'' +
                ", search='" + search + '\'' +
                ", sortDirection=" + sortDirection +
                '}';
    }
}
