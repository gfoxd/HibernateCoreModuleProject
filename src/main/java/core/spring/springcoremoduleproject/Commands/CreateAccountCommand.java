package core.spring.springcoremoduleproject.Commands;

import core.spring.springcoremoduleproject.Entities.Account;
import core.spring.springcoremoduleproject.Entities.User;
import core.spring.springcoremoduleproject.Services.AccountService;
import core.spring.springcoremoduleproject.Services.UserService;
import core.spring.springcoremoduleproject.Util.HibernateUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Scanner;

@Component
public class CreateAccountCommand implements OperationCommand {
    private final UserService userService;
    private final AccountService accountService;

    @Autowired
    HibernateUtility hibernateUtility;

    public CreateAccountCommand(UserService userService, AccountService accountService) {
        this.userService = userService;
        this.accountService = accountService;
    }

    @Override
    public void execute(Scanner scanner) {
        try {
            System.out.println("Enter the user ID for which to create an account:");
            int userId = Integer.parseInt(scanner.nextLine().trim());

            User user = userService.findUserById(userId);
            if (user == null) {
                throw new IllegalArgumentException("User with ID " + userId + " not found.");
            }

            accountService.createAccount(user);

            Integer newAccountId = hibernateUtility.executeTransaction(session -> {
                return (Integer) session.createNativeQuery("SELECT MAX(id) FROM Accounts")
                        .getSingleResult();
            });

            System.out.println("\nNew account created with ID: " + newAccountId + " for user: " + user.getLogin());
        } catch (NumberFormatException e) {
            System.out.println("\nInvalid user ID format.");
        } catch (Exception e) {
            System.out.println("\nError creating account: " + e.getMessage());
        }
    }

    @Override
    public ConsoleOperationType getOperationType() {
        return ConsoleOperationType.ACCOUNT_CREATE;
    }
}
