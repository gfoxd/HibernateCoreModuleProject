package core.spring.springcoremoduleproject.Services;

import core.spring.springcoremoduleproject.Commands.ConsoleOperationType;
import core.spring.springcoremoduleproject.Commands.OperationCommand;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class OperationsConsoleListener {
    private final Map<ConsoleOperationType, OperationCommand> commandMap;

    public OperationsConsoleListener(List<OperationCommand> commands) {
        this.commandMap = commands.stream()
                .collect(Collectors.toMap(OperationCommand::getOperationType, Function.identity()));
    }

    public void start() {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            try {
                System.out.println("\nPlease enter one of operation type:");
                System.out.println("- USER_CREATE");
                System.out.println("- SHOW_ALL_USERS");
                System.out.println("- ACCOUNT_CREATE");
                System.out.println("- ACCOUNT_CLOSE");
                System.out.println("- ACCOUNT_DEPOSIT");
                System.out.println("- ACCOUNT_TRANSFER");
                System.out.println("- ACCOUNT_WITHDRAW");

                String command = scanner.nextLine().trim().toUpperCase();
                ConsoleOperationType operationType = ConsoleOperationType.valueOf(command);

                OperationCommand operationCommand = commandMap.get(operationType);
                if (operationCommand != null) {
                    operationCommand.execute(scanner);
                } else {
                    System.out.println("Unknown command. Try again.");
                }
            } catch (IllegalArgumentException e) {
                System.out.println("Invalid command. Try again.");
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
    }
}
