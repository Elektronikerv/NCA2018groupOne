package ncadvanced2018.groupeone.parent.dao;

import java.io.Serializable;


public interface CrudDao<T, ID extends Serializable> {

    T create(T entity);

    T findById(ID id);

    T update(T entity);

    T delete(T entity);

    T delete(ID id);

}
