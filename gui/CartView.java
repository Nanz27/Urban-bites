package gui;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import model.Order;
import model.OrderItem;

public class CartView {

    public static void show(Stage stage) {

        //-------------------------------------------------------
        // Root Layout & Theme (Deep Modern Dark Contrast)
        //-------------------------------------------------------
        BorderPane root = new BorderPane();
        root.setStyle(
                "-fx-background-color: linear-gradient(to bottom right, " +
                "#FDF1E0 0%, #FCE7D0 45%, #FBDFC0 100%);"
        );

        //-------------------------------------------------------
        // Navigation Header Bar
        //-------------------------------------------------------
        Label logo = new Label("🍽 Urban Bites");
        logo.setStyle(
                "-fx-font-size: 24;" +
                "-fx-font-weight: bold;" +
                "-fx-text-fill: #FF6B35;"
        );

        Button menuButton = createPrimaryButton("🍽 Menu");
        menuButton.setOnAction(e -> PublicMenu.show(stage, true));
        
        Button backButton = createPrimaryButton("← Dashboard");
        backButton.setOnAction(e -> CustomerDashboard.show(stage));


        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        HBox header = new HBox(25,logo, spacer, menuButton, backButton);
        header.setAlignment(Pos.CENTER_LEFT);
        header.setPadding(new Insets(18, 50, 18, 50));
        header.setStyle(
                "-fx-background-color: transparent;"
        );
        root.setTop(header);

        //-------------------------------------------------------
        // Title & Description Header
        //-------------------------------------------------------
        Label title = new Label("Your Shopping Cart");
        title.setStyle(
                "-fx-font-size: 26;" +
                "-fx-font-weight: bold;" +
                "-fx-text-fill: #211A14;"
        );

        Label subtitle = new Label("Review your selected food items before placing your order.");
        subtitle.setStyle(
                "-fx-font-size: 13;" +
                "-fx-text-fill: #6B6259;"
        );

        VBox titleBox = new VBox(4, title, subtitle);
        titleBox.setAlignment(Pos.CENTER_LEFT);

        //-------------------------------------------------------
        // Modern Styled TableView Component
        //-------------------------------------------------------
        TableView<OrderItem> table = new TableView<>();
        table.setPlaceholder(new Label("Your cart is empty."));
        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        table.setStyle(
                "-fx-background-color: #FFFFFF;" +
                "-fx-table-cell-border-color: #EAD9C0;" +
                "-fx-background-radius: 12;"
        );

        TableColumn<OrderItem, String> nameCol = new TableColumn<>("Food Item");
        nameCol.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getFoodItem().getName()));

        TableColumn<OrderItem, Integer> qtyCol = new TableColumn<>("Quantity");
        qtyCol.setCellValueFactory(data -> new SimpleIntegerProperty(data.getValue().getQuantity()).asObject());
        qtyCol.setMaxWidth(120);

        TableColumn<OrderItem, Double> priceCol = new TableColumn<>("Subtotal");
        priceCol.setCellValueFactory(data -> new SimpleDoubleProperty(data.getValue().calculateSubtotal()).asObject());
        priceCol.setCellFactory(col -> new TableCell<>() {
            @Override
            protected void updateItem(Double subtotal, boolean empty) {
                super.updateItem(subtotal, empty);
                if (empty || subtotal == null) {
                    setText(null);
                } else {
                    setText(String.format("৳ "
                    		+ "৳ %.2f", subtotal));
                    setStyle("-fx-text-fill: #FF6B35; -fx-font-weight: bold;");
                }
            }
        });
        priceCol.setMaxWidth(140);

        table.getColumns().addAll(nameCol, qtyCol, priceCol);
        refreshCart(table);

        VBox tableContainer = new VBox(table);
        tableContainer.setPadding(new Insets(20, 50, 20, 50));
        tableContainer.setStyle("-fx-background-color: transparent;");

        //-------------------------------------------------------
        // Total Price & Checkout Action Panel
        //-------------------------------------------------------
        Label totalLabel = new Label();
        updateTotal(totalLabel);
        totalLabel.setStyle(
                "-fx-font-size: 20;" +
                "-fx-font-weight: bold;" +
                "-fx-text-fill: #211A14;"
        );

        Button removeButton = createSecondaryButton("🗑 Remove Item");
        Button checkoutButton = createPrimaryButton("🚀 Place Order");

        HBox actionToolbar = new HBox(15, removeButton, checkoutButton);
        actionToolbar.setAlignment(Pos.CENTER_RIGHT);

        HBox bottomPanel = new HBox(totalLabel, actionToolbar);
        bottomPanel.setAlignment(Pos.CENTER_LEFT);
        HBox.setHgrow(actionToolbar, Priority.ALWAYS);
        actionToolbar.setAlignment(Pos.CENTER_RIGHT);
        bottomPanel.setPadding(new Insets(10, 50, 40, 50));
        bottomPanel.setStyle("-fx-background-color: transparent;");

        //-------------------------------------------------------
        // Container Assembly & Scroll Setup
        //-------------------------------------------------------
        VBox centerContainer = new VBox(titleBox);
        centerContainer.setPadding(new Insets(30, 50, 0, 50));
        centerContainer.setStyle("-fx-background-color: transparent;");

        VBox mainContent = new VBox(centerContainer, tableContainer, bottomPanel);
        mainContent.setStyle("-fx-background-color: transparent;");

        ScrollPane scrollPane = new ScrollPane(mainContent);
        scrollPane.setFitToWidth(true);
        scrollPane.setStyle(
                "-fx-background: transparent;" +
                "-fx-background-color: transparent;"
        );

        root.setCenter(scrollPane);

        //-------------------------------------------------------
        // Button Event Actions
        //-------------------------------------------------------
        removeButton.setOnAction(e -> {
            OrderItem selected = table.getSelectionModel().getSelectedItem();
            if (selected == null) {
                showAlert("Select an item first.");
                return;
            }
            AppData.cart.removeItem(selected.getFoodItem().getItemId());
            refreshCart(table);
            updateTotal(totalLabel);
        });

        checkoutButton.setOnAction(e -> {
            if (AppData.cart.getItems().isEmpty()) {
                showAlert("Cart is empty.");
                return;
            }

            try {
                Order order = new Order(
                        AppData.system.nextOrderId(),
                        AppData.currentCustomer
                );

                for (OrderItem item : AppData.cart.getItems()) {
                    order.addItem(item.getFoodItem(), item.getQuantity());
                }

                AppData.system.placeOrder(order);
                AppData.system.saveAll();

                String bill = generateBill(order);
                AppData.cart.clear();

                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Order Confirmed");
                alert.setHeaderText("Thank you for ordering!");
                alert.setContentText(bill);
                alert.showAndWait();

                CustomerDashboard.show(stage);

            } catch (Exception ex) {
                showAlert(ex.getMessage());
            }
        });

        Scene scene = new Scene(root, 1280, 800);
        scene.getStylesheets().add(Main.class.getResource("style.css").toExternalForm());
        stage.setTitle("Urban Bites - Your Cart");
        stage.setScene(scene);
        stage.show();
    }

    //-------------------------------------------------------
    // Helper Methods & UI Components
    //-------------------------------------------------------
    private static void refreshCart(TableView<OrderItem> table) {
        table.getItems().clear();
        table.getItems().addAll(AppData.cart.getItems());
    }

    private static void updateTotal(Label label) {
        double total = 0;
        for (OrderItem item : AppData.cart.getItems()) {
            total += item.calculateSubtotal();
        }
        label.setText("Total: ৳ " + String.format("%.2f", total));
    }

    private static String generateBill(Order order) {
        StringBuilder bill = new StringBuilder();
        bill.append("Order ID: ").append(order.getOrderId()).append("\n\n");
        bill.append("Items:\n");

        for (OrderItem item : order.getItems()) {
            bill.append(item.getFoodItem().getName())
                    .append(" x ")
                    .append(item.getQuantity())
                    .append("\n");
        }

        bill.append("\nTotal: ৳ ").append(String.format("%.2f", order.calculateTotal()));
        return bill.toString();
    }

    private static void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private static Button createPrimaryButton(String text) {

        Button button = new Button(text);

        button.setPrefWidth(150);
        button.setPrefHeight(44);

        String normalStyle =
                "-fx-background-color: #9A6B3A;" +
                "-fx-text-fill: #FFFDF8;" +
                "-fx-font-size: 14;" +
                "-fx-font-weight: bold;" +
                "-fx-background-radius: 14;" +
                "-fx-border-radius: 14;" +
                "-fx-padding: 10 24;" +
                "-fx-cursor: hand;" +
                "-fx-effect: dropshadow(gaussian, rgba(80,60,30,0.18), 8, 0, 0, 3);";

        String hoverStyle =
                "-fx-background-color: #7F542C;" +
                "-fx-text-fill: #FFFFFF;" +
                "-fx-font-size: 14;" +
                "-fx-font-weight: bold;" +
                "-fx-background-radius: 14;" +
                "-fx-border-radius: 14;" +
                "-fx-padding: 10 24;" +
                "-fx-cursor: hand;" +
                "-fx-effect: dropshadow(gaussian, rgba(80,60,30,0.28), 12, 0, 0, 4);";

        button.setStyle(normalStyle);

        button.setOnMouseEntered(e ->
                button.setStyle(hoverStyle)
        );

        button.setOnMouseExited(e ->
                button.setStyle(normalStyle)
        );

        return button;
    }

    private static Button createSecondaryButton(String text) {
        Button button = new Button(text);
        button.setStyle(
                "-fx-background-color: #F3E7D8;" +
                "-fx-text-fill: #3F3A34;" +
                "-fx-font-size: 13;" +
                "-fx-font-weight: bold;" +
                "-fx-background-radius: 8;" +
                "-fx-border-color: #EAD9C0;" +
                "-fx-border-radius: 8;" +
                "-fx-padding: 8 16;" +
                "-fx-cursor: hand;"
        );
        return button;
    }
}