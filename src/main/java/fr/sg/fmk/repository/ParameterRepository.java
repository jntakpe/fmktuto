package fr.sg.fmk.repository;

import fr.sg.fmk.domain.Parameter;
import fr.sg.fmk.domain.Parameter;
import org.springframework.data.repository.CrudRepository;

/**
 * Interface permettant de g�rer l'entit� {@link fr.sg.fmk.domain.Parameter}
 *
 * @author jntakpe
 * @see org.springframework.data.repository.CrudRepository
 */
public interface ParameterRepository extends CrudRepository<Parameter, Long> {

    /**
     * R�cup�re en base de donn�es le {@link fr.sg.fmk.domain.Parameter} � partir du code du param�tre
     *
     * @param code code du paramt�re
     * @return le param�tre correspondant au code
     */
    Parameter findByCode(String code);
}
