package ua.den.model.service;

import ua.den.model.entity.dto.User;
import ua.den.model.exception.InvalidLoginOrPasswordException;
import ua.den.model.exception.NoSuchIdException;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import static org.junit.Assert.assertEquals;

public class LoginServiceTest {
    @Mock
    private LoginService loginService = Mockito.mock(LoginService.class);
    private User user;
    private User wrongUser;

    @Before
    public void setUp() throws Exception {
        user = new User.Builder()
                .login("Homeboy")
                .password("zxcvbnm")
                .email("raw@mail.ua")
                .name("John")
                .surname("Smith")
                .admin(false).build();

        wrongUser = new User.Builder()
                .login("victim")
                .password("a5a").build();

        Mockito.when(loginService.checkUserData(user)).thenReturn(user);
        Mockito.when(loginService.checkUserData(wrongUser))
                .thenThrow(new NoSuchIdException(wrongUser.getLogin()));
    }

    @Test
    public void checkUserData() throws NoSuchIdException, InvalidLoginOrPasswordException {
        assertEquals(user, loginService.checkUserData(user));
    }

    @Test(expected = NoSuchIdException.class)
    public void checkWrongUserData() throws NoSuchIdException, InvalidLoginOrPasswordException {
        assertEquals(wrongUser, loginService.checkUserData(wrongUser));
    }
}