package model;

/**
 * Represents a non-vegetarian food item.
 */
public class NonVegItem extends FoodItem {


    public NonVegItem(int itemId,
                      String name,
                      double price,
                      String description,
                      String imagePath) {


        super(itemId,
              name,
              price,
              FoodCategory.NON_VEG,
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