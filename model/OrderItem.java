package model;

/**
 * Represents one food item inside an order.
 */
public class OrderItem {

    // -------------------- Attributes --------------------

    private FoodItem foodItem;
    private int quantity;

    // -------------------- Constructor --------------------

    public OrderItem(FoodItem foodItem, int quantity) {
        this.foodItem = foodItem;
        this.quantity = (quantity > 0) ? quantity : 1;
    }

    // -------------------- Getters --------------------

    public FoodItem getFoodItem() {
        return foodItem;
    }

    public int getQuantity() {
        return quantity;
    }

    public String getName() {
        return foodItem != null ? foodItem.getName() : "";
    }

    public double getPrice() {
        return foodItem != null ? foodItem.calculatePrice() : 0.0;
    }

    // -------------------- Setters --------------------

    public void setQuantity(int quantity) {
        if(quantity > 0) {
            this.quantity = quantity;
        }
    }

    // -------------------- Calculate Subtotal --------------------

    public double calculateSubtotal() {
        return foodItem != null ? foodItem.calculatePrice() * quantity : 0.0;
    }

    // -------------------- Display --------------------

    public void displayOrderItem() {
        if (foodItem != null) {
            System.out.printf("%-20s x %-3d $%.2f%n",
                    foodItem.getName(),
                    quantity,
                    calculateSubtotal());
        }
    }

    // -------------------- toString --------------------

    @Override
    public String toString() {
        return foodItem != null ? foodItem.getName() + " x" + quantity : "Unknown x" + quantity;
    }
}