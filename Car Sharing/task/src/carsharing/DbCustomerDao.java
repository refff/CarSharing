package carsharing;

import java.util.List;

public class DbCustomerDao implements CustomerDao{
    private static final String CREATE_TABLE = "Create table if not exists customer" +
            "(id integer primary key AUTO_INCREMENT, " +
            "name varchar not null unique," +
            "RENTED_CAR_ID integer DEFAULT NULL," +
            "CONSTRAINT FK_CAR_ID FOREIGN KEY (RENTED_CAR_ID)" +
            "REFERENCES CAR(ID));";
    private static final String SELECT_ALL_BY_ID = "Select * from customer where rented_car_id = %d;";
    private static final String SELECT_ALL = "Select * from customer;";
    private static final String SELECT = "Select * from customer where id = %d;";
    private static final String INSERT_DATA = "INSERT INTO customer VALUES (%d, '%s', null);";
    private static final String RENT_CAR = "UPDATE CUSTOMER SET RENTED_CAR_ID = %d where ID = %d;";
    private static final String UPDATE_DATA = "update customer set name = %s where id = %d;";
    private static final String DELETE_DATA = "delete from customer where id = %d;";

    private final DbClient dbClient;

    public DbCustomerDao(String url) {
        dbClient = new DbClient(url);
        dbClient.run(CREATE_TABLE);
    }

    @Override
    public List<Customer> findAll() {
        return dbClient.selectForList(SELECT_ALL, "Customer");
    }

    @Override
    public List<Customer> findAllById(int id) {
        List<Customer> customersCarList = dbClient.selectForList(String.format(SELECT_ALL_BY_ID, id), "Customer");
        return customersCarList;
    }

    @Override
    public Customer findById(int id) {
        Customer customer = (Customer) dbClient.select(String.format(SELECT, id), "Customer");
        return customer;
    }

    @Override
    public void add(Customer customer) {
        dbClient.run(String.format(INSERT_DATA, customer.getId(), customer.getName()));
    }

    @Override
    public void rentCar(int carId, int customerId) {
        dbClient.run(String.format(RENT_CAR, carId, customerId));
    }

    @Override
    public void update(Customer customer) {
        dbClient.run(String.format(UPDATE_DATA, customer.getName(), customer.getId()));
    }

    @Override
    public void deleteById(int id) {
        dbClient.run(String.format(DELETE_DATA, id));
    }

    @Override
    public void showAll() {
        dbClient.selectAll("Select * from customer;");
    }

    @Override
    public boolean isCarRented(int customerId) {
        String query = "Select * from customer where id = " + customerId;
        Customer customer = (Customer) dbClient.select(query, "Customer");
        int id = customer.getRentedCarId();

        return id != 0;
    }

    @Override
    public void returnCar(int customerId) {
        dbClient.run("update customer set rented_car_id = null where id = " + customerId + ";");
    }
}
