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
public class WithdrawCommand implements OperationCommand {
    private final UserService userService;
    private final AccountService accountService;

    public WithdrawCommand(UserService userService, AccountService accountService) {
        this.userService = userService;
        this.accountService = accountService;
    }

    @Autowired
    HibernateUtility hibernateUtility;

    @Override
    public void execute(Scanner scanner) {
        try {
            System.out.println("Enter account ID:");
            int accountId = Integer.parseInt(scanner.nextLine().trim());

            System.out.println("Enter amount to withdraw:");
            double withdrawAmount = Double.parseDouble(scanner.nextLine().trim());

            accountService.minusMoney(accountService.findAccountById(accountId), withdrawAmount);

            System.out.println("Amount " + withdrawAmount + " withdrawn to account ID: " + accountId);
        } catch (NumberFormatException e) {
            System.out.println("Invalid input format.");
        } catch (Exception e) {
            System.out.println("Error withdrawing to account: " + e.getMessage());
        }
    }

    @Override
    public ConsoleOperationType getOperationType() {
        return ConsoleOperationType.ACCOUNT_WITHDRAW;
    }
}
