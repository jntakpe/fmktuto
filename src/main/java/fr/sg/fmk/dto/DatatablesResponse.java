package fr.sg.fmk.dto;

import fr.sg.fmk.domain.GenericDomain;
import org.springframework.data.domain.Page;

/**
 * Wrapper encapsulant la réponse envoyée à une DataTable
 *
 * @author jntakpe
 */
public final class DatatablesResponse<T extends GenericDomain> {

    /**
     * Contenu de la table à afficher
     */
    private final Iterable<T> aaData;

    /**
     * Nombre total d'enregistrements sur une page (enregistrements filtrés)
     */
    private Integer iTotalRecords;

    /**
     * Nombre total d'enregistrements (total de toutes les pages)
     */
    private Long iTotalDisplayRecords;

    /**
     * Compteur d'appel au serveur
     */
    private Integer sEcho;

    /**
     * Constructeur pour les réponse avec pagination déléguée au client
     *
     * @param aaData contenu de la table
     */
    public DatatablesResponse(Iterable<T> aaData) {
        this.aaData = aaData;
    }

    /**
     * Constructeur pour les réponses avec pagination
     *
     * @param page  objet contenant les informations sur la page à afficher
     * @param sEcho compteur d'appel au serveur
     */
    public DatatablesResponse(Page<T> page, Integer sEcho) {
        this.aaData = page.getContent();
        this.iTotalRecords = page.getNumberOfElements();
        this.iTotalDisplayRecords = page.getTotalElements();
        this.sEcho = sEcho;
    }

    public Iterable<T> getAaData() {
        return aaData;
    }

    public Integer getiTotalRecords() {
        return iTotalRecords;
    }

    public void setiTotalRecords(Integer iTotalRecords) {
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
