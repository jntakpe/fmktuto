package fr.sg.fmk.repository;

import fr.sg.fmk.domain.Parameter;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Interface permettant de g�rer l'entit� {@link fr.sg.fmk.domain.Parameter}
 *
 * @author jntakpe
 * @see org.springframework.data.jpa.repository.JpaRepository
 */
public interface ParameterRepository extends JpaRepository<Parameter, Long> {

    /**
     * R�cup�re en base de donn�es le {@link fr.sg.fmk.domain.Parameter} � partir du code du param�tre
     *
     * @param code code du paramt�re
     * @return le param�tre correspondant au code
     */
    Parameter findByCode(String code);
}
