package core.spring.springcoremoduleproject.Commands;

import core.spring.springcoremoduleproject.Entities.User;
import core.spring.springcoremoduleproject.Services.UserService;
import org.springframework.stereotype.Component;

import java.util.Scanner;

@Component
public class ShowAllUsersCommand implements OperationCommand {
    private final UserService userService;

    public ShowAllUsersCommand(UserService userService) {
        this.userService = userService;
    }

    @Override
    public void execute(Scanner scanner) {
        try {
            if (userService.getUserList().isEmpty()) {
                System.out.println("No users found.");
            } else {
                boolean showTitle = true;

                for (User user : userService.getUserList()) {

                    if (showTitle) {
                        System.out.println("\nList of all users:");
                        showTitle = false;
                    }

                    System.out.println(user);
                }

            }
        } catch (Exception e) {
            System.out.println("Error showing users: " + e.getMessage());
        }
    }

    @Override
    public ConsoleOperationType getOperationType() {
        return ConsoleOperationType.SHOW_ALL_USERS;
    }
}
