package mdrive.business.dao.hibernate.impl;

import mdrive.business.dao.hibernate.GenericDAO;
import mdrive.business.exception.ExtendedDataAccessException;
import mdrive.business.exception.ModelConstraintViolationException;
import org.hibernate.Hibernate;
import org.hibernate.LockMode;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Example;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import javax.validation.ConstraintViolationException;
import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.Collection;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Elena
 * Date: 11.01.2011
 * Time: 8:27:25
 * To change this template use File | Settings | File Templates.
 */
public abstract class GenericDAOHibernateImpl<T, ID extends Serializable> extends HibernateDaoSupport implements
        GenericDAO<T, ID> {

    private Class<T> persistentClass;

    private boolean autoFlush = true;

    @SuppressWarnings("unchecked")
    public GenericDAOHibernateImpl() {
        this.persistentClass = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass())
                .getActualTypeArguments()[0];
    }

    /**
     * For mock unit tests
     * @param clazz
     */
    public GenericDAOHibernateImpl(Class clazz) {
        this.persistentClass = clazz;
    }

    @Autowired
    public void setFactory(SessionFactory sessionFactory) {
        super.setSessionFactory(sessionFactory);
    }

    /*
     * GenericDAOHibernate Interface Implementation
     */

    /**
     *
     * @param id a valid identifier of an existing persistent instance of the class
     * @return
     */
    @SuppressWarnings("unchecked")
    public T findReference(ID id) {
        T entityReference = (T) getHibernateTemplate().load(getPersistentClass(), id);
        return entityReference;
    }

    /**
     *
     * @param id an identifier
     * @return
     */
    public T findById(ID id) {
        if (id == null) {
            return getNullObject();
        }
        return findByIdForUpdate(id, false);
    }

    /**
     *
     * @param id an identifier
     * @return
     */
    public T findByIdForUpdate(ID id) {
        return findByIdForUpdate(id, true);
    }

    /**
     *
     * @param orderBy
     * @return
     */
    @SuppressWarnings("unchecked")
    public List<T> findAll(Order... orderBy) {
        return getHibernateTemplate().findByCriteria(createOrderedCriteria(orderBy));
    }

    /**
     *
     * @param firstResult
     * @param maxResults
     * @param orderBy
     * @return
     */
    @SuppressWarnings("unchecked")
    public List<T> findAll(int firstResult, int maxResults, Order... orderBy) {
        return getHibernateTemplate().findByCriteria(createOrderedCriteria(orderBy), firstResult, maxResults);
    }

    /**
     *
     * @return
     */
    public int findCount() {
        DetachedCriteria criteria = createCriteria();
        criteria.setProjection(Projections.rowCount());
        return DataAccessUtils.intResult(getHibernateTemplate().findByCriteria(criteria));
    }

    /**
     *
     * @param exampleInstance the entity to use as an example to find
     * @return
     */
    @SuppressWarnings("unchecked")
    public List<T> findByExample(T exampleInstance) {
        DetachedCriteria criteria = createExampleCriteria(exampleInstance, null);
        return (List<T>) getHibernateTemplate().findByCriteria(criteria);
    }

    /**
     *
     * @param exampleInstance the entity to use as an example to find
     * @param excludeProperty
     * @return
     */
    @SuppressWarnings("unchecked")
    public List<T> findByExample(T exampleInstance, String[] excludeProperty) {
        DetachedCriteria criteria = createExampleCriteria(exampleInstance, excludeProperty);
        return getHibernateTemplate().findByCriteria(criteria);
    }

    /**
     *
     * @param exampleInstance the entity to use as an example to find
     * @param excludeProperty
     * @param firstResult
     * @param maxResults
     * @param orderByProperty
     * @param sortAscending
     * @param enableLike
     * @return
     */
    @SuppressWarnings("unchecked")
    public List<T> findByExample(T exampleInstance, String[] excludeProperty, int firstResult, int maxResults,
                                 String orderByProperty, boolean sortAscending, boolean enableLike) {
        DetachedCriteria criteria = createExampleCriteria(exampleInstance,
                excludeProperty,
                orderByProperty,
                sortAscending,
                enableLike);
        return getHibernateTemplate().findByCriteria(criteria, firstResult, maxResults);
    }

    /**
     *
     * @param exampleInstance the entity to use as an example to find
     * @param excludeProperty
     * @return
     */
    public int findCountByExample(T exampleInstance, String[] excludeProperty) {
        DetachedCriteria criteria = createExampleCriteria(exampleInstance, excludeProperty);
        criteria.setProjection(Projections.rowCount());
        return DataAccessUtils.intResult(getHibernateTemplate().findByCriteria(criteria));
    }

    /**
     *
     * @param entity a transient instance of a persistent class
     * @return
     * @throws ExtendedDataAccessException
     */
    @SuppressWarnings("unchecked")
    public ID create(T entity) throws ExtendedDataAccessException {
        try {
            ID id = (ID) getHibernateTemplate().save(entity);
            checkAutoFlush();
            return id;
        } catch (DataAccessException dae) {
            throw new ExtendedDataAccessException(dae, entity, ExtendedDataAccessException.DataAccessOperation.CREATE);
        } catch (ConstraintViolationException e) {
            throw new ModelConstraintViolationException(e);
        }
    }

    /**
     *
     * @param entity a detached instance with state to be copied
     * @return
     * @throws ExtendedDataAccessException
     */
    @SuppressWarnings("unchecked")
    public T update(T entity) throws ExtendedDataAccessException {
        try {
            T res = (T) getHibernateTemplate().merge(entity);
            checkAutoFlush();
            return res;
        } catch (DataAccessException dae) {
            throw new ExtendedDataAccessException(dae, entity, ExtendedDataAccessException.DataAccessOperation.UPDATE);
        }
    }

    /**
     *
     * @param entity the instance to be removed
     * @throws ExtendedDataAccessException
     */
    public void delete(T entity) throws ExtendedDataAccessException {
        try {
            getHibernateTemplate().delete(entity);
            checkAutoFlush();
        } catch (DataAccessException dae) {
            throw new ExtendedDataAccessException(dae, entity, ExtendedDataAccessException.DataAccessOperation.DELETE);
        }
    }

    private void checkAutoFlush() {
        if (autoFlush) {
            flush();
        }
    }

    public void flush() {
        getHibernateTemplate().flush();
    }

    public void clear() {
        getHibernateTemplate().clear();
    }

    public void initializeReference(Object ref) {
        Hibernate.initialize(ref);
    }

    /*
     * Helper routine
     */

    /**
     * Use this inside subclasses as a convenience method.
     */
    @SuppressWarnings("unchecked")
    protected T findByIdForUpdate(ID id, boolean lock) {
        T entity;
        if (lock) {
            entity = (T) getHibernateTemplate().get(getPersistentClass(), id, LockMode.UPGRADE);
        } else {
            entity = (T) getHibernateTemplate().get(getPersistentClass(), id);
        }
        if (entity == null) {
            return getNullObject();
        }
        return entity;
    }

    protected DetachedCriteria createExampleCriteria(T exampleInstance, String[] excludeProperty) {
        return createExampleCriteria(exampleInstance, excludeProperty, null, true, false);
    }

    protected DetachedCriteria createExampleCriteria(T exampleInstance, String[] excludeProperty,
                                                     String orderByProperty, boolean sortAscending,
                                                     boolean enableLike) {
        DetachedCriteria criteria = createCriteria();

        // add example filter
        if (exampleInstance != null) {
            Example example = Example.create(exampleInstance).ignoreCase();
            if (excludeProperty != null) {
                for (String exclude : excludeProperty) {
                    example.excludeProperty(exclude);
                }
            }
            // add search by "like" if specified
            if (enableLike) {
                example.enableLike();
            }
            criteria.add(example);
        }

        // add order by if any
        if (orderByProperty != null && !"".equals(orderByProperty.trim())) {
            if (sortAscending) {
                criteria.addOrder(Order.asc(orderByProperty));
            } else {
                criteria.addOrder(Order.desc(orderByProperty));
            }
        }

        return criteria;
    }

    /**
     * Use this inside subclasses as a convenience method.
     */

    protected DetachedCriteria createOrderedCriteria(Order... orderBy) {
        DetachedCriteria criteria = createCriteria();
        for (Order order : orderBy) {
            criteria.addOrder(order);
        }
        return criteria;
    }

    protected DetachedCriteria createCriteria(Criterion... criterion) {
        DetachedCriteria crit = DetachedCriteria.forClass(getPersistentClass());
        for (Criterion c : criterion) {
            crit.add(c);
        }
        return crit;
    }

    /**
     *
     * @return
     */
    private Class<T> getPersistentClass() {
        return persistentClass;
    }

    /**
     *
     * @return
     */
    public boolean isAutoFlush() {
        return autoFlush;
    }

    /**
     *
     * @param autoFlush
     */
    public void setAutoFlush(boolean autoFlush) {
        this.autoFlush = autoFlush;
    }

    /**
     *
     * @param o
     * @return
     */
    @Override
    public boolean sessionContains(Object o) {
        return getHibernateTemplate().getSessionFactory().getCurrentSession().contains(o);
    }

    /**
     *
     * @param entities
     */
    @Override
    public void saveOrUpdateAll(Collection entities) {
        getHibernateTemplate().saveOrUpdateAll(entities);
    }
}