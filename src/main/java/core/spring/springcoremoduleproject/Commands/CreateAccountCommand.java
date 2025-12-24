package core.spring.springcoremoduleproject.Commands;

import core.spring.springcoremoduleproject.Entities.Account;
import core.spring.springcoremoduleproject.Entities.User;
import core.spring.springcoremoduleproject.Services.AccountService;
import core.spring.springcoremoduleproject.Services.UserService;
import org.springframework.stereotype.Component;

import java.util.Scanner;

@Component
public class CreateAccountCommand implements OperationCommand {
    private final UserService userService;
    private final AccountService accountService;

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
            accountService.createAccount(userId);
            Account newAccount = user.getAccountList().stream()
                    .max((a1, a2) -> Integer.compare(a1.getId(), a2.getId()))
                    .orElseThrow(() -> new RuntimeException("Account not created."));
            System.out.println("New account created with ID: " + newAccount.getId() + " for user: " + user.getLogin());
        } catch (NumberFormatException e) {
            System.out.println("Invalid user ID format.");
        } catch (Exception e) {
            System.out.println("Error creating account: " + e.getMessage());
        }
    }

    @Override
    public ConsoleOperationType getOperationType() {
        return ConsoleOperationType.ACCOUNT_CREATE;
    }
}
