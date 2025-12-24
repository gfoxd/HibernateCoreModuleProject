package core.spring.springcoremoduleproject.Commands;

import core.spring.springcoremoduleproject.Entities.Account;
import core.spring.springcoremoduleproject.Entities.User;
import core.spring.springcoremoduleproject.Services.UserService;
import org.springframework.stereotype.Component;

import java.util.Scanner;

@Component
public class CloseAccountCommand implements OperationCommand {
    private final UserService userService;

    public CloseAccountCommand(UserService userService) {
        this.userService = userService;
    }

    @Override
    public void execute(Scanner scanner) {
        try {
            System.out.println("Enter account ID to close:");
            int accountId = Integer.parseInt(scanner.nextLine().trim());

            Account accountToClose = null;
            User accountOwner = null;

            for (User user : userService.getUserList()) {
                for (Account account : user.getAccountList()) {
                    if (account.getId() == accountId) {
                        accountToClose = account;
                        accountOwner = user;
                        break;
                    }
                }
                if (accountToClose != null) {
                    break;
                }
            }

            if (accountToClose == null || accountOwner == null) {
                throw new IllegalArgumentException("Account with ID " + accountId + " not found.");
            }

            if (accountOwner.getAccountList().size() <= 1) {
                throw new IllegalArgumentException("Cannot close the only account for user: " + accountOwner.getLogin());
            }

            double balance = accountToClose.getMoneyAmount();
            Account firstAccount = accountOwner.getAccountList().get(0);
            firstAccount.plusMoney(balance);
            accountOwner.getAccountList().remove(accountToClose);

            System.out.println("Account with ID " + accountId + " has been closed. Balance transferred to account ID " + firstAccount.getId());
        } catch (NumberFormatException e) {
            System.out.println("Invalid account ID format.");
        } catch (Exception e) {
            System.out.println("Error closing account: " + e.getMessage());
        }
    }

    @Override
    public ConsoleOperationType getOperationType() {
        return ConsoleOperationType.ACCOUNT_CLOSE;
    }
}
