package se.ifmo.refactoring.contacts.client;

import se.ifmo.refactoring.contacts.api.AuthService;
import se.ifmo.refactoring.contacts.api.User;

import javax.ws.rs.core.Response;
import java.io.PrintWriter;
import java.util.InputMismatchException;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class AuthMenu {

    private final Scanner scanner;
    private final PrintWriter writer;
    private final AuthService authService;

    private static final String AUTHORIZATION_KEY = "Authorization";

    public AuthMenu(final Scanner scanner, final PrintWriter writer, final AuthService authService) {
        this.scanner = scanner;
        this.writer = writer;
        this.authService = authService;
    }

    public String init() {
        printMenu(true);
        while (true) {
            final AuthCommand authCommand = readMenuCommand();
            User user;
            switch (authCommand) {
                case LOGIN:
                    user = readNewUser();
                    Response response = authService.login(user);
                    if (response.getStatus() == 200){
                        return response.getHeaderString(AUTHORIZATION_KEY);
                    } else {
                        printWrongLogin();
                    }
                    break;
                case REGISTER:
                    user = readNewUser();
                    boolean result = authService.register(user);
                    if (result){
                        writer.println("Success");
                    } else {
                        writer.println("User already exists");
                    }
                    break;
                case EXIT:
                    writer.println("Goodbye!");
                    return null;
                default:
                    writer.println("Unknown state for command " + authCommand);
            }
        }
    }

    private void printMenu(final boolean isNew) {
        if (isNew) {
            writer.println("Enter the number of action and press [Enter]. Then follow instructions.\n");
        }
        writer.println("Menu:");
        writer.println("1. Login");
        writer.println("2. Register");
        writer.println("3. Exit");
    }

    private String readLine() {
        try {
            return scanner.next();
        } catch (final NoSuchElementException e) {
            printWrongInputNote();
        }
        return readLine();
    }

    private User readNewUser() {
        final User user = new User();
        writer.println("Login: ");
        user.setLogin(readLine());
        writer.println("Password: ");
        user.setPassword(readLine());

        return user;
    }

    private AuthCommand readMenuCommand() {
        try {
            final int input = scanner.nextInt();
            scanner.nextLine();
            return AuthCommand.fromInt(input);
        } catch (final InputMismatchException | IllegalArgumentException e) {
            printWrongInputNote();
        }
        return readMenuCommand();
    }

    private void printWrongInputNote() {
        writer.println("You typed something wrong. Try one more time!");
    }

    private void printWrongLogin() {
        writer.println("You typed wrong login or password");
    }

    private enum AuthCommand {
        LOGIN,
        REGISTER,
        EXIT;

        public static AuthCommand fromInt(final int number) {
            switch (number) {
                case 1:
                    return LOGIN;
                case 2:
                    return REGISTER;
                case 3:
                    return EXIT;
                default:
                    throw new IllegalArgumentException(
                            "There is no any MenuCommand mapped to number " + number);
            }
        }
    }
}
