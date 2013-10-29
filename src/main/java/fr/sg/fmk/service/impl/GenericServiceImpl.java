package fr.sg.fmk.service.impl;

import com.github.dandelion.datatables.core.ajax.ColumnDef;
import com.github.dandelion.datatables.core.ajax.DatatablesCriterias;
import fr.sg.fmk.domain.GenericDomain;
import fr.sg.fmk.dto.Unicity;
import fr.sg.fmk.exception.BusinessCode;
import fr.sg.fmk.exception.BusinessException;
import fr.sg.fmk.service.GenericService;
import fr.sg.fmk.service.MessageManager;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.List;


/**
 * Implémentation des services usuels
 *
 * @author jntakpe
 */
@Service
public abstract class GenericServiceImpl<T extends GenericDomain> implements GenericService<T> {

    /**
     * Encapsulation des appels aux loggers
     */
    @Autowired
    protected MessageManager messageManager;

    /**
     * L'interface de la couche repository à utiliser
     */
    public abstract JpaRepository<T, Long> getRepository();

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional(readOnly = true)
    public long count() {
        return getRepository().count();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional(readOnly = true)
    public T findOne(Long id) {
        return getRepository().findOne(id);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional(readOnly = true)
    public Iterable<T> findAll() {
        return getRepository().findAll();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional(readOnly = true)
    public boolean exists(Long id) {
        return getRepository().exists(id);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional
    public void delete(Long id) {
        getRepository().delete(id);
    }

    /**
     * @{inheritDoc}
     */
    @Override
    public void delete(T entity) {
        getRepository().delete(entity);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional
    public T save(T entity) {
        return getRepository().save(entity);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional(readOnly = true)
    public boolean isAvaillable(Unicity unicity) {
        Class<T> domainClass = getDomainClass();
        String fieldName = unicity.getField();
        Field field = ReflectionUtils.findField(domainClass, fieldName);
        if (field == null) throw createBussinessException(BusinessCode.ENTITY_FIELD_MISSING, fieldName, domainClass);
        Class<?> fieldClass = ReflectionUtils.findField(getDomainClass(), fieldName).getType();
        String upperName = fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
        Class<? extends CrudRepository> repo = getRepository().getClass();
        String name = "findBy" + upperName;
        String nameIC = "findBy" + upperName + "IgnoreCase";
        Method method = ReflectionUtils.findMethod(repo, name, fieldClass);
        if (method == null && (method = ReflectionUtils.findMethod(repo, nameIC, fieldClass)) == null)
            throw createBussinessException(BusinessCode.REPOSITORY_METHOD_MISSING, fieldName, repo, name, nameIC);
        T entity = (T) ReflectionUtils.invokeMethod(method, getRepository(), unicity.getValue());
        return entity == null || entity.getId().equals(unicity.getId());
    }

    @Override
    public Pageable buildPageRequest(DatatablesCriterias dc) {
        if (dc.hasOneSortedColumn() || dc.hasOneFilteredColumn()) {
            List<Sort.Order> orders = new ArrayList<Sort.Order>();
            for (ColumnDef columnDef : dc.getColumnDefs()) {
                if (columnDef.isFilterable() && StringUtils.isNotBlank(columnDef.getSearch())) {
                    orders.add(new Sort.Order())
                }
            }
        }
        return null;
    }

    /**
     * @{inheritDoc}
     */
    @Override
    public BusinessException createBussinessException(BusinessCode code, Object... errorParams) {
        return new BusinessException(messageManager.getMessage(code, errorParams), code);
    }

    /**
     * Méthode renvoyant l'entité de la couche domain/model
     *
     * @return ressource utilisée par le contrôlleur
     */
    private Class<T> getDomainClass() {
        ParameterizedType genericSuperclass = ((ParameterizedType) this.getClass().getGenericSuperclass());
        return (Class<T>) genericSuperclass.getActualTypeArguments()[0];
    }

}
