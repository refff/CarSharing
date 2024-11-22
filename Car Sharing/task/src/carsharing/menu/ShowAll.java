package carsharing.menu;

public class ShowAll implements MenuAction {
    @Override
    public void execute() {
        System.out.println("\nCustomers: ");
        MainMenu.customerDao.showAll();
        System.out.println("\nCars: ");
        MainMenu.carDao.showAll();
        System.out.println();
        MainMenu.companyDao.showAll();
        System.out.println();
    }
}
