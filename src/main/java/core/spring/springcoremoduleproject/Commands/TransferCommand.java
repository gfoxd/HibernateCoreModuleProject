package core.spring.springcoremoduleproject.Commands;

import core.spring.springcoremoduleproject.Entities.Account;
import core.spring.springcoremoduleproject.Entities.User;
import core.spring.springcoremoduleproject.Services.UserService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Scanner;

@Component
public class TransferCommand implements OperationCommand {
    private final UserService userService;
    private final double transferCommission;

    public TransferCommand(UserService userService, @Value("${account.transfer-commission}") double transferCommission) {
        this.userService = userService;
        this.transferCommission = transferCommission;
    }

    @Override
    public void execute(Scanner scanner) {
        try {
            System.out.println("Enter source account ID:");
            int fromAccountId = Integer.parseInt(scanner.nextLine().trim());

            System.out.println("Enter target account ID:");
            int toAccountId = Integer.parseInt(scanner.nextLine().trim());

            System.out.println("Enter amount to transfer:");
            double amount = Double.parseDouble(scanner.nextLine().trim());

            if (amount <= 0) {
                throw new IllegalArgumentException("Amount must be positive. Entered: " + amount);
            }

            Account fromAccount = null;
            Account toAccount = null;
            User fromUser = null;
            User toUser = null;

            for (User user : userService.getUserList()) {
                for (Account account : user.getAccountList()) {
                    if (account.getId() == fromAccountId) {
                        fromAccount = account;
                        fromUser = user;
                    }
                    if (account.getId() == toAccountId) {
                        toAccount = account;
                        toUser = user;
                    }
                }
            }

            if (fromAccount == null || toAccount == null) {
                throw new IllegalArgumentException("One or both accounts not found.");
            }

            if (fromAccount.getMoneyAmount() < amount) {
                throw new IllegalArgumentException("Not enough money on account ID " + fromAccountId);
            }

            boolean isSameUser = (fromAccount.getUserId() == toAccount.getUserId());
            double amountWithCommission = isSameUser ? amount : amount * (1 + (transferCommission / 100));

            fromAccount.minusMoney(amountWithCommission);
            toAccount.plusMoney(amount);

            System.out.printf("Amount %.2f transferred from account ID %d to account ID %d.%n", amount, fromAccountId, toAccountId);
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
