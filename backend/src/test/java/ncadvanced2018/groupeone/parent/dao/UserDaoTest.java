package ncadvanced2018.groupeone.parent.dao;

import lombok.extern.slf4j.Slf4j;
import ncadvanced2018.groupeone.parent.dao.UserDao;
import ncadvanced2018.groupeone.parent.entity.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class UserDaoTest {

    @Autowired
    private UserDao userService;

    @Test
    public void shouldAddUserToDB(){
        User userForAdd = new User();
        userForAdd.setEmail("junitEmail@gmail.com");
        userForAdd.setFirstName("junitFirstName");
        userForAdd.setLastName("junitLastName");
        userForAdd.setPassword("junitPass");
        userForAdd.setPhoneNumber("0506078105");

        userService.create(userForAdd);
        User userFetched = userService.findByEmail("junitEmail@gmail.com");

        assertThat(userForAdd.getEmail(), equalTo(userFetched.getEmail()));
        userService.deleteByEmail("junitEmail@gmail.com");
    }


    @Test
    public void shouldDeleteUserFromDB(){
        User userForAdd = new User();
        userForAdd.setEmail("junitEmail@gmail.com");
        userForAdd.setFirstName("junitFirstName");
        userForAdd.setLastName("junitLastName");
        userForAdd.setPassword("junitPass");
        userForAdd.setPhoneNumber("0506078105");

        User user = userService.create(userForAdd);
        userService.deleteByEmail(user.getEmail());
    }

    @Test
    public void shouldFetchByEmail(){
        String email = "admin1@mail.com";
        User userByEmail = userService.findByEmail(email);

        log.info("fetched user by email: {}", userByEmail);
        assertThat(userByEmail.getEmail(), equalTo(email));
    }

    @Test
    public void shouldFetchById(){
        Long id = 11L;
        User userById = userService.findById(id);

        log.info("fetched user by id: {}", userById);
        assertThat(userById.getId(), equalTo(11L));
    }

}
