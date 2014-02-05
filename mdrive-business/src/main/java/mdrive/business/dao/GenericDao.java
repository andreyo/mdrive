package mdrive.business.dao;

import mdrive.business.model.ModelBean;
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
public abstract class GenericDao<T extends ModelBean> {

    private Class<T> clazz;

    {
        if (getClass().getGenericSuperclass() instanceof ParameterizedType) {
            //spring proxies
            clazz = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
        } else {
            //wicket proxies
            clazz = (Class<T>) getClass().getSuperclass();
        }
    }

    @PersistenceContext
    EntityManager entityManager;

    public boolean isManaged(T entity) {
        return entityManager.contains(entity);
    }

    public T findOne(Long id) {
        return entityManager.find(clazz, id);
    }

    public List<T> findAll() {
        List resultList = entityManager.createQuery("from " + clazz.getName()).getResultList();
        if (resultList == null) {
            resultList = Collections.emptyList();
        }
        return resultList;
    }

    public void persist(T entity) {
        entityManager.persist(entity);
    }

    public void persist(List<T> entities) {
        for (T entity : entities) {
            entityManager.persist(entity);
        }
    }

    public T merge(T entity) {
        return entityManager.merge(entity);
    }

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

    public void delete(T entity) {
        entityManager.remove(entity);
    }

    public void deleteById(long entityId) {
        T entity = findOne(entityId);
        delete(entity);
    }

    public void setAutoFlush(boolean autoFlush) {
        entityManager.setFlushMode(autoFlush ? FlushModeType.AUTO : FlushModeType.COMMIT);
    }
}
