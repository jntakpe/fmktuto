package fr.sg.fmk.dto;

import fr.sg.fmk.domain.GenericDomain;

import java.util.List;

/**
 * Wrapper encapsulant la réponse envoyée à une DataTable
 *
 * @author jntakpe
 */
public class DatatablesResponse<T extends GenericDomain> {

    /**
     * Contenu de la table à afficher
     */
    private List<T> aaData;

    /**
     * Nombre total d'enregistrements sur une page (enregistrements filtrés)
     */
    private Long iTotalRecords;

    /**
     * Nombre total d'enregistrements (total de toutes les pages)
     */
    private Long iTotalDisplayRecords;

    /**
     * Compteur d'appel au serveur
     */
    private Integer sEcho;

    public List<T> getAaData() {
        return aaData;
    }

    public void setAaData(List<T> aaData) {
        this.aaData = aaData;
    }

    public Long getiTotalRecords() {
        return iTotalRecords;
    }

    public void setiTotalRecords(Long iTotalRecords) {
        this.iTotalRecords = iTotalRecords;
    }

    public Long getiTotalDisplayRecords() {
        return iTotalDisplayRecords;
    }

    public void setiTotalDisplayRecords(Long iTotalDisplayRecords) {
        this.iTotalDisplayRecords = iTotalDisplayRecords;
    }

    public Integer getsEcho() {
        return sEcho;
    }

    public void setsEcho(Integer sEcho) {
        this.sEcho = sEcho;
    }

    @Override
    public String toString() {
        return "DatatablesResponse{" +
                "aaData=" + aaData +
                ", iTotalRecords=" + iTotalRecords +
                ", iTotalDisplayRecords=" + iTotalDisplayRecords +
                ", sEcho=" + sEcho +
                '}';
    }
}
