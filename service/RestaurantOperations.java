package service;

import model.Customer;
import model.FoodItem;
import model.Order;
import model.OrderStatus;

import exception.DuplicateCustomerException;
import exception.DuplicateFoodException;
import exception.EmptyOrderException;
import exception.FoodNotFoundException;
import exception.OrderNotFoundException;

public interface RestaurantOperations {

    // ================= Menu =================

    void addFoodItem(FoodItem item)
            throws DuplicateFoodException;

    void removeFoodItem(int itemId)
            throws FoodNotFoundException;

    void updateFoodPrice(int itemId,
                         double newPrice)
            throws FoodNotFoundException;

    void displayMenu();

    FoodItem searchFood(int itemId);

    FoodItem searchFood(String name);
    
    void updateFoodItem(int itemId,
            FoodItem newItem)
throws FoodNotFoundException;

    // ================= Customer =================

    void registerCustomer(Customer customer)
            throws DuplicateCustomerException;

    Customer searchCustomer(int customerId);

    Customer searchCustomer(String name);

    // ================= Order =================

    void placeOrder(Order order)
            throws EmptyOrderException;

    Order searchOrder(int orderId);

    void updateOrderStatus(int orderId,
                           OrderStatus status)
            throws OrderNotFoundException;

    void displayAllOrders();

    // ================= File =================

    void saveAll();

    void loadAll();

}