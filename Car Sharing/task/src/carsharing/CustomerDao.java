package carsharing;

import java.util.List;

public interface CustomerDao {
    List<Customer> findAll();
    List<Customer> findAllById(int id);
    Customer findById(int id);
    void add(Customer customer);
    void rentCar(int carId, int customerId);
    void update(Customer customer);
    void deleteById(int id);
    void showAll();
    boolean isCarRented(int customerId);
    void returnCar(int customerId);
}
