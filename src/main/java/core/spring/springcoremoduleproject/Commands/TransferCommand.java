package core.spring.springcoremoduleproject.Commands;

import core.spring.springcoremoduleproject.Entities.Account;
import core.spring.springcoremoduleproject.Entities.User;
import core.spring.springcoremoduleproject.Services.AccountService;
import core.spring.springcoremoduleproject.Services.UserService;
import core.spring.springcoremoduleproject.Util.HibernateUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Scanner;

@Component
public class TransferCommand implements OperationCommand {
    private final UserService userService;
    private final double transferCommission;
    private final AccountService accountService;

    @Autowired
    HibernateUtility hibernateUtility;

    public TransferCommand(UserService userService, @Value("${account.transfer-commission}") double transferCommission, AccountService accountService) {
        this.userService = userService;
        this.transferCommission = transferCommission;
        this.accountService = accountService;
    }

    @Override
    public void execute(Scanner scanner) {
        try {
            System.out.println("Enter source account ID:");
            int fromAccountId = Integer.parseInt(scanner.nextLine().trim());
            Account fromAccount = accountService.findAccountById(fromAccountId);

            System.out.println("Enter target account ID:");
            int toAccountId = Integer.parseInt(scanner.nextLine().trim());
            Account toAccount = accountService.findAccountById(toAccountId);

            System.out.println("Enter amount to transfer:");
            double amount = Double.parseDouble(scanner.nextLine().trim());

            if (amount <= 0) {
                throw new IllegalArgumentException("Amount must be positive. Entered: " + amount);
            }

            if (fromAccount == null || toAccount == null) {
                throw new IllegalArgumentException("One or both accounts not found.");
            }

            boolean isSameUser = (fromAccount.getId() == toAccount.getId());
            double amountWithCommission = isSameUser ? amount : amount * (1 + (transferCommission / 100));
            if (fromAccount.getMoneyAmount() < amountWithCommission) {
                throw new IllegalArgumentException("Insufficient funds in the account (including commission) " + fromAccountId);
            }

            accountService.minusMoney(fromAccount, amountWithCommission);
            accountService.plusMoney(toAccount, amount);

            System.out.printf("Amount %.2f transferred from account ID %d to account ID %d.%n", amount, fromAccountId, toAccountId);

            /*
            * hibernateUtility.executeTransaction(session -> {
            fromAccount.setMoneyAmount(fromAccount.getMoneyAmount() - amountWithCommission);
            session.update(fromAccount);

            toAccount.setMoneyAmount(toAccount.getMoneyAmount() + amount);
            session.update(toAccount);

            // ИСКУСТВЕННО выбрасываем исключение
            throw new RuntimeException("Simulated error after transfer!!!");
        });
            * */


        } catch (NumberFormatException e) {
            System.out.println("Invalid input format.");
        } catch (Exception e) {
            System.out.println("Error transferring amount: " + e.getMessage());
        }
    }

    @Override
    public ConsoleOperationType getOperationType() {
        return ConsoleOperationType.ACCOUNT_TRANSFER;
    }
}
