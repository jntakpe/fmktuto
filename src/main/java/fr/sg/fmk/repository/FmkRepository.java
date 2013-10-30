package fr.sg.fmk.repository;

import fr.sg.fmk.domain.GenericDomain;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;
import org.springframework.data.repository.NoRepositoryBean;

/**
 * Interface à étendre lors de la création d'un repository géré par le framework
 *
 * @author jntakpe
 */
@NoRepositoryBean
public interface FmkRepository<T extends GenericDomain> extends JpaRepository<T, Long>, QueryDslPredicateExecutor<T> {

}
