package core.spring.springcoremoduleproject.Commands;

import core.spring.springcoremoduleproject.Entities.Account;
import core.spring.springcoremoduleproject.Entities.User;
import core.spring.springcoremoduleproject.Services.UserService;
import org.springframework.stereotype.Component;

import java.util.Scanner;

@Component
public class WithdrawCommand implements OperationCommand {
    private final UserService userService;

    public WithdrawCommand(UserService userService) {
        this.userService = userService;
    }

    @Override
    public void execute(Scanner scanner) {
        try {
            System.out.println("Enter account ID to withdraw from:");
            int accountId = Integer.parseInt(scanner.nextLine().trim());

            Account account = null;
            for (User user : userService.getUserList()) {
                for (Account acc : user.getAccountList()) {
                    if (acc.getId() == accountId) {
                        account = acc;
                        break;
                    }
                }
                if (account != null) {
                    break;
                }
            }

            if (account == null) {
                throw new IllegalArgumentException("Account with ID " + accountId + " not found.");
            }

            System.out.println("Enter amount to withdraw:");
            double amount = Double.parseDouble(scanner.nextLine().trim());
            if (amount <= 0) {
                throw new IllegalArgumentException("Amount must be positive. Entered: " + amount);
            }

            if (account.getMoneyAmount() < amount) {
                throw new IllegalArgumentException("Not enough money on account ID " + accountId);
            }

            account.minusMoney(amount);
            System.out.println("Amount " + amount + " withdrawn from account ID: " + accountId);
        } catch (NumberFormatException e) {
            System.out.println("Invalid input format.");
        } catch (Exception e) {
            System.out.println("Error withdrawing amount: " + e.getMessage());
        }
    }

    @Override
    public ConsoleOperationType getOperationType() {
        return ConsoleOperationType.ACCOUNT_WITHDRAW;
    }
}
