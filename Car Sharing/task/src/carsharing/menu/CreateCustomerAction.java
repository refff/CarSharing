package carsharing.menu;

import carsharing.Customer;
import carsharing.CustomerDao;

import java.util.Scanner;

public class CreateCustomerAction implements MenuAction {
    private final Scanner scanner = new Scanner(System.in);

    @Override
    public void execute() {
        System.out.println("\nEnter the customer name:");
        String customerName = scanner.nextLine();
        int id = MainMenu.customerDao.findAll().size() + 1;

        MainMenu.customerDao.add(new Customer(id, customerName));

        System.out.println("\nThe customer was added!\n");
    }
}
