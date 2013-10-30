package fr.sg.fmk.repository;

import fr.sg.fmk.domain.GenericDomain;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;
import org.springframework.data.repository.NoRepositoryBean;

/**
 * Interface � �tendre lors de la cr�ation d'un repository g�r� par le framework
 *
 * @author jntakpe
 */
@NoRepositoryBean
public interface FmkRepository<T extends GenericDomain> extends JpaRepository<T, Long>, QueryDslPredicateExecutor<T> {

}
