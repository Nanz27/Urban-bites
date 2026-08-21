package model;

import java.util.ArrayList;

/**
 * Customer class inherits from Person.
 * Stores customer information, password, and order history.
 */
public class Customer extends Person {

    // -------------------- Attributes --------------------

    private ArrayList<Order> orderHistory;
    private String address;
    private String password;

    // -------------------- Constructors --------------------

    public Customer(
            int personId,
            String name,
            String phoneNumber,
            String password
    ) {
        super(personId, name, phoneNumber);
        this.password = password;
        orderHistory = new ArrayList<>();
    }

    /**
     * Overloaded constructor if you want to initialize with a custom full name immediately.
     */
    public Customer(
            int personId,
            String name,
            String fullName,
            String phoneNumber,
            String password
    ) {
        super(personId, name, phoneNumber);
        setFullName(fullName);
        this.password = password;
        orderHistory = new ArrayList<>();
    }

    // -------------------- Password Methods --------------------

    /**
     * Returns the customer's password.
     */
    public String getPassword() {

        return password;
    }

    /**
     * Changes the customer's password.
     */
    public void setPassword(String password) {

        this.password = password;
    }

    // -------------------- Order Methods --------------------

    /**
     * Add a completed order to history.
     */
    public void addOrder(Order order) {

        orderHistory.add(order);
    }

    /**
     * Display customer's order history.
     */
    public void displayOrderHistory() {

        if (orderHistory.isEmpty()) {

            System.out.println("\nNo orders found.");
            return;
        }

        System.out.println(
                "\n========== ORDER HISTORY =========="
        );

        for (Order order : orderHistory) {

            order.displayOrder();

            System.out.println();
        }
    }

    // -------------------- Getters --------------------

    /**
     * Returns the customer's order history.
     */
    public ArrayList<Order> getOrderHistory() {

        return orderHistory;
    }

    /**
     * Returns the customer's address.
     */
    public String getAddress() {

        return address;
    }

    // -------------------- Setters --------------------

    /**
     * Sets the customer's address.
     */
    public void setAddress(String address) {

        this.address = address;
    }

    // -------------------- Overridden Method --------------------

    @Override
    public void displayProfile() {

        System.out.println(
                "\n========== CUSTOMER =========="
        );

        System.out.println(
                "Customer ID : " + getPersonId()
        );

        System.out.println(
                "Username    : " + getName()
        );

        System.out.println(
                "Full Name   : " + getFullName()
        );

        System.out.println(
                "Phone       : " + getPhoneNumber()
        );

        System.out.println(
                "Total Orders: " + orderHistory.size()
        );
    }
}