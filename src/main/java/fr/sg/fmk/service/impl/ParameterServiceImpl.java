package fr.sg.fmk.service.impl;

import fr.sg.fmk.domain.Parameter;
import fr.sg.fmk.repository.ParameterRepository;
import fr.sg.fmk.domain.Parameter;
import fr.sg.fmk.repository.ParameterRepository;
import fr.sg.fmk.service.ParameterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Impl�mentation des services associ�s � l'entit� {@link fr.sg.fmk.domain.Parameter}
 *
 * @author jntakpe
 * @see GenericServiceImpl
 */
@Service
public final class ParameterServiceImpl extends GenericServiceImpl<Parameter> implements ParameterService {

    /**
     * Encapsulation des appels aux loggers
     */
    @Autowired
    private ParameterRepository parameterRepository;

    /**
     * {@inheritDoc}
     */
    @Override
    public CrudRepository<Parameter, Long> getRepository() {
        return parameterRepository;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional(readOnly = true)
    public Parameter findByCode(String code) {
        return parameterRepository.findByCode(code);
    }

}
