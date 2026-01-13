package core.spring.springcoremoduleproject.Services;

import core.spring.springcoremoduleproject.Entities.User;
import core.spring.springcoremoduleproject.Util.HibernateUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {

    @Autowired
    private AccountService accountService;

    @Autowired
    HibernateUtility hibernateUtility;

    public void createUser(String login) {
        hibernateUtility.executeTransaction(session -> {
            if (findUserByLogin(login) == null) {
                User newUser = new User(login);
                session.persist(newUser);
                accountService.createAccountNewUser(newUser, session); // Передаём сессию
            } else {
                System.out.println("A user with this login already exists");
            }
        });
    }

    public User findUserByLogin(String login) {
        List<User> users = hibernateUtility.executeTransaction(session -> {
            return session
                    .createQuery("FROM User u WHERE u.login = :login", User.class)
                    .setParameter("login", login)
                    .getResultList();
        });

        return users.isEmpty() ? null : users.get(0);
    }

    public User findUserById(int id) {
        List<User> users = hibernateUtility.executeTransaction(session -> {
            return session
                    .createQuery("SELECT u FROM User u LEFT JOIN FETCH u.accountList WHERE u.id = :id", User.class)
                    .setParameter("id", id)
                    .getResultList();
        });

        return users.isEmpty() ? null : users.get(0);
    }

    public List<User> getUserList() {
        List<User> users = hibernateUtility.executeTransaction(session -> {
            return session
                    .createQuery("SELECT DISTINCT u FROM User u LEFT JOIN FETCH u.accountList", User.class)
                    .getResultList();
        });

        return users;
    }

}
