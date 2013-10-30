package fr.sg.fmk.repository;

import fr.sg.fmk.domain.GenericDomain;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;
import org.springframework.data.repository.NoRepositoryBean;

/**
 * @author jntakpe
 */
@NoRepositoryBean
public interface WrapRepository<T extends GenericDomain> extends JpaRepository<T, Long>, QueryDslPredicateExecutor<T> {

}
