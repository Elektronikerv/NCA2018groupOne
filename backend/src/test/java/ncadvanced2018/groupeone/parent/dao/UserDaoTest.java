package ncadvanced2018.groupeone.parent.dao;

import lombok.extern.slf4j.Slf4j;
import ncadvanced2018.groupeone.parent.entity.User;
import ncadvanced2018.groupeone.parent.service.UserService;
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

        userService.addUser(userForAdd);
        User userFetched = userService.getUserByEmail("junitEmail@gmail.com");

        assertThat(userForAdd.getEmail(), equalTo(userFetched.getEmail()));
    }

    @Test
    public void shouldDeleteUserFromDB(){
        User userForAdd = new User();
        userForAdd.setEmail("junitEmail@gmail.com");
        userForAdd.setFirstName("junitFirstName");
        userForAdd.setLastName("junitLastName");
        userForAdd.setPassword("junitPass");
        userForAdd.setPhoneNumber("0506078105");

        User user = userService.addUser(userForAdd);
        userService.deleteByEmail(user.getEmail());
    }

    @Test
    public void shouldFetchByEmail(){
        String email = "test@gmail.com";
        User userByEmail = userService.getUserByEmail(email);

        log.info("fetched user by email: {}", userByEmail);
        assertThat(userByEmail.getEmail(), equalTo(email));
    }

    @Test
    public void shouldFetchById(){
        Long id = 11L;
        User userById = userService.getById(id);

        log.info("fetched user by id: {}", userById);
        assertThat(userById.getId(), equalTo(11L));
    }

}
