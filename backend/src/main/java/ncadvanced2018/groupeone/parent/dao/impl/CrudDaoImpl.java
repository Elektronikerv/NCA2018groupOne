package ncadvanced2018.groupeone.parent.dao.impl;

import ncadvanced2018.groupeone.parent.dao.CrudDao;
import ncadvanced2018.groupeone.parent.entity.AbstractEntity;

/**
 * Basic implementation of {@link CrudDao} interface.
 *
 * @param <T> entity type.
 */
public abstract class CrudDaoImpl<T extends AbstractEntity> implements CrudDao<T, Long> {

    @Override
    public T save(T entity) {
        return null;
    }

    @Override
    public T findOne(Long id) {
        return null;
    }

    @Override
    public void delete(T entity) {

    }

    @Override
    public void delete(Long id) {

    }

    @Override
    public boolean exists(Long id) {
        return false;
    }

    @Override
    public Long count() {
        return null;
    }
}
