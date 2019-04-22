package ua.den.model.service;

import ua.den.model.entity.tables.User;
import ua.den.model.exception.NotUniqueLoginException;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import static org.junit.Assert.*;

public class RegisterServiceTest {
    @Mock
    private RegisterService registerService = Mockito.mock(RegisterService.class);
    private User user;
    private User existedUser;

    @Before
    public void setUp() throws Exception {
        user = new User.Builder()
                .login("Pompey")
                .password("1111111")
                .name("Name")
                .surname("Surname")
                .email("mail@gmail.com")
                .admin(false).build();

        existedUser = new User.Builder()
                .login("Godfather")
                .password("19720304")
                .name("Vito")
                .surname("Andolini")
                .email("corleone@gmail.com")
                .admin(false).build();

        Mockito.when(registerService.checkUniqueLogin(user.getLogin())).thenReturn(true);
        Mockito.when(registerService.checkUniqueLogin(existedUser.getLogin()))
                .thenThrow(new NotUniqueLoginException(existedUser.getLogin()));
    }

    @Test
    public void checkUniqueLogin() throws NotUniqueLoginException {
        assertTrue(registerService.checkUniqueLogin(user.getLogin()));
    }

    @Test (expected = NotUniqueLoginException.class)
    public void checkNotUniqueLogin() throws NotUniqueLoginException {
        assertTrue(registerService.checkUniqueLogin(existedUser.getLogin()));
    }

    @Test
    public void registerNewUser() {
        registerService.registerNewUser(user);
    }
}