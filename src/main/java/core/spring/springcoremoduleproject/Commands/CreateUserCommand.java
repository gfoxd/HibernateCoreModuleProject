package core.spring.springcoremoduleproject.Commands;

import core.spring.springcoremoduleproject.Entities.User;
import core.spring.springcoremoduleproject.Services.UserService;
import org.springframework.stereotype.Component;

import java.util.Scanner;

@Component
public class CreateUserCommand implements OperationCommand {
    private final UserService userService;

    public CreateUserCommand(UserService userService) {
        this.userService = userService;
    }

    @Override
    public void execute(Scanner scanner) {

        try {
            System.out.println("Enter login for new user:");
            String login = scanner.nextLine().trim();
            if (login.isEmpty()) {
                throw new IllegalArgumentException("Login cannot be empty.");
            }
            userService.createUser(login);
            User newUser = userService.getUserList().stream()
                    .filter(user -> user.getLogin().equals(login))
                    .findFirst()
                    .orElseThrow(() -> new RuntimeException("User not created."));
            System.out.println("User created: " + newUser);
        } catch (Exception e) {
            System.out.println("Error creating user: " + e.getMessage());
        }
    }

    @Override
    public ConsoleOperationType getOperationType() {
        return ConsoleOperationType.USER_CREATE;
    }
}
