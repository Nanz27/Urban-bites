package model;

/**
 * Represents a beverage item.
 */
public class BeverageItem extends FoodItem {


    public BeverageItem(int itemId,
                        String name,
                        double price,
                        String description,
                        String imagePath) {


        super(itemId,
              name,
              price,
              FoodCategory.BEVERAGE,
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