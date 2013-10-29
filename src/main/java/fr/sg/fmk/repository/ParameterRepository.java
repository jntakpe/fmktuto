package fr.sg.fmk.repository;

import fr.sg.fmk.domain.Parameter;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Interface permettant de gérer l'entité {@link fr.sg.fmk.domain.Parameter}
 *
 * @author jntakpe
 * @see org.springframework.data.jpa.repository.JpaRepository
 */
public interface ParameterRepository extends JpaRepository<Parameter, Long> {

    /**
     * Récupère en base de données le {@link fr.sg.fmk.domain.Parameter} à partir du code du paramètre
     *
     * @param code code du paramtère
     * @return le paramètre correspondant au code
     */
    Parameter findByCode(String code);
}
