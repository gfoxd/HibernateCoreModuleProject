package core.spring.springcoremoduleproject.Services;

import core.spring.springcoremoduleproject.Entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {
    private final List<User> userList = new ArrayList<>();
    private int nextUserId = 1;
    @Value("${account.default-amount}")
    private double defaultAmount;

    @Autowired
    private AccountService accountService;

    public void createUser(String login) {
        if (findUserByLogin(login)) {
            User newUser = new User(nextUserId++, login);

            userList.add(newUser);
            accountService.createAccountNewUser(newUser.getId());
        } else {
            System.out.println("Пользователь с таким логином уже существует");
        }
    }

    public boolean findUserByLogin(String login) {
        for (User user : userList) {
            if (user.getLogin().equals(login)) {
                return false;
            }
        }
        return true;
    }

    public User findUserById(int id) {
        for (User user : userList) {
            if (user.getId() == id) {
                return user;
            }
        }
        return null;
    }

    public List<User> getUserList() {
        return userList;
    }

    @Override
    public String toString() {
        return "UserService{" +
                "userList=" + userList +
                '}';
    }
}
