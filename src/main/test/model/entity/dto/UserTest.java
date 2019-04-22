package ua.den.model.entity.tables;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class UserTest {
    private User user;

    private String login = "Vader";
    private String password;
    private String email;
    private String name;
    private String surname;
    private boolean admin;

    @Before
    public void setUp() throws Exception {
        user = new User.Builder()
                .login(login)
                .password(password)
                .email(email)
                .name(name)
                .surname(surname)
                .admin(admin).build();
    }

    @Test
    public void getLogin() {
        assertEquals(login, user.getLogin());
    }

    @Test
    public void setLogin() {
        login = "111Kirill1111";

        user.setLogin(login);

        assertEquals(login, user.getLogin());
    }

    @Test
    public void getPassword() {
        assertEquals(password, user.getPassword());
    }

    @Test
    public void setPassword() {
        password = "o4eHbXopoIIIuuPass";

        user.setPassword(password);

        assertEquals(password, user.getPassword());
    }

    @Test
    public void getEmail() {
        assertEquals(email, user.getEmail());
    }

    @Test
    public void setEmail() {
        email = "work@gmail.com";

        user.setEmail(email);

        assertEquals(email, user.getEmail());
    }

    @Test
    public void getName() {
        assertEquals(name, user.getName());
    }

    @Test
    public void setName() {
        name = "Ivan";

        user.setName(name);

        assertEquals(name, user.getName());
    }

    @Test
    public void getSurname() {
        assertEquals(surname, user.getSurname());
    }

    @Test
    public void setSurname() {
        surname = "Ivanov";

        user.setSurname(surname);

        assertEquals(surname, user.getSurname());
    }

    @Test
    public void isAdmin() {
        assertEquals(admin, user.isAdmin());
    }

    @Test
    public void setAdmin() {
        admin = true;

        user.setAdmin(admin);

        assertEquals(admin, user.isAdmin());
    }

    @Test
    public void equals() {
        assertEquals(user, user);
    }
}