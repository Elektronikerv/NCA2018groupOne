package ncadvanced2018.groupeone.parent.dao;

import lombok.extern.slf4j.Slf4j;
import ncadvanced2018.groupeone.parent.model.entity.Role;
import ncadvanced2018.groupeone.parent.model.entity.impl.RealRole;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class RoleDaoTest {

    @Autowired
    private RoleDao roleDao;

    @Test
    @Transactional
    @Rollback
    public void insertRoleTest() {
        Role expected = new RealRole();
        expected.setName("courier");
        expected.setDescription("courier");

        roleDao.create(expected);
        Role actual = roleDao.findById(expected.getId());

        Assert.assertEquals(expected, actual);
    }

    @Test
    @Transactional
    @Rollback
    public void updateRoleTest() {
        Role expected = roleDao.findById(4L);
        expected.setName("courier");
        expected.setDescription("courier");
        Role actual = roleDao.findById(4L);

        Assert.assertEquals(expected, actual);
    }

    @Test
    @Transactional
    @Rollback
    public void deleteRoleTest() {
        Role expected = new RealRole();
        expected.setName("courierTest");
        expected.setDescription("courierTest");

        Role role = roleDao.create(expected);
        roleDao.delete(role);

        Assert.assertNull(roleDao.findById(expected.getId()));
    }

    @Test
    @Transactional
    @Rollback
    public void findRoleById() {
        Long expected = 3L;
        Role roleById = roleDao.findById(expected);

        log.info("Fetched Role by id: {}", roleById);
        Assert.assertEquals(expected, roleById.getId());
    }
}