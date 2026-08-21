package model;

import java.util.ArrayList;

/**
 * Represents a customer order.
 */
public class Order {

    // -------------------- Attributes --------------------

    private int orderId;
    private Customer customer;
    private ArrayList<OrderItem> items;
    private OrderStatus status;

    // -------------------- Constructor --------------------

    public Order(int orderId, Customer customer) {

        this.orderId = orderId;
        this.customer = customer;
        this.items = new ArrayList<>();
        this.status = OrderStatus.PLACED;

    }

    // -------------------- Getters --------------------

    public int getOrderId() {
        return orderId;
    }

    public Customer getCustomer() {
        return customer;
    }

    public ArrayList<OrderItem> getItems() {
        return items;
    }

    public OrderStatus getStatus() {
        return status;
    }

    // -------------------- Add Item --------------------

    public void addItem(FoodItem foodItem, int quantity) {

        // Check whether the food item already exists
        for (OrderItem orderItem : items) {

            if (orderItem.getFoodItem().getItemId() == foodItem.getItemId()) {

                orderItem.setQuantity(
                        orderItem.getQuantity() + quantity);

                return;

            }

        }

        // Otherwise create a new OrderItem
        items.add(new OrderItem(foodItem, quantity));

    }

    // Compile-time Polymorphism (Method Overloading)
    public void addItem(OrderItem item) {

        addItem(item.getFoodItem(), item.getQuantity());

    }

    // -------------------- Remove Item --------------------

    public void removeItem(int itemId) {

        OrderItem removeItem = null;

        for(OrderItem item : items) {

            if(item.getFoodItem().getItemId() == itemId) {

                removeItem = item;
                break;

            }

        }

        if(removeItem != null) {

            items.remove(removeItem);
            System.out.println("Item removed successfully.");

        }
        else {

            System.out.println("Item not found.");

        }

    }

    // -------------------- Calculate Total --------------------

    public double calculateTotal() {

        double total = 0;

        for(OrderItem item : items) {

            total += item.calculateSubtotal();

        }

        return total;

    }

    // -------------------- Update Status --------------------

    public void updateStatus(OrderStatus status) {

        this.status = status;

    }

    // -------------------- Display Order --------------------

    public void displayOrder() {

        System.out.println("\n=================================");
        System.out.println("Order ID : " + orderId);
        System.out.println("Customer : " + customer.getName());
        System.out.println("Status   : " + status);

        System.out.println("\nItems");

        for(OrderItem item : items) {

            item.displayOrderItem();

        }

        System.out.println("---------------------------------");
        System.out.printf("Total Bill : $%.2f%n", calculateTotal());

    }

    public String getFormattedItemsList() {
        if (getItems() == null || getItems().isEmpty()) {
            return "No items";
        }
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < getItems().size(); i++) {
            sb.append(getItems().get(i).getName());
            if (i < getItems().size() - 1) {
                sb.append(", ");
            }
        }
        return sb.toString();
    }

    public int getTotalQuantity() {
        if (getItems() == null || getItems().isEmpty()) {
            return 0;
        }
        int totalQty = 0;
        for (var item : getItems()) {
            totalQty += item.getQuantity();
        }
        return totalQty;
    }

    public double getTotalPrice() {
        if (getItems() == null || getItems().isEmpty()) {
            return 0.0;
        }
        double totalPrice = 0.0;
        for (var item : getItems()) {
            totalPrice += item.getPrice() * item.getQuantity();
        }
        return totalPrice;
    }
}