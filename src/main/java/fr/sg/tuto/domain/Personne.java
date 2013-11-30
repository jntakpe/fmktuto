package fr.sg.tuto.domain;

import fr.sg.fmk.domain.GenericDomain;

import javax.persistence.Entity;
import javax.persistence.SequenceGenerator;
import javax.validation.constraints.Digits;

/**
 * @author jntakpe
 */
@Entity
@SequenceGenerator(name = "SG", sequenceName = "SEQ_PERSONNES")
public class Personne extends GenericDomain {

    private String nom;

    private String prenom;

    @Digits(integer = 10, fraction = 0)
    private String telephone;

    private String email;

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Personne personne = (Personne) o;

        if (email != null ? !email.equals(personne.email) : personne.email != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return email != null ? email.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "Personne{" +
                "nom='" + nom + '\'' +
                ", prenom='" + prenom + '\'' +
                '}';
    }
}
