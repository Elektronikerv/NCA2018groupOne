package ncadvanced2018.groupeone.parent.dao;

import java.io.Serializable;

/**
 * Interface for generic CRUD operations.
 *
 * @param <T>  entity type.
 * @param <ID> entity identifier type.
 */
public interface CrudDao<T, ID extends Serializable> {

    /**
     * Saves a given entity.
     *
     * @param entity must not be null.
     * @return the saved entity.
     */
    T save(T entity);

    /**
     * Fetches entity by its id.
     *
     * @param id must not be null.
     * @return entity with the given id or null if none found.
     */
    T findOne(ID id);

    /**
     * Deletes a given entity.
     *
     * @param entity must not be null.
     */
    void delete(T entity);

    /**
     * Deletes the entity with the given id.
     *
     * @param id must not be null.
     */
    void delete(ID id);

    /**
     * Checks whether entity with the given id exists.
     *
     * @param id must not be null.
     * @return true if entity with the given id exists, false otherwise.
     */
    boolean exists(ID id);

    /**
     * Returns number of entities of type T.
     *
     * @return number of entities of type T.
     */
    Long count();
}
