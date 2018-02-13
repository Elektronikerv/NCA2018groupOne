package ncadvanced2018.groupeone.parent.service;

import java.io.Serializable;
import java.util.Optional;

public interface CrudService<T, ID extends Serializable> {

    T create(T entity);

    T update(T entity);

    Optional<T> findOne(ID id);

    void delete(T entity);

    void delete(ID id);

    boolean exists(ID id);

    Long getCount();
}
