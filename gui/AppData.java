package gui;

import model.Admin;
import model.Customer;
import service.RestaurantSystem;
import model.Cart;

public class AppData {

    public static RestaurantSystem system =
            new RestaurantSystem();

    public static Admin admin =
            new Admin(
                    1,
                    "Restaurant Manager",
                    "01700000000",
                    "admin",
                    "admin123"
            );


    public static Customer currentCustomer = null;
    public static Cart cart = new Cart();

}