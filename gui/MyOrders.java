package gui;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import model.Order;
import model.OrderItem;

public class MyOrders {

    public static void show(Stage stage) {

        // =======================================================
        // ROOT
        // =======================================================

        BorderPane root = new BorderPane();

        root.setStyle(
                "-fx-background-color: #FDF1E0;"
        );

        // =======================================================
        // NAVIGATION HEADER
        // =======================================================

        Label logo = new Label("🍽 Urban Bites");

        logo.setStyle(
                "-fx-font-size: 24;" +
                "-fx-font-weight: bold;" +
                "-fx-text-fill: #9A6B3A;"
        );

        Button backButton =
                createSecondaryButton("← Dashboard");

        backButton.setOnAction(
                e -> CustomerDashboard.show(stage)
        );

        Region spacer = new Region();

        HBox.setHgrow(
                spacer,
                Priority.ALWAYS
        );

        HBox header = new HBox(
                20,
                logo,
                spacer,
                backButton
        );

        header.setAlignment(
                Pos.CENTER_LEFT
        );

        header.setPadding(
                new Insets(18, 50, 18, 50)
        );

        header.setStyle(
                "-fx-background-color: #FFFFFF;" +
                "-fx-border-color: #EAD9C0;" +
                "-fx-border-width: 0 0 1 0;" +
                "-fx-effect: dropshadow(" +
                "gaussian," +
                "rgba(80,60,30,0.10)," +
                "12,0,0,3" +
                ");"
        );

        root.setTop(header);

        // =======================================================
        // TITLE
        // =======================================================

        Label title =
                new Label("My Order History");

        title.setStyle(
                "-fx-font-size: 28;" +
                "-fx-font-weight: bold;" +
                "-fx-text-fill: #3F3020;"
        );

        Label subtitle =
                new Label(
                        "View all your previous orders and the items included in each order."
                );

        subtitle.setStyle(
                "-fx-font-size: 14;" +
                "-fx-text-fill: #756858;"
        );

        VBox titleBox =
                new VBox(
                        5,
                        title,
                        subtitle
                );

        titleBox.setAlignment(
                Pos.CENTER_LEFT
        );

        // =======================================================
        // REFRESH BUTTON
        // =======================================================

        Button refreshButton =
                createPrimaryButton("↻ Refresh");

        // =======================================================
        // ORDER CONTAINER
        // =======================================================

        VBox ordersContainer =
                new VBox(20);

        ordersContainer.setPadding(
                new Insets(
                        10,
                        0,
                        40,
                        0
                )
        );

        // =======================================================
        // LOAD ORDERS
        // =======================================================

        refreshOrders(
                ordersContainer
        );

        refreshButton.setOnAction(
                e -> refreshOrders(
                        ordersContainer
                )
        );

        // =======================================================
        // TOP SECTION
        // =======================================================

        HBox toolbar =
                new HBox(
                        refreshButton
                );

        toolbar.setAlignment(
                Pos.CENTER_LEFT
        );

        VBox topSection =
                new VBox(
                        18,
                        titleBox,
                        toolbar
                );

        topSection.setPadding(
                new Insets(
                        30,
                        50,
                        15,
                        50
                )
        );

        // =======================================================
        // MAIN CONTENT
        // =======================================================

        VBox content =
                new VBox(
                        topSection,
                        ordersContainer
                );

        content.setPadding(
                new Insets(
                        0,
                        50,
                        0,
                        50
                )
        );

        content.setStyle(
                "-fx-background-color: #FDF1E0;"
        );

        ScrollPane scrollPane =
                new ScrollPane(content);

        scrollPane.setFitToWidth(true);

        scrollPane.setHbarPolicy(
                ScrollPane.ScrollBarPolicy.NEVER
        );

        scrollPane.setVbarPolicy(
                ScrollPane.ScrollBarPolicy.AS_NEEDED
        );

        scrollPane.setStyle(
                "-fx-background-color: transparent;" +
                "-fx-background: transparent;"
        );

        root.setCenter(
                scrollPane
        );

        // =======================================================
        // SCENE
        // =======================================================

        Scene scene =
                new Scene(
                        root,
                        1280,
                        800
                );

        scene.getStylesheets().add(
                Main.class
                        .getResource("style.css")
                        .toExternalForm()
        );

        stage.setTitle(
                "Urban Bites - My Orders"
        );

        stage.setScene(scene);
        stage.show();
    }

    // ===========================================================
    // REFRESH ORDERS
    // ===========================================================

    private static void refreshOrders(
            VBox ordersContainer
    ) {

        ordersContainer.getChildren().clear();

        if (AppData.currentCustomer == null) {

            Label message =
                    new Label(
                            "Please log in to view your orders."
                    );

            message.setStyle(
                    "-fx-font-size: 16;" +
                    "-fx-text-fill: #756858;"
            );

            ordersContainer.getChildren().add(
                    message
            );

            return;
        }

        if (AppData.currentCustomer
                .getOrderHistory()
                .isEmpty()) {

            Label emptyLabel =
                    new Label(
                            "You haven't placed any orders yet."
                    );

            emptyLabel.setStyle(
                    "-fx-font-size: 16;" +
                    "-fx-font-weight: bold;" +
                    "-fx-text-fill: #756858;"
            );

            VBox emptyBox =
                    new VBox(
                            emptyLabel
                    );

            emptyBox.setAlignment(
                    Pos.CENTER
            );

            emptyBox.setPadding(
                    new Insets(50)
            );

            ordersContainer.getChildren().add(
                    emptyBox
            );

            return;
        }

        // =======================================================
        // CREATE ONE CARD FOR EACH ORDER
        // =======================================================

        for (Order order :
                AppData.currentCustomer
                        .getOrderHistory()) {

            VBox orderCard =
                    createOrderCard(order);

            ordersContainer.getChildren().add(
                    orderCard
            );
        }
    }

    // ===========================================================
    // CREATE ORDER CARD
    // ===========================================================

    private static VBox createOrderCard(
            Order order
    ) {

        // -------------------------------------------------------
        // ORDER HEADER
        // -------------------------------------------------------

        Label orderId =
                new Label(
                        "Order #" +
                        order.getOrderId()
                );

        orderId.setStyle(
                "-fx-font-size: 18;" +
                "-fx-font-weight: bold;" +
                "-fx-text-fill: #3F3020;"
        );

        Label status =
                new Label(
                        order.getStatus()
                                .toString()
                );

        status.setStyle(
                "-fx-background-color: #E8DCC8;" +
                "-fx-text-fill: #6E5633;" +
                "-fx-font-size: 12;" +
                "-fx-font-weight: bold;" +
                "-fx-background-radius: 15;" +
                "-fx-padding: 6 12;"
        );

        Region headerSpacer =
                new Region();

        HBox.setHgrow(
                headerSpacer,
                Priority.ALWAYS
        );

        HBox orderHeader =
                new HBox(
                        10,
                        orderId,
                        headerSpacer,
                        status
                );

        orderHeader.setAlignment(
                Pos.CENTER_LEFT
        );

        // -------------------------------------------------------
        // ITEMS
        // -------------------------------------------------------

        VBox itemsBox =
                new VBox(8);

        for (OrderItem item :
                order.getItems()) {

            Label itemName =
                    new Label(
                            item.getFoodItem()
                                    .getName()
                    );

            itemName.setStyle(
                    "-fx-font-size: 14;" +
                    "-fx-font-weight: bold;" +
                    "-fx-text-fill: #3F3020;"
            );

            Label quantity =
                    new Label(
                            "x" +
                            item.getQuantity()
                    );

            quantity.setStyle(
                    "-fx-font-size: 14;" +
                    "-fx-text-fill: #756858;"
            );

            Label subtotal =
                    new Label(
                            String.format(
                                    "৳ %.2f",
                                    item.calculateSubtotal()
                            )
                    );

            subtotal.setStyle(
                    "-fx-font-size: 14;" +
                    "-fx-font-weight: bold;" +
                    "-fx-text-fill: #9A6B3A;"
            );

            Region itemSpacer =
                    new Region();

            HBox.setHgrow(
                    itemSpacer,
                    Priority.ALWAYS
            );

            HBox itemRow =
                    new HBox(
                            10,
                            itemName,
                            quantity,
                            itemSpacer,
                            subtotal
                    );

            itemRow.setAlignment(
                    Pos.CENTER_LEFT
            );

            itemRow.setPadding(
                    new Insets(
                            8,
                            10,
                            8,
                            10
                    )
            );

            itemRow.setStyle(
                    "-fx-background-color: #FFF9F1;" +
                    "-fx-background-radius: 8;"
            );

            itemsBox.getChildren().add(
                    itemRow
            );
        }

        // -------------------------------------------------------
        // DIVIDER
        // -------------------------------------------------------

        Separator separator =
                new Separator();

        separator.setStyle(
                "-fx-background-color: #EAD9C0;"
        );

        // -------------------------------------------------------
        // TOTAL
        // -------------------------------------------------------

        Label totalText =
                new Label("Total");

        totalText.setStyle(
                "-fx-font-size: 15;" +
                "-fx-font-weight: bold;" +
                "-fx-text-fill: #3F3020;"
        );

        Label total =
                new Label(
                        String.format(
                                "৳ %.2f",
                                order.getTotalPrice()
                        )
                );

        total.setStyle(
                "-fx-font-size: 18;" +
                "-fx-font-weight: bold;" +
                "-fx-text-fill: #9A6B3A;"
        );

        Region totalSpacer =
                new Region();

        HBox.setHgrow(
                totalSpacer,
                Priority.ALWAYS
        );

        HBox totalRow =
                new HBox(
                        totalText,
                        totalSpacer,
                        total
                );

        totalRow.setAlignment(
                Pos.CENTER_LEFT
        );

        // -------------------------------------------------------
        // CARD
        // -------------------------------------------------------

        VBox card =
                new VBox(
                        15,
                        orderHeader,
                        itemsBox,
                        separator,
                        totalRow
                );

        card.setPadding(
                new Insets(20)
        );

        card.setStyle(
                "-fx-background-color: #FFFFFF;" +
                "-fx-background-radius: 18;" +
                "-fx-border-color: #E3D7C3;" +
                "-fx-border-width: 1;" +
                "-fx-border-radius: 18;" +
                "-fx-effect: dropshadow(" +
                "gaussian," +
                "rgba(80,60,30,0.12)," +
                "12,0,0,4" +
                ");"
        );

        return card;
    }

    // ===========================================================
    // PRIMARY BUTTON
    // ===========================================================

    private static Button createPrimaryButton(
            String text
    ) {

        Button button =
                new Button(text);

        button.setPrefWidth(135);
        button.setPrefHeight(42);

        button.setStyle(
                "-fx-background-color: #9A6B3A;" +
                "-fx-text-fill: #FFFDF8;" +
                "-fx-font-size: 14;" +
                "-fx-font-weight: bold;" +
                "-fx-background-radius: 14;" +
                "-fx-padding: 10 20;" +
                "-fx-cursor: hand;"
        );

        return button;
    }

    // ===========================================================
    // SECONDARY BUTTON
    // ===========================================================

    private static Button createSecondaryButton(
            String text
    ) {

        Button button =
                new Button(text);

        button.setPrefWidth(150);
        button.setPrefHeight(42);

        button.setStyle(
                "-fx-background-color: #F3E7D8;" +
                "-fx-text-fill: #6E5633;" +
                "-fx-font-size: 14;" +
                "-fx-font-weight: bold;" +
                "-fx-background-radius: 14;" +
                "-fx-border-color: #D8C3A5;" +
                "-fx-border-width: 1.2;" +
                "-fx-border-radius: 14;" +
                "-fx-padding: 10 20;" +
                "-fx-cursor: hand;"
        );

        return button;
    }
}