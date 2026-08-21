package model;

/**
 * Represents a vegetarian food item.
 */
public class VegItem extends FoodItem {

    public VegItem(int itemId,
                   String name,
                   double price,
                   String description,
                   String imagePath) {

        super(itemId,
              name,
              price,
              FoodCategory.VEG,
              description,
              imagePath);
    }

    @Override
    public double calculatePrice() {

        return getPrice();

    }


    @Override
    public void displayItem() {

        super.displayItem();

    }
}