package gui;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import model.Order;
import model.OrderStatus;
import model.OrderItem;

public class OrderManagement {

    public static void show(Stage stage){

        //-------------------------------------------------------
        // Root Layout & Theme (Deep Modern Dark Contrast)
        //-------------------------------------------------------
        BorderPane root = new BorderPane();
        root.setStyle("-fx-background-color: #FDF1E0;");

        //-------------------------------------------------------
        // Navigation Header Bar
        //-------------------------------------------------------
        Label logo = new Label("🍽 Urban Bites Admin");
        logo.setStyle(
                "-fx-font-size: 24;" +
                "-fx-font-weight: bold;" +
                "-fx-text-fill: #FF6B35;"
        );

        Button backButton = createSecondaryButton("← Dashboard");
        backButton.setOnAction(e -> FoodMenu.show(stage));

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        HBox header = new HBox(logo, spacer, backButton);
        header.setAlignment(Pos.CENTER_LEFT);
        header.setPadding(new Insets(18, 50, 18, 50));
        header.setStyle(
                "-fx-background-color: #FFFFFF;" +
                "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.3), 15, 0, 0, 4);"
        );
        root.setTop(header);

        //-------------------------------------------------------
        // Title & Description Header
        //-------------------------------------------------------
        Label title = new Label("Order Management");
        title.setStyle(
                "-fx-font-size: 26;" +
                "-fx-font-weight: bold;" +
                "-fx-text-fill: #1A1A2E;"
        );

        Label subtitle = new Label("Monitor incoming customer orders and update tracking statuses.");
        subtitle.setStyle(
                "-fx-font-size: 13;" +
                "-fx-text-fill: #78716C;"
        );

        VBox titleBox = new VBox(4, title, subtitle);
        titleBox.setAlignment(Pos.CENTER_LEFT);

        //-------------------------------------------------------
        // TableView Design & Columns
        //-------------------------------------------------------
        TableView<Order> table = new TableView<>();
        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        table.setStyle(
                "-fx-background-color: #F3E7D8;" +
                "-fx-control-inner-background: #F3E7D8;" +
                "-fx-table-cell-border-color: #EAD9C0;" +
                "-fx-background-radius: 10;" +
                "-fx-border-color: #EAD9C0;" +
                "-fx-border-radius: 10;"
        );

        TableColumn<Order, Integer> idCol = new TableColumn<>("Order ID");
        idCol.setCellValueFactory(data ->
                new SimpleIntegerProperty(data.getValue().getOrderId()).asObject());

        TableColumn<Order, String> customerCol = new TableColumn<>("Customer");
        customerCol.setCellValueFactory(data ->
                new SimpleStringProperty(data.getValue().getCustomer().getName()));
        
        TableColumn<Order, String> itemsCol = new TableColumn<>("Items");
        itemsCol.setCellValueFactory(data -> {
            StringBuilder items = new StringBuilder();
            for(OrderItem item : data.getValue().getItems()){
                items.append(item.getFoodItem().getName());
                items.append(" x");
                items.append(item.getQuantity());
                items.append(", ");
            }
            String result = items.toString();
            if (result.endsWith(", ")) {
                result = result.substring(0, result.length() - 2);
            }
            return new SimpleStringProperty(result);
        });
        
        TableColumn<Order, Double> totalCol = new TableColumn<>("Total ($)");
        totalCol.setCellValueFactory(data ->
                new SimpleDoubleProperty(data.getValue().calculateTotal()).asObject());
        
        TableColumn<Order, String> statusCol = new TableColumn<>("Status");
        statusCol.setCellValueFactory(data ->
                new SimpleStringProperty(data.getValue().getStatus().toString()));
        
        table.getColumns().addAll(
                idCol,
                customerCol,
                itemsCol,
                totalCol,
                statusCol
        );

        table.getItems().addAll(AppData.system.getOrders());

        //-------------------------------------------------------
        // Action Controls & Panel
        //-------------------------------------------------------
        Button updateButton = createPrimaryButton("✏️ Update Status");
        Button refreshButton = createSecondaryButton("🔄 Refresh");
        
        refreshButton.setOnAction(e -> {
            table.getItems().clear();
            table.getItems().addAll(AppData.system.getOrders());
        });
        
        updateButton.setOnAction(e -> {
            Order selected = table.getSelectionModel().getSelectedItem();

            if(selected == null){
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setHeaderText(null);
                alert.setContentText("Please select an order first.");
                alert.showAndWait();
                return;
            }

            ChoiceDialog<String> dialog = new ChoiceDialog<>(
                    "Pending",
                    "Pending",
                    "Preparing",
                    "Ready",
                    "Completed",
                    "Cancelled"
            );

            dialog.setTitle("Update Order Status");
            dialog.setHeaderText("Change status for Order ID: " + selected.getOrderId());
            dialog.setContentText("Select new status:");

            dialog.showAndWait().ifPresent(status -> {
                try {
                    AppData.system.updateOrderStatus(
                            selected.getOrderId(),
                            OrderStatus.valueOf(status.toUpperCase())
                    );

                    AppData.system.saveAll();

                    table.getItems().clear();
                    table.getItems().addAll(AppData.system.getOrders());

                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setHeaderText(null);
                    alert.setContentText("Order status updated successfully!");
                    alert.showAndWait();

                } catch(Exception ex){
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setHeaderText(null);
                    alert.setContentText(ex.getMessage());
                    alert.showAndWait();
                }
            });
        });

        HBox actionBox = new HBox(15, refreshButton, updateButton);
        actionBox.setAlignment(Pos.CENTER_LEFT);

        //-------------------------------------------------------
        // Card Container Structure
        //-------------------------------------------------------
        VBox card = new VBox(20);
        card.setAlignment(Pos.TOP_LEFT);
        card.setPadding(new Insets(30, 35, 30, 35));
        card.setMaxWidth(1100);
        card.setStyle(
                "-fx-background-color: #FFFFFF;" +
                "-fx-background-radius: 20;" +
                "-fx-border-color: #EAD9C0;" +
                "-fx-border-radius: 20;" +
                "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.4), 20, 0, 0, 6);"
        );

        VBox.setVgrow(table, Priority.ALWAYS);
        card.getChildren().addAll(
                titleBox,
                new Separator() {{ setStyle("-fx-background-color: #EAD9C0;"); }},
                actionBox,
                table
        );

        VBox centerWrapper = new VBox(card);
        centerWrapper.setAlignment(Pos.CENTER);
        centerWrapper.setPadding(new Insets(30, 40, 30, 40));

        ScrollPane scrollPane = new ScrollPane(centerWrapper);
        scrollPane.setFitToWidth(true);
        scrollPane.setStyle(
                "-fx-background: #FDF1E0;" +
                "-fx-background-color: transparent;"
        );

        root.setCenter(scrollPane);

        Scene scene = new Scene(root, 1280, 800);
        scene.getStylesheets().add(Main.class.getResource("style.css").toExternalForm());
        stage.setTitle("Urban Bites - Order Management");
        stage.setScene(scene);
        stage.show();
    }

    //-------------------------------------------------------
    // Aesthetic Styling & Component Helpers
    //-------------------------------------------------------
    private static Button createPrimaryButton(String text) {
        Button button = new Button(text);
        button.setStyle(
                "-fx-background-color: #FF6B35;" +
                "-fx-text-fill: white;" +
                "-fx-font-size: 13;" +
                "-fx-font-weight: bold;" +
                "-fx-background-radius: 8;" +
                "-fx-padding: 10 18;" +
                "-fx-cursor: hand;"
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
                "-fx-padding: 10 18;" +
                "-fx-cursor: hand;"
        );
        return button;
    }
}