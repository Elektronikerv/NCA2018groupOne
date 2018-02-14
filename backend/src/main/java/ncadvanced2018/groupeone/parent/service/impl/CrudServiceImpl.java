package ncadvanced2018.groupeone.parent.service.impl;

import lombok.extern.slf4j.Slf4j;
import ncadvanced2018.groupeone.parent.dao.CrudDao;
import ncadvanced2018.groupeone.parent.entity.AbstractEntity;
import ncadvanced2018.groupeone.parent.exception.EntityExistsException;
import ncadvanced2018.groupeone.parent.exception.NoSuchEntityException;
import ncadvanced2018.groupeone.parent.service.CrudService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.Optional;

@Transactional
@Slf4j
@Service
public abstract class CrudServiceImpl<T extends AbstractEntity> implements CrudService<T, Long> {

    private final CrudDao<T, Long> crudDao;

    @Autowired
    public CrudServiceImpl(CrudDao<T, Long> crudDao) {
        this.crudDao = crudDao;
    }

    @Override
    public T create(T entity) throws EntityExistsException {
        Assert.notNull(entity, "entity must not be null");
        if (entity.isNew()) {
            throw new EntityExistsException("Failed to perform create operation. Id was not null: " + entity);
        }
        return this.crudDao.create(entity);
    }

    @Override
    public T update(T entity) throws EntityExistsException {
        Assert.notNull(entity, "entity must not be null");
        if (entity.isNew()) {
            throw new EntityExistsException("Failed to perform update operation. Id was not null: " + entity);
        }
        return this.crudDao.create(entity);
    }

    @Override
    public Optional<T> findOne(Long id) throws NoSuchEntityException {
        Assert.notNull(id, "id must not be null");
        log.debug("Searching for entity with id: {}", id);
        Optional<T> entity = this.crudDao.findById(id);
        if (!entity.isPresent()) {
            throw new NoSuchEntityException("Failed to retrieve entity with id" + id);
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
        log.debug("Deleting entity with id: {}", id);
        this.crudDao.delete(id);
    }

    @Override
    public boolean exists(Long id) {
        Assert.notNull(id, "id must not be null");
        log.debug("Checking if entity with id: {} exists", id);
        return this.crudDao.exists(id);
    }

    @Override
    public Long getCount() {
        Long count = this.crudDao.count();
        log.debug("Counted {} entities", count);
        return count;
    }
}