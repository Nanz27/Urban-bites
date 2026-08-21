package gui;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import model.FoodItem;

public class FoodMenu {

    public static void show(Stage stage) {

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

        Button ordersButton = createSecondaryButton("📦 View Orders");
        ordersButton.setOnAction(e -> OrderManagement.show(stage));

        Button logoutButton = createDangerButton("Logout");
        logoutButton.setOnAction(e -> Main.showMainMenu(stage));

        HBox navRight = new HBox(12, ordersButton, logoutButton);
        navRight.setAlignment(Pos.CENTER_RIGHT);

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        HBox header = new HBox(logo, spacer, navRight);
        header.setAlignment(Pos.CENTER_LEFT);
        header.setPadding(new Insets(18, 50, 18, 50));
        header.setStyle(
                "-fx-background-color: #FFFFFF;" +
                "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.3), 15, 0, 0, 4);"
        );
        root.setTop(header);

        //-------------------------------------------------------
        // Dashboard Summary Metric Cards
        //-------------------------------------------------------
        HBox dashboardCards = new HBox(20);
        dashboardCards.setAlignment(Pos.CENTER_LEFT);
        dashboardCards.getChildren().addAll(
                createMetricCard("🍔", "Total Foods", String.valueOf(AppData.system.getMenu().size())),
                createMetricCard("👤", "Total Customers", String.valueOf(AppData.system.getCustomers().size())),
                createMetricCard("📦", "Total Orders", String.valueOf(AppData.system.getOrders().size()))
        );

        Label title = new Label("Menu Management");
        title.setStyle(
                "-fx-font-size: 26;" +
                "-fx-font-weight: bold;" +
                "-fx-text-fill: #1A1A2E;"
        );

        Label subtitle = new Label("Add, edit, or remove dishes available across the Urban Bites platform.");
        subtitle.setStyle(
                "-fx-font-size: 13;" +
                "-fx-text-fill: #78716C;"
        );

        VBox titleBox = new VBox(4, title, subtitle);
        titleBox.setAlignment(Pos.CENTER_LEFT);

        //-------------------------------------------------------
        // Action Toolbar Buttons
        //-------------------------------------------------------
        Button addButton = createPrimaryButton("➕ Add Food");
        Button editButton = createToolbarButton("✏ Edit Food");
        Button removeButton = createDangerButton("🗑 Remove");
        Button refreshButton = createToolbarButton("🔄 Refresh");

        HBox toolbar = new HBox(12, addButton, editButton, removeButton, refreshButton);
        toolbar.setAlignment(Pos.CENTER_LEFT);

        VBox topSection = new VBox(25, dashboardCards, titleBox, toolbar);
        topSection.setPadding(new Insets(30, 50, 20, 50));
        topSection.setStyle("-fx-background-color: #FDF1E0;");

        //-------------------------------------------------------
        // Modern Styled TableView Component
        //-------------------------------------------------------
        TableView<FoodItem> table = new TableView<>();
        table.setPlaceholder(new Label("No food items available in inventory."));
        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        table.setStyle(
                "-fx-background-color: #FFFFFF;" +
                "-fx-table-cell-border-color: #EAD9C0;" +
                "-fx-background-radius: 12;"
        );

        TableColumn<FoodItem, ImageView> imageCol = new TableColumn<>("Image");
        imageCol.setCellValueFactory(data -> {
            Image image = ImageUtil.loadImage(data.getValue().getImagePath());
            ImageView view = image != null ? new ImageView(image) : new ImageView();
            view.setFitWidth(55);
            view.setFitHeight(55);
            view.setPreserveRatio(false);

            Rectangle clip = new Rectangle(55, 55);
            clip.setArcWidth(12);
            clip.setArcHeight(12);
            view.setClip(clip);

            StackPane imageContainer = new StackPane(view);
            imageContainer.setPrefSize(65, 65);
            imageContainer.setAlignment(Pos.CENTER);
            imageContainer.setStyle(
                    "-fx-background-color: #F3E7D8;" +
                    "-fx-background-radius: 10;" +
                    "-fx-border-color: #EAD9C0;" +
                    "-fx-border-radius: 10;" +
                    "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.2), 6, 0, 0, 2);"
            );

            ImageView finalView = new ImageView();
            // Wrap in an auxiliary pane or return as Node/ImageView bound appropriately
            // To match TableColumn<FoodItem, ImageView>, we can return the ImageView inside a StackPane converted via node lookup or just style the ImageView container cell factory.
            // Let's use a cell factory on imageCol for rich card styling:
            return new SimpleObjectProperty<>(view);
        });
        
        // Let's use a custom cell factory for imageCol so it renders as an exquisite card container cell
        imageCol.setCellFactory(col -> new TableCell<>() {
            @Override
            protected void updateItem(ImageView view, boolean empty) {
                super.updateItem(view, empty);
                if (empty || getTableRow() == null || getTableRow().getItem() == null) {
                    setGraphic(null);
                } else {
                    FoodItem item = getTableRow().getItem();
                    Image image = ImageUtil.loadImage(item.getImagePath());
                    ImageView imgView = image != null ? new ImageView(image) : new ImageView();
                    imgView.setFitWidth(56);
                    imgView.setFitHeight(56);
                    imgView.setPreserveRatio(false);

                    Rectangle clip = new Rectangle(56, 56);
                    clip.setArcWidth(12);
                    clip.setArcHeight(12);
                    imgView.setClip(clip);

                    StackPane cardBox = new StackPane(imgView);
                    cardBox.setPrefSize(64, 64);
                    cardBox.setMaxSize(64, 64);
                    cardBox.setAlignment(Pos.CENTER);
                    cardBox.setStyle(
                            "-fx-background-color: #F3E7D8;" +
                            "-fx-background-radius: 10;" +
                            "-fx-border-color: #EAD9C0;" +
                            "-fx-border-radius: 10;" +
                            "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.25), 8, 0, 0, 2);"
                    );

                    setGraphic(cardBox);
                    setAlignment(Pos.CENTER);
                }
            }
        });
        imageCol.setMaxWidth(90);

        TableColumn<FoodItem, Integer> idCol = new TableColumn<>("ID");
        idCol.setCellValueFactory(data -> new SimpleIntegerProperty(data.getValue().getItemId()).asObject());
        idCol.setMaxWidth(60);

        TableColumn<FoodItem, String> nameCol = new TableColumn<>("Name");
        nameCol.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getName()));

        TableColumn<FoodItem, String> categoryCol = new TableColumn<>("Category");
        categoryCol.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getCategory().toString()));

        TableColumn<FoodItem, Double> priceCol = new TableColumn<>("Price");
        priceCol.setCellValueFactory(data -> new SimpleDoubleProperty(data.getValue().getPrice()).asObject());
        // Format price representation
        priceCol.setCellFactory(col -> new TableCell<>() {
            @Override
            protected void updateItem(Double price, boolean empty) {
                super.updateItem(price, empty);
                if (empty || price == null) {
                    setText(null);
                } else {
                    setText(String.format("৳%.2f", price));
                    setStyle("-fx-text-fill: #FF6B35; -fx-font-weight: bold;");
                }
            }
        });

        TableColumn<FoodItem, String> descriptionCol = new TableColumn<>("Description");
        descriptionCol.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getDescription()));

        table.getColumns().addAll(imageCol, idCol, nameCol, categoryCol, priceCol, descriptionCol);
        table.getItems().addAll(AppData.system.getMenu());

        VBox tableContainer = new VBox(table);
        tableContainer.setPadding(new Insets(0, 50, 40, 50));
        tableContainer.setStyle("-fx-background-color: #FDF1E0;");

        VBox centerContainer = new VBox(topSection, tableContainer);
        centerContainer.setStyle("-fx-background-color: #FDF1E0;");

        ScrollPane scrollPane = new ScrollPane(centerContainer);
        scrollPane.setFitToWidth(true);
        scrollPane.setStyle(
                "-fx-background: #FDF1E0;" +
                "-fx-background-color: transparent;"
        );

        root.setCenter(scrollPane);

        //-------------------------------------------------------
        // Button Event Actions (Preserved Functionality)
        //-------------------------------------------------------
        addButton.setOnAction(e -> AddFood.show(stage));

        editButton.setOnAction(e -> {
            FoodItem selected = table.getSelectionModel().getSelectedItem();
            if (selected == null) {
                showAlert("Select a food item first.");
                return;
            }
            EditFood.show(stage, selected);
        });

        removeButton.setOnAction(e -> {
            FoodItem selected = table.getSelectionModel().getSelectedItem();
            if (selected == null) {
                showAlert("Select a food item first.");
                return;
            }
            try {
                AppData.system.removeFoodItem(selected.getItemId());
                AppData.system.saveAll();
                table.getItems().remove(selected);
                showAlert("Food removed successfully!");
            } catch (Exception ex) {
                showAlert(ex.getMessage());
            }
        });

        refreshButton.setOnAction(e -> {
            table.getItems().clear();
            table.getItems().addAll(AppData.system.getMenu());
        });

        Scene scene = new Scene(root, 1280, 800);
        scene.getStylesheets().add(Main.class.getResource("style.css").toExternalForm());
        stage.setTitle("Urban Bites - Admin Dashboard");
        stage.setScene(scene);
        stage.show();
    }

    //-------------------------------------------------------
    // Aesthetic UI Helper Components
    //-------------------------------------------------------
    private static VBox createMetricCard(String icon, String title, String value) {
        Label iconLabel = new Label(icon);
        iconLabel.setStyle("-fx-font-size: 22;");

        Label titleLabel = new Label(title);
        titleLabel.setStyle("-fx-font-size: 12; -fx-text-fill: #78716C; -fx-font-weight: bold;");

        Label valueLabel = new Label(value);
        valueLabel.setStyle("-fx-font-size: 22; -fx-text-fill: #1A1A2E; -fx-font-weight: bold;");

        VBox textBox = new VBox(2, titleLabel, valueLabel);
        textBox.setAlignment(Pos.CENTER_LEFT);

        HBox innerCard = new HBox(15, iconLabel, textBox);
        innerCard.setAlignment(Pos.CENTER_LEFT);

        VBox card = new VBox(innerCard);
        card.setAlignment(Pos.CENTER_LEFT);
        card.setPrefSize(250, 95);
        card.setPadding(new Insets(16, 20, 16, 20));
        card.setStyle(
                "-fx-background-color: #FFFFFF;" +
                "-fx-background-radius: 14;" +
                "-fx-border-color: #EAD9C0;" +
                "-fx-border-radius: 14;" +
                "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.25), 10, 0, 0, 3);"
        );

        return card;
    }

    private static Button createPrimaryButton(String text) {
        Button button = new Button(text);
        button.setStyle(
                "-fx-background-color: #FF6B35;" +
                "-fx-text-fill: white;" +
                "-fx-font-size: 13;" +
                "-fx-font-weight: bold;" +
                "-fx-background-radius: 8;" +
                "-fx-padding: 10 16;" +
                "-fx-cursor: hand;"
        );
        return button;
    }

    private static Button createToolbarButton(String text) {
        Button button = new Button(text);
        button.setStyle(
                "-fx-background-color: #F3E7D8;" +
                "-fx-text-fill: #3F3A34;" +
                "-fx-font-size: 13;" +
                "-fx-font-weight: bold;" +
                "-fx-background-radius: 8;" +
                "-fx-border-color: #EAD9C0;" +
                "-fx-border-radius: 8;" +
                "-fx-padding: 10 16;" +
                "-fx-cursor: hand;"
        );
        return button;
    }

    private static Button createSecondaryButton(String text) {
        Button button = new Button(text);
        button.setStyle(
                "-fx-background-color: transparent;" +
                "-fx-text-fill: #FF6B35;" +
                "-fx-font-size: 13;" +
                "-fx-font-weight: bold;" +
                "-fx-border-color: #FF6B35;" +
                "-fx-border-radius: 20;" +
                "-fx-background-radius: 20;" +
                "-fx-padding: 6 18;" +
                "-fx-cursor: hand;"
        );
        return button;
    }

    private static Button createDangerButton(String text) {
        Button button = new Button(text);
        button.setStyle(
                "-fx-background-color: transparent;" +
                "-fx-text-fill: #EF4444;" +
                "-fx-font-size: 13;" +
                "-fx-font-weight: bold;" +
                "-fx-border-color: #EF4444;" +
                "-fx-border-radius: 8;" +
                "-fx-background-radius: 8;" +
                "-fx-padding: 10 16;" +
                "-fx-cursor: hand;"
        );
        return button;
    }

    private static void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}