package mdrive.business.dao;

import java.io.Serializable;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Elena
 * Date: 11.01.2011
 * Time: 8:13:06
 * To change this template use File | Settings | File Templates.
 */
public interface GenericDao<T extends Serializable> {

    boolean isManaged(T entity);

    T findOne(Long id);

    List<T> findAll();

    void persist(T entity);

    void persist(List<T> entities);

    T merge(T entity);

    List<T> merge(List<T> entities);

    void delete(T entity);

    void deleteById(long entityId);

    void setAutoFlush(boolean autoFlush);
}