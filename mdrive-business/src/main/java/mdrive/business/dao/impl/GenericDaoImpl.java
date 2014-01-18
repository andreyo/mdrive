package mdrive.business.dao.impl;

import mdrive.business.dao.GenericDao;
import mdrive.business.model.type.ModelBean;
import org.apache.commons.collections.CollectionUtils;

import javax.persistence.EntityManager;
import javax.persistence.FlushModeType;
import javax.persistence.PersistenceContext;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Elena
 * Date: 11.01.2011
 * Time: 8:27:25
 * To change this template use File | Settings | File Templates.
 */
public abstract class GenericDaoImpl<T extends ModelBean> implements GenericDao<T> {

    private Class<T> clazz = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];

    @PersistenceContext
    EntityManager entityManager;

    @Override
    public boolean isManaged(T entity) {
        return entityManager.contains(entity);
    }


    @Override
    public T findOne(Long id) {
        return entityManager.find(clazz, id);
    }

    @Override
    public List<T> findAll() {
        List resultList = entityManager.createQuery("from " + clazz.getName()).getResultList();
        if (resultList == null) {
            resultList = Collections.emptyList();
        }
        return resultList;
    }

    @Override
    public void persist(T entity) {
        entityManager.persist(entity);
    }

    @Override
    public void persist(List<T> entities) {
        for (T entity : entities) {
            entityManager.persist(entity);
        }
    }

    @Override
    public T merge(T entity) {
        return entityManager.merge(entity);
    }

    @Override
    public List<T> merge(List<T> entities) {
        if (CollectionUtils.isEmpty(entities)) {
            return Collections.<T>emptyList();
        }
        List<T> list = new ArrayList<T>();
        for (T entity : entities) {
            list.add(entityManager.merge(entity));
        }
        return list;
    }

    @Override
    public void delete(T entity) {
        entityManager.remove(entity);
    }

    @Override
    public void deleteById(long entityId) {
        T entity = findOne(entityId);
        delete(entity);
    }

    @Override
    public void setAutoFlush(boolean autoFlush) {
        entityManager.setFlushMode(autoFlush ? FlushModeType.AUTO : FlushModeType.COMMIT);
    }
}
