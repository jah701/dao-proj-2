package internet.shop.controller;

import internet.shop.service.UserService;
import internet.shop.service.impl.UserServiceImpl;
import java.util.Scanner;

public class UserController {
    public void userVerify() {
        System.out.println("Welcome to our internet shop!"
                + "\nWould you like to create a new account?"
                + "\nEnter 'yes' if yes or 'no' if you already have one"
                + "\nEnter 'quit' to exit");
        String command;
        while (!(command = readInput()).equals("quit")) {
            if (command.equals("yes")) {
                System.out.println("To sign up enter your 'name', 'login' and 'password' below: ");
                String signUpCommand = readInput();
                UserService userService = new UserServiceImpl();
                userService.userSignUp(signUpCommand);
                break;
            }
            if (command.equals("no")) {
                System.out.println("Okay, you should have an account already."
                        + " Enter your 'login' and 'password': ");
                command = readInput();
                String[] splitLoginCommand = command.split(" ");
                String login = splitLoginCommand[0];
                String password = splitLoginCommand[1];

            }
        }
    }

    public String readInput() {
        Scanner scanner = new Scanner(System.in);
        return scanner.nextLine();
    }
}
