package fr.sg.fmk.dto;

import com.github.dandelion.datatables.core.ajax.ColumnDef;

import java.util.List;

/**
 * Wrapper mappant les propriétés d'une table DataTables
 *
 * @author jntakpe
 */
public class DatatablesRequest {

    /**
     * Numéro de la première donnée de la page à afficher
     */
    private Integer displayStart;

    /**
     * Nombre de données par page
     */
    private Integer displaySize;

    /**
     * Proprietés d'une colonne
     */
    private List<ColumnProps> columnProps;

    /**
     * Proprietés d'une colonne triée
     */
    private List<ColumnProps> sortingColumnProps;

    /**
     * Compteur d'appels
     */
    private Integer callCounter;

    public Integer getDisplayStart() {
        return displayStart;
    }

    public void setDisplayStart(Integer displayStart) {
        this.displayStart = displayStart;
    }

    public Integer getDisplaySize() {
        return displaySize;
    }

    public void setDisplaySize(Integer displaySize) {
        this.displaySize = displaySize;
    }

    public List<ColumnProps> getColumnProps() {
        return columnProps;
    }

    public void setColumnProps(List<ColumnProps> columnProps) {
        this.columnProps = columnProps;
    }

    public List<ColumnProps> getSortingColumnProps() {
        return sortingColumnProps;
    }

    public void setSortingColumnProps(List<ColumnProps> sortingColumnProps) {
        this.sortingColumnProps = sortingColumnProps;
    }

    public Integer getCallCounter() {
        return callCounter;
    }

    public void setCallCounter(Integer callCounter) {
        this.callCounter = callCounter;
    }

    @Override
    public String toString() {
        return "DatatablesRequest{" +
                "displayStart=" + displayStart +
                ", displaySize=" + displaySize +
                ", columnProps=" + columnProps +
                ", sortingColumnProps=" + sortingColumnProps +
                ", callCounter=" + callCounter +
                '}';
    }
}
