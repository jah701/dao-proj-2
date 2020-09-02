package internet.shop.controller;

import internet.shop.model.Product;
import java.util.Scanner;

public class ProductController {
    public void productExecute() {
        System.out.println("Welcome to our internet shop!"
                + "\nWould you like to create a new product?"
                + "\nEnter 'yes' to create or 'no' to exit.");
        String command;
        while (!(command = readInput()).equals("quit")) {
            try {
                if (command.equals("yes")) {
                    System.out.println("Please enter product data: 'name' and 'price'");
                    String signUpCommand = readInput();
                    String[] splitSignUpCommand = signUpCommand.split(" ");
                    String name = splitSignUpCommand[0];
                    Double price = Double.parseDouble(splitSignUpCommand[1]);
                    Product product = new Product(name, price);
                    //productDao.addProduct(product);
                    System.out.println("Product created successfully");
                    break;
                }
                if (command.equals("no")) {
                    System.out.println("You've exit the app.");
                    break;
                }
            } catch (NumberFormatException | ArrayIndexOutOfBoundsException e) {
                System.out.println("Please enter correct data: [name] *space* [price]");
            }
        }
    }

    public String readInput() {
        Scanner scanner = new Scanner(System.in);
        return scanner.nextLine();
    }
}
