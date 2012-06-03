package mdrive.business.dao.hibernate;

import mdrive.business.exception.ExtendedDataAccessException;
import org.hibernate.criterion.Order;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Elena
 * Date: 11.01.2011
 * Time: 8:13:06
 * To change this template use File | Settings | File Templates.
 */
public interface GenericDAO<T, ID extends Serializable> {

    //TODO: add webservices

    //TODO: use JPA instead of Hibernate

    /**
     * Return the persistent instance of the given entity class with the given identifier,
     * assuming that the instance exists.
     * <br><br>
     * You should not use this method to determine if an instance exists (use <tt>findById()</tt>
     * instead). Use this only to retrieve an instance that you assume exists, where non-existence
     * would be an actual error.
     *
     * @param id a valid identifier of an existing persistent instance of the class
     * @return the persistent instance or proxy
     * @throws Exception
     */
    T findReference(ID id);

    /**
     * Return the persistent instance of the given entity class with the given identifier,
     * or null if there is no such persistent instance. (If the instance, or a proxy for the
     * instance, is already associated with the session, return that instance or proxy.)
     *
     * @param id an identifier
     * @return a persistent instance or null
     * @throws Exception
     */
    T findById(ID id);

    /**
     * Return the persistent instance of the given entity class with the given identifier,
     * or null if there is no such persistent instance. Obtain the "upgrade" lock mode
     * if the instance exists. Objects loaded in this lock mode are
     * materialized using an SQL <tt>select ... for update</tt>.
     *
     * @param id an identifier
     * @return a persistent instance or null
     * @throws org.hibernate.HibernateException
     *
     */
    T findByIdForUpdate(ID id);

    /**
     * @param exampleInstance the entity to use as an example to find
     * @return returns the entity class persistent instances list.
     * @throws IllegalArgumentException if the specified <code>exampleInstance</code>
     *                                  is <code>null</code>
     */
    List<T> findByExample(T exampleInstance);

    /**
     * Gets list of the persistent instances of the entity class
     *
     * @param orderBy
     * @return returns the entity class persistent instances list.
     * @see #findAll(int, int, org.hibernate.criterion.Order...)
     * @see #findCount()
     */
    List<T> findAll(Order... orderBy);

    /**
     * Gets list of the persistent instances of the entity class
     *
     * @param firstResult
     * @param maxResults
     * @param orderBy
     * @return returns the entity class persistent instances list.
     * @see #findAll(Order...)
     * @see #findCount()
     */
    List<T> findAll(int firstResult, int maxResults, Order... orderBy);

    /**
     * Gets the result of the row count query, ie. <tt>count(*)</tt>
     *
     * @return returns the entity class instances count
     */
    int findCount();

    /**
     * @param exampleInstance the entity to use as an example to find
     * @return returns the entity class persistent instances list.
     * @throws IllegalArgumentException if the specified <code>exampleInstance</code>
     *                                  is <code>null</code>
     */
    List<T> findByExample(T exampleInstance, String[] excludeProperty);

    /**
     * Creates a new instance, which includes all non-null properties
     * by default, excluding the specified ones
     *
     * @param exampleInstance the entity to use as an example to find
     * @param firstResult
     * @param maxResults
     * @return returns the entity class persistent instances list.
     * @throws IllegalArgumentException if the specified <code>exampleInstance</code>
     *                                  is <code>null</code>
     */
    List<T> findByExample(T exampleInstance, String[] excludeProperty, int firstResult, int maxResults,
                          String orderByProperty, boolean sortAscending, boolean enableLike);

    /**
     * Gets the result of the row count query, ie. <tt>count(*)</tt>
     * with filtering by all non-null properties by default,
     * excluding the specified ones
     *
     * @param exampleInstance the entity to use as an example to find
     * @return returns the entity class instances count
     * @throws IllegalArgumentException if the specified <code>exampleInstance</code>
     *                                  is <code>null</code>
     */
    int findCountByExample(T exampleInstance, String[] excludeProperty);

    /**
     * Persist the given transient instance, first assigning a generated identifier. (Or
     * using the current value of the identifier property if the <tt>assigned</tt>
     * generator is used.) This operation cascades to associated instances if the
     * association is mapped with <tt>cascade="save-update"</tt>.
     *
     * @param entity a transient instance of a persistent class
     * @return the generated identifier
     * @throws mdrive.business.exception.ExtendedDataAccessException
     *
     */
    ID create(T entity) throws ExtendedDataAccessException;

    /**
     * Copy the state of the given object onto the persistent object with the same
     * identifier. If there is no persistent instance currently associated with
     * the session, it will be loaded. Return the persistent instance. If the
     * given instance is unsaved, save a copy of and return it as a newly persistent
     * instance. The given instance does not become associated with the session.
     * This operation cascades to associated instances if the association is mapped
     * with <tt>cascade="merge"</tt>.<br>
     * <br>
     * The semantics of this method are defined by JSR-220.
     *
     * @param entity a detached instance with state to be copied
     * @return an updated persistent instance
     * @throws com.training.hibernate.exception.ExtendedDataAccessException
     *
     */
    T update(T entity) throws ExtendedDataAccessException;

    /**
     * Remove a persistent instance from the datastore. The argument may be
     * an instance associated with the receiving <tt>Session</tt> or a transient
     * instance with an identifier associated with existing persistent state.
     * This operation cascades to associated instances if the association is mapped
     * with <tt>cascade="delete"</tt>.
     *
     * @param entity the instance to be removed
     * @throws ExtendedDataAccessException
     */
    void delete(T entity) throws ExtendedDataAccessException;

    /**
     * Force this session to flush. Must be called at the end of a
     * unit of work, before commiting the transaction and closing the
     * session (depending on {@link #setFlushMode flush-mode},
     * {@link org.hibernate.Transaction#commit()} calls this method).
     * <p/>
     * <i>Flushing</i> is the process of synchronizing the underlying persistent
     * store with persistable state held in memory.
     *
     * @throws Exception Indicates problems flushing the session or
     *                   talking to the database.
     */
    void flush();

    /**
     * Completely clear the session. Evict all loaded instances and cancel all pending
     * saves, updates and deletions. Do not close open iterators or instances of
     * <tt>ScrollableResults</tt>.
     */
    void clear();

    /**
     * Force initialization of a proxy or persistent collection.
     * Note: This only ensures intialization of a proxy object or collection;
     * it is not guaranteed that the elements INSIDE the collection will be initialized/materialized.
     *
     * @param proxy a persistable object, proxy, persistent collection or <tt>null</tt>
     * @throws org.hibernate.HibernateException
     *          if we can't initialize the proxy at this time, eg. the <tt>Session</tt> was closed
     */
    void initializeReference(Object ref);


    /**
     * Some operations need to invoke getSession.flush().
     * It is doing automatically if autoFlush == true;
     *
     * @return true if autoFlush
     */
    boolean isAutoFlush();

    /**
     * Some operations need to invoke getSession.flush().
     * It is doing automatically if autoFlush == true;
     *
     * @param autoFlush
     */
    void setAutoFlush(boolean autoFlush);

    /**
     * Check if entity obtained in this session
     *
     * @param o
     * @return
     */
    boolean sessionContains(Object o);

    /**
     * Bulk operation
     *
     * @param entities
     */
    void saveOrUpdateAll(Collection entities);

    /**
     *
     * @return NullObject relevant instance
     */
    T getNullObject();
}