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
public class DepositCommand implements OperationCommand {
    private final UserService userService;
    private final AccountService accountService;

    @Autowired
    HibernateUtility hibernateUtility;

    public DepositCommand(UserService userService, AccountService accountService) {
        this.userService = userService;
        this.accountService = accountService;
    }

    @Override
    public void execute(Scanner scanner) {
        try {
            System.out.println("Enter account ID:");
            int accountId = Integer.parseInt(scanner.nextLine().trim());

            System.out.println("Enter amount to deposit:");
            double deposit = Double.parseDouble(scanner.nextLine().trim());

            if (deposit <= 0) {
                throw new IllegalArgumentException("Amount must be positive. Entered: " + deposit);
            }

            accountService.plusMoney(accountService.findAccountById(accountId), deposit);

            System.out.println("Amount " + deposit + " deposited to account ID: " + accountId);
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
