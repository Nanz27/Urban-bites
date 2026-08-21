package service;

import model.*;
import exception.*;

import java.io.*;
import java.util.ArrayList;

public class RestaurantSystem implements RestaurantOperations {

    // ===========================
    // Attributes
    // ===========================

    private ArrayList<FoodItem> menu;
    private ArrayList<Customer> customers;
    private ArrayList<Order> orders;

    // Track the highest ID ever assigned, separately from the current
    // list contents. Using max(current items)+1 alone would let a
    // removed item's ID get reused by a brand-new item, which then
    // corrupts any historical order still referencing the old item.
    private int maxFoodId = 0;
    private int maxCustomerId = 0;
    private int maxOrderId = 0;

    private static final String MENU_FILE = "src/data/menu.txt";
    private static final String CUSTOMERS_FILE = "src/data/customers.txt";
    private static final String ORDERS_FILE = "src/data/orders.txt";

    // ===========================
    // Constructor
    // ===========================

    public RestaurantSystem() {

        menu = new ArrayList<>();
        customers = new ArrayList<>();
        orders = new ArrayList<>();

    }

    // ===========================
    // Getter Methods
    // ===========================

    public ArrayList<FoodItem> getMenu() {
        return menu;
    }

    public ArrayList<Customer> getCustomers() {
        return customers;
    }

    public ArrayList<Order> getOrders() {
        return orders;
    }

    // ===========================
    // Auto ID Generation
    // (always continues after the highest existing ID —
    //  IDs only ever grow, never get reused)
    // ===========================

    public int nextCustomerId() {

        return maxCustomerId + 1;

    }

    public int nextFoodItemId() {

        return maxFoodId + 1;

    }

    public int nextOrderId() {

        return maxOrderId + 1;

    }

    // ===========================
    // Menu Methods
    // ===========================

    @Override
    public void addFoodItem(FoodItem item)
            throws DuplicateFoodException {

        if (searchFood(item.getItemId()) != null) {

            throw new DuplicateFoodException(
                    "A food item with ID " + item.getItemId() + " already exists.");

        }

        menu.add(item);

        if (item.getItemId() > maxFoodId) {
            maxFoodId = item.getItemId();
        }

    }

    @Override
    public void removeFoodItem(int itemId)
            throws FoodNotFoundException {

        FoodItem item = searchFood(itemId);

        if(item == null){

            throw new FoodNotFoundException(
                    "Food item not found.");

        }

        menu.remove(item);

    }

    @Override
    public void updateFoodPrice(int itemId,
                                double newPrice)
            throws FoodNotFoundException {

        FoodItem item = searchFood(itemId);

        if(item == null){

            throw new FoodNotFoundException(
                    "Food item not found.");

        }

        item.setPrice(newPrice);

    }
    
    @Override
    public void updateFoodItem(int itemId,
                               FoodItem newItem)
            throws FoodNotFoundException {

        for (int i = 0; i < menu.size(); i++) {

            if (menu.get(i).getItemId() == itemId) {

                menu.set(i, newItem);
                return;

            }

        }

        throw new FoodNotFoundException(
                "Food item not found."
        );

    }

    @Override
    public void displayMenu() {

        if(menu.isEmpty()){

            System.out.println("\nMenu is empty.");
            return;

        }

        System.out.println("\n=========== MENU ===========");

        for(FoodItem item : menu){

            item.displayItem();
            System.out.println();

        }

    }

    // ===========================
    // Search Food (Overloading)
    // ===========================

    @Override
    public FoodItem searchFood(int itemId) {

        for(FoodItem item : menu){

            if(item.getItemId()==itemId){

                return item;

            }

        }

        return null;

    }

    @Override
    public FoodItem searchFood(String name) {

        for(FoodItem item : menu){

            if(item.getName().equalsIgnoreCase(name)){

                return item;

            }

        }

        return null;

    }

    // ===========================
    // Customer Methods
    // ===========================

    @Override
    public void registerCustomer(Customer customer)
            throws DuplicateCustomerException {

        if(searchCustomer(customer.getPersonId()) != null){

            throw new DuplicateCustomerException(
                    "Customer already exists.");

        }

        customers.add(customer);

        if (customer.getPersonId() > maxCustomerId) {
            maxCustomerId = customer.getPersonId();
        }

    }

    @Override
    public Customer searchCustomer(int customerId) {

        for(Customer customer : customers){

            if(customer.getPersonId()==customerId){

                return customer;

            }

        }

        return null;

    }

    @Override
    public Customer searchCustomer(String name) {

        for(Customer customer : customers){

            if(customer.getName().equalsIgnoreCase(name)){

                return customer;

            }

        }

        return null;

    }
    // ======================================
    // Order Methods
    // ======================================

    @Override
    public void placeOrder(Order order)
            throws EmptyOrderException {

        if (order.getItems().isEmpty()) {

            throw new EmptyOrderException(
                    "Cannot place an empty order.");

        }

        orders.add(order);

        if (order.getOrderId() > maxOrderId) {
            maxOrderId = order.getOrderId();
        }

        // Store in customer's history
        order.getCustomer().addOrder(order);

    }

    @Override
    public Order searchOrder(int orderId) {

        for (Order order : orders) {

            if (order.getOrderId() == orderId) {

                return order;

            }

        }

        return null;

    }

    @Override
    public void updateOrderStatus(int orderId,
                                  OrderStatus status)
            throws OrderNotFoundException {

        Order order = searchOrder(orderId);

        if (order == null) {

            throw new OrderNotFoundException(
                    "Order not found.");

        }

        order.updateStatus(status);

        System.out.println("Order status updated.");

    }

    // ======================================
    // Display All Orders
    // ======================================

    @Override
    public void displayAllOrders() {

        if (orders.isEmpty()) {

            System.out.println("\nNo orders available.");
            return;

        }

        System.out.println("\n========== ALL ORDERS ==========");

        for (Order order : orders) {

            order.displayOrder();
            System.out.println();

        }

    }

    // ======================================
    // Save / Load everything
    // ======================================

    @Override
    public void saveAll() {

        saveMenu();
        saveCustomers();
        saveOrders();

    }

    @Override
    public void loadAll() {

        loadMenu();
        loadCustomers();
        loadOrders();

    }

    // ---------------- Menu file ----------------
    // Format: itemId,category,name,price,extra

    private void saveMenu() {

        try {

            ensureDataFolder();

            BufferedWriter writer =
                    new BufferedWriter(new FileWriter(MENU_FILE));

            for (FoodItem item : menu) {

                writer.write(item.getItemId() + ","
                        + item.getCategory() + ","
                        + sanitize(item.getName()) + ","
                        + item.getPrice() + ","
                        + sanitize(item.getDescription()) + ","
                        + sanitize(item.getImagePath()));

                writer.newLine();

            }

            writer.close();

        }

        catch (IOException e) {

            System.out.println("Error saving menu.");

        }

    }

    private void loadMenu() {

        File file = new File(MENU_FILE);

        if (!file.exists()) {
            return;
        }

        try {

            BufferedReader reader = new BufferedReader(new FileReader(file));
            String line;

            while ((line = reader.readLine()) != null) {

                if (line.isBlank()) {
                    continue;
                }

                try {

                    String[] parts = line.split(",");

                    int id = Integer.parseInt(parts[0].trim());

                    FoodCategory category =
                            FoodCategory.valueOf(parts[1].trim());

                    String name = parts[2].trim();

                    double price =
                            Double.parseDouble(parts[3].trim());

                    String description =
                            parts[4].trim();

                    String imagePath =
                            parts[5].trim();

                    FoodItem item;

                    switch (category) {

                        case VEG:
                            item = new VegItem(
                                    id,
                                    name,
                                    price,
                                    description,
                                    imagePath
                            );
                            break;

                        case NON_VEG:
                            item = new NonVegItem(
                                    id,
                                    name,
                                    price,
                                    description,
                                    imagePath
                            );
                            break;

                        default:
                            item = new BeverageItem(
                                    id,
                                    name,
                                    price,
                                    description,
                                    imagePath
                            );

                    }

                    if (searchFood(id) == null) {
                        menu.add(item);
                    }

                    if (id > maxFoodId) {
                        maxFoodId = id;
                    }

                }

                catch (Exception e) {

                    System.out.println("Skipped a corrupted menu line: " + line);

                }

            }

            reader.close();

        }

        catch (IOException e) {

            System.out.println("Error loading menu.");

        }

    }

 
    // ---------------- Customers file ----------------
    // Format: customerId,name,phone
    private void saveCustomers() {

        try {

            ensureDataFolder();

            BufferedWriter writer =
                    new BufferedWriter(new FileWriter(CUSTOMERS_FILE));

            for (Customer customer : customers) {

                writer.write(
                        customer.getPersonId() + ","
                        + sanitize(customer.getName()) + ","
                        + sanitize(customer.getPhoneNumber()) + ","
                        + sanitize(customer.getPassword())
                );

                writer.newLine();
            }

            writer.close();

        }

        catch (IOException e) {

            System.out.println("Error saving customers.");

        }

    }
    private void loadCustomers() {

        File file = new File(CUSTOMERS_FILE);

        if (!file.exists()) {
            return;
        }

        try {

            BufferedReader reader =
                    new BufferedReader(new FileReader(file));

            String line;

            while ((line = reader.readLine()) != null) {

                if (line.isBlank()) {
                    continue;
                }

                try {

                    String[] parts = line.split(",", -1);

                    int id = Integer.parseInt(parts[0].trim());

                    String name = parts[1].trim();

                    String phone = parts[2].trim();

                    // Password is the 4th field
                    String password = "";

                    if (parts.length >= 4) {
                        password = parts[3].trim();
                    }

                    if (searchCustomer(id) == null) {

                        customers.add(
                                new Customer(
                                        id,
                                        name,
                                        phone,
                                        password
                                )
                        );
                    }

                    if (id > maxCustomerId) {
                        maxCustomerId = id;
                    }

                }

                catch (Exception e) {

                    System.out.println(
                            "Skipped a corrupted customer line: "
                            + line
                    );

                }

            }

            reader.close();

        }

        catch (IOException e) {

            System.out.println("Error loading customers.");

        }

    }

    // ---------------- Orders file ----------------
    // Format: orderId,customerId,status,itemId:qty|itemId:qty|...

    private void saveOrders() {

        try {

            ensureDataFolder();

            BufferedWriter writer =
                    new BufferedWriter(new FileWriter(ORDERS_FILE));

            for (Order order : orders) {

                StringBuilder itemsField = new StringBuilder();

                for (int i = 0; i < order.getItems().size(); i++) {

                    OrderItem oi = order.getItems().get(i);

                    itemsField.append(oi.getFoodItem().getItemId())
                              .append(":")
                              .append(oi.getQuantity());

                    if (i < order.getItems().size() - 1) {
                        itemsField.append("|");
                    }

                }

                writer.write(order.getOrderId() + ","
                        + order.getCustomer().getPersonId() + ","
                        + order.getStatus() + ","
                        + itemsField);

                writer.newLine();

            }

            writer.close();

        }

        catch (IOException e) {

            System.out.println("Error saving orders.");

        }

    }

    private void loadOrders() {

        File file = new File(ORDERS_FILE);

        if (!file.exists()) {
            return;
        }

        try {

            BufferedReader reader = new BufferedReader(new FileReader(file));
            String line;

            while ((line = reader.readLine()) != null) {

                if (line.isBlank()) continue;

                try {

                    String[] parts = line.split(",", -1);

                    int orderId = Integer.parseInt(parts[0].trim());
                    int customerId = Integer.parseInt(parts[1].trim());
                    OrderStatus status = OrderStatus.valueOf(parts[2].trim());
                    String itemsField = parts.length > 3 ? parts[3].trim() : "";

                    Customer customer = searchCustomer(customerId);

                    if (customer == null) {
                        System.out.println("Skipped order " + orderId
                                + " — customer " + customerId + " not found.");
                        continue;
                    }

                    if (searchOrder(orderId) != null) {
                        continue; // already loaded
                    }

                    Order order = new Order(orderId, customer);

                    if (!itemsField.isBlank()) {

                        for (String pair : itemsField.split("\\|")) {

                            String[] idQty = pair.split(":");
                            int foodId = Integer.parseInt(idQty[0].trim());
                            int qty = Integer.parseInt(idQty[1].trim());

                            FoodItem foodItem = searchFood(foodId);

                            if (foodItem != null) {
                                order.addItem(foodItem, qty);
                            }

                        }

                    }

                    order.updateStatus(status);

                    orders.add(order);
                    customer.addOrder(order);

                    if (orderId > maxOrderId) {
                        maxOrderId = orderId;
                    }

                }

                catch (Exception e) {

                    System.out.println("Skipped a corrupted order line: " + line);

                }

            }

            reader.close();

        }

        catch (IOException e) {

            System.out.println("Error loading orders.");

        }

    }

    private void ensureDataFolder() {

        File folder = new File("src/data");

        if (!folder.exists()) {
            folder.mkdirs();
        }

    }

    // ======================================
    // Sanitize free-text fields before writing them to a comma-separated
    // file. User-entered text (names, descriptions from a TextArea) can
    // contain commas or newlines, which would otherwise silently corrupt
    // the row and cause the item to be dropped on the next load.
    // ======================================

    private String sanitize(String text) {

        if (text == null) {
            return "";
        }

        return text
                .replace(",", ";")
                .replace("\r", " ")
                .replace("\n", " ")
                .trim();

    }

    public void placeOrder(Customer currentCustomer, FoodItem item, int quantity) {
        // Ensure the customer and item are valid
        if (currentCustomer == null || item == null || quantity <= 0) {
            throw new IllegalArgumentException("Invalid customer, item, or quantity for order placement.");
        }

        // Logic to handle order recording or processing
        // Example: Add the order to your system's order list or database
        System.out.println("Order placed successfully for " + currentCustomer.getName() + 
                           " - Item: " + item.getName() + " (Qty: " + quantity + ")");
    }
}