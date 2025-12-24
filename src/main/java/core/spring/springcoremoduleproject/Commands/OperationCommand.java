package core.spring.springcoremoduleproject.Commands;

import java.util.Scanner;

public interface OperationCommand {
    void execute(Scanner scanner);
    ConsoleOperationType getOperationType();
}