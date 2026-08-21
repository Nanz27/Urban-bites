package model;

/**
 * Abstract superclass representing a food item.
 * VegItem, NonVegItem and BeverageItem inherit from this class.
 */
public abstract class FoodItem {

    // -------------------- Attributes --------------------

    private int itemId;
    private String name;
    private double price;
    private FoodCategory category;

    // New fields
    private String description;
    private String imagePath;

    // -------------------- Constructor --------------------

    public FoodItem(int itemId,
                    String name,
                    double price,
                    FoodCategory category,
                    String description,
                    String imagePath) {

        this.itemId = itemId;
        this.name = name;
        this.price = price;
        this.category = category;
        this.description = description;
        this.imagePath = imagePath;

    }

    // -------------------- Getters --------------------

    public int getItemId() {
        return itemId;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    public FoodCategory getCategory() {
        return category;
    }

    public String getDescription() {
        return description;
    }

    public String getImagePath() {
        return imagePath;
    }

    // -------------------- Setters --------------------

    public void setName(String name) {
        this.name = name;
    }

    public void setPrice(double price) {

        if (price <= 0) {
            throw new IllegalArgumentException("Price must be greater than zero.");
        }

        this.price = price;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    // -------------------- Abstract Method --------------------

    public abstract double calculatePrice();

    // -------------------- Common Display Method --------------------

    public void displayItem() {

        System.out.println("-------------------------------------");
        System.out.println("Item ID      : " + itemId);
        System.out.println("Name         : " + name);
        System.out.println("Category     : " + category);
        System.out.printf("Price        : $%.2f%n", calculatePrice());
        System.out.println("Description  : " + description);
        System.out.println("Image        : " + imagePath);

    }

    // -------------------- toString --------------------

    @Override
    public String toString() {

        return itemId + " - "
                + name
                + " ("
                + category
                + ") - $"
                + String.format("%.2f", calculatePrice());

    }

}