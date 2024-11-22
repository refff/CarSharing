package carsharing.menu;

import carsharing.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class MainMenu {
    private static final Scanner scanner = new Scanner(System.in);
    private Map<Integer, MenuAction> menuActions = new HashMap<>();
    public static CompanyDao companyDao;
    public static CarDao carDao;
    public static CustomerDao customerDao;

    public MainMenu(String dbName) {
        String url = "jdbc:h2:./src/carsharing/db/" + dbName;

        menuActions.put(1, new ManagerLoginAction());
        menuActions.put(2, new CustomerLoginAction());
        menuActions.put(3, new CreateCustomerAction());
        menuActions.put(4, new ShowAll());

        companyDao = new DbCompanyDao(url);
        carDao = new DbCarDao(url);
        customerDao = new DbCustomerDao(url);
    }

    public void displayMenu() {
        System.out.println("""
                    1. Log in as a manager
                    2. Log in as a customer
                    3. Create a customer
                    0. Exit""");
    }

    public void selectOption(int option) {
        MenuAction action = menuActions.get(option);
        if (action != null) {
            action.execute();
        } else if (option == 0) {

        } else {
            incorrectInput();
        }
    }

    public static int menuOption() {
        return Integer.parseInt(scanner.nextLine());
    }

    static void incorrectInput() {
        System.out.println("\nThere are no such option\n");
    }

    static void printElements(String listType, List list) {
        int num = 1;

        System.out.println(listType);
        for (Object element : list) {
            System.out.println(num + ". " + element);
            num++;
        }

        System.out.print(listType.equals("\nCar list:") ? "":"0. Back\n");
    }

    static void emptyListError(String str) {
        System.out.printf("\nThe %s list is empty!\n", str);
    }
}
