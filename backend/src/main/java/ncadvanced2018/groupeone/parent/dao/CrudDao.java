package ncadvanced2018.groupeone.parent.dao;

import java.io.Serializable;


public interface CrudDao<T, ID extends Serializable> {

    T create(T entity);

    T findById(ID id);

    T update(T entity);

    boolean delete(T entity);

    boolean delete(ID id);
}
