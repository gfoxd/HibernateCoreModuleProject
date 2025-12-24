package core.spring.springcoremoduleproject.Services;

import core.spring.springcoremoduleproject.Entities.Account;
import core.spring.springcoremoduleproject.Entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class AccountService {
    @Value("${account.default-amount}")
    private double defaultAmount;
    @Value("${account.transfer-commission}")
    private double commission;

    private int nextAccountId = 1;

    @Autowired
    private UserService userService;

    public void createAccountNewUser(int userId) {
        Account newAccount = new Account(nextAccountId++, userId, defaultAmount);
        User user = userService.findUserById(userId);
        if (user != null) {
            user.getAccountList().add(newAccount);
        }
    }

    public void createAccount(int userId) {
        Account newAccount = new Account(nextAccountId++, userId);
        User user = userService.findUserById(userId);
        if (user != null) {
            user.getAccountList().add(newAccount);
        }
    }

}
