package carsharing;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.function.Function;

public class DbClient<T> {
    private final String url;

    public DbClient(String url) {
        this.url = url;
    }

    private Connection connect() throws SQLException {
        Connection connection = DriverManager.getConnection(url);
        connection.setAutoCommit(true);
        return connection;
    }

    public void run(String query) {
        try (Connection connection = connect();
             Statement statement = connection.createStatement()) {
            statement.executeUpdate(query);
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public <T> Object select(String query, String type) {
        List<T> list = (List<T>) selectForList(query, type);
        if (list.size()== 1) {
            return list.get(0);
        } else if (list.isEmpty()) {
            return null;
        } else {
            throw new IllegalStateException();
        }
    }

    public List<T> selectForList(String query, String type) {
        List<T> companies = new ArrayList<>();

        try (Connection connection = DriverManager.getConnection(url);
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {
            connection.setAutoCommit(true);

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                T t = null;

                switch (type) {
                    case "Car" -> {
                        int companyId = resultSet.getInt("COMPANY_ID");
                        t = (T) new Car(id, name, companyId);
                    }
                    case "Company" -> t = (T) new Company(name, id);
                    case "Customer" -> {
                        int rentedCarId = resultSet.getInt("RENTED_CAR_ID");
                        Customer customer = new Customer(id, name);
                        customer.setRentedCarId(rentedCarId);
                        t = (T) customer;
                    }
                }
                companies.add(t);
            }

            return companies;
        } catch (Exception e) {
            System.out.println(e);
        }

        return companies;
    }

    public void selectAll(String query) {
        try (Connection connection = DriverManager.getConnection(url);
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {

            connection.setAutoCommit(true);
            while (resultSet.next()) {
                String id = resultSet.getString("id");
                String name = resultSet.getString("name");
                String carId = resultSet.getString("RENTED_CAR_ID");
                System.out.printf("id - %s, name - %s, Rented Car Id - %s\n", id, name, carId);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void selectAllCar(String query) {
        try (Connection connection = connect();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {

            connection.setAutoCommit(true);
            while (resultSet.next()) {
                String id = resultSet.getString("id");
                String name = resultSet.getString("name");
                String carId = resultSet.getString("COMPANY_ID");
                System.out.printf("id - %s, model - %s, Company Id - %s\n", id, name, carId);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void selectAllComp(String query) {
        try (Connection connection = connect();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {

            connection.setAutoCommit(true);
            while (resultSet.next()) {
                String id = resultSet.getString("id");
                String name = resultSet.getString("name");

                System.out.printf("id - %s, name - %s\n", id, name);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public ArrayList<ArrayList<String>> numOfRows(String query) {
        ArrayList<ArrayList<String>> carList = new ArrayList<>();

        try (Connection connection = connect();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {

            while (resultSet.next()) {
                String id = resultSet.getString("id");
                String name = resultSet.getString("NAME");
                String comp_id = resultSet.getString("company_id");

                ArrayList temp = new ArrayList<>();
                temp.add(id);
                temp.add(name);
                temp.add(comp_id);

                carList.add(temp);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return carList;
    }
}
