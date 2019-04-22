package ua.den.model.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import ua.den.model.entity.tables.AuthorityType;
import ua.den.model.entity.tables.User;
import ua.den.model.exception.NotUniqueLoginException;
import ua.den.model.repository.AuthorityRepository;
import ua.den.model.repository.UserRepository;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final AuthorityRepository authorityRepository;

    @Autowired
    public UserService(UserRepository userRepository, AuthorityRepository authorityRepository) {
        this.userRepository = userRepository;
        this.authorityRepository = authorityRepository;
    }

    public void create(User user) {
        user.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));
        user.setAuthority(authorityRepository.findOneByRole(AuthorityType.ROLE_USER));

        userRepository.save(user);
    }

    public User getByLogin(String login) {
        return userRepository.getUserByLogin(login);
    }

    public boolean checkUniqueLogin(String login) throws NotUniqueLoginException {
        User user = getByLogin(login);

        if (user == null) {
            return true;
        }

        throw new NotUniqueLoginException(login);
    }

}
