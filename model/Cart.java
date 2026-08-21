package model;

import java.util.ArrayList;

public class Cart {

    private ArrayList<OrderItem> items;


    public Cart() {

        items = new ArrayList<>();

    }


    // Add food to cart
    public void addItem(FoodItem foodItem, int quantity) {

        for(OrderItem item : items) {

            if(item.getFoodItem().getItemId()
                    == foodItem.getItemId()) {

                item.setQuantity(
                        item.getQuantity() + quantity
                );

                return;

            }

        }


        items.add(
                new OrderItem(foodItem, quantity)
        );

    }


    // Remove item
    public void removeItem(int itemId) {

        items.removeIf(
                item ->
                item.getFoodItem().getItemId()
                == itemId
        );

    }


    // Get cart items
    public ArrayList<OrderItem> getItems() {

        return items;

    }


    // Calculate total
    public double getTotal() {

        double total = 0;


        for(OrderItem item : items) {

            total += item.calculateSubtotal();

        }


        return total;

    }


    // Empty cart after order
    public void clear() {

        items.clear();

    }


    public boolean isEmpty() {

        return items.isEmpty();

    }

}