package core.spring.springcoremoduleproject.Commands;

import core.spring.springcoremoduleproject.Entities.Account;
import core.spring.springcoremoduleproject.Entities.User;
import core.spring.springcoremoduleproject.Services.UserService;
import org.springframework.stereotype.Component;

import java.util.Scanner;

@Component
public class DepositCommand implements OperationCommand {
    private final UserService userService;

    public DepositCommand(UserService userService) {
        this.userService = userService;
    }

    @Override
    public void execute(Scanner scanner) {
        try {
            System.out.println("Enter user ID:");
            int userId = Integer.parseInt(scanner.nextLine().trim());
            User user = userService.findUserById(userId);
            if (user == null) {
                throw new IllegalArgumentException("User with ID " + userId + " not found.");
            }

            System.out.println("Enter account ID:");
            int accountId = Integer.parseInt(scanner.nextLine().trim());
            Account account = user.getAccountList().stream()
                    .filter(acc -> acc.getId() == accountId)
                    .findFirst()
                    .orElseThrow(() -> new IllegalArgumentException("Account with ID " + accountId + " not found."));

            System.out.println("Enter amount to deposit:");
            double amount = Double.parseDouble(scanner.nextLine().trim());
            if (amount <= 0) {
                throw new IllegalArgumentException("Amount must be positive. Entered: " + amount);
            }

            account.plusMoney(amount);
            System.out.println("Amount " + amount + " deposited to account ID: " + accountId);
        } catch (NumberFormatException e) {
            System.out.println("Invalid input format.");
        } catch (Exception e) {
            System.out.println("Error depositing to account: " + e.getMessage());
        }
    }

    @Override
    public ConsoleOperationType getOperationType() {
        return ConsoleOperationType.ACCOUNT_DEPOSIT;
    }
}
