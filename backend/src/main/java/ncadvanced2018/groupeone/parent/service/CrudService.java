package ncadvanced2018.groupeone.parent.service;

import ncadvanced2018.groupeone.parent.exception.EntityExistsException;
import ncadvanced2018.groupeone.parent.exception.NoSuchEntityException;

import java.io.Serializable;
import java.util.Optional;

public interface CrudService<T, ID extends Serializable> {

    T create(T entity) throws EntityExistsException;

    T update(T entity) throws EntityExistsException;

    Optional<T> findOne(ID id) throws NoSuchEntityException;

    void delete(T entity);

    void delete(ID id);

    boolean exists(ID id);

    Long getCount();
}
