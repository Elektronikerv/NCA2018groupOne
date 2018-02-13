package ncadvanced2018.groupeone.parent.service.impl;

import ncadvanced2018.groupeone.parent.dao.CrudDao;
import ncadvanced2018.groupeone.parent.entity.AbstractEntity;
import ncadvanced2018.groupeone.parent.service.CrudService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.Optional;

@Transactional
@Service
public class CrudServiceImpl<T extends AbstractEntity> implements CrudService<T, Long> {

    private final CrudDao<T, Long> crudDao;

    @Autowired
    public CrudServiceImpl(CrudDao<T, Long> crudDao) {
        this.crudDao = crudDao;
    }

    @Override
    public T create(T entity) {
        Assert.notNull(entity, "entity must not be null");
        if (entity.isNew()) {

        }
        return this.crudDao.save(entity);
    }

    @Override
    public T update(T entity) {
        Assert.notNull(entity, "entity must not be null");
        if (entity.isNew()) {

        }
        return this.crudDao.save(entity);
    }

    @Override
    public Optional<T> findOne(Long id) {
        Assert.notNull(id, "id must not be null");
        Optional<T> entity = this.crudDao.findOne(id);
        if (!entity.isPresent()) {

        }
        return entity;
    }

    @Override
    public void delete(T entity) {
        Assert.notNull(entity, "entity must not be null");
        this.crudDao.delete(entity);
    }

    @Override
    public void delete(Long id) {
        Assert.notNull(id, "id must not be null");
        this.crudDao.delete(id);
    }

    @Override
    public boolean exists(Long id) {
        Assert.notNull(id, "id must not be null");
        return this.crudDao.exists(id);
    }

    @Override
    public Long getCount() {
        return this.crudDao.count();
    }
}