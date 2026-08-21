package gui;

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

public class FoodView {

    public static void show(Stage stage) {

        //-------------------------------------------------------
        // Root Layout & Theme (Deep Modern Dark Contrast)
        //-------------------------------------------------------
        BorderPane root = new BorderPane();
        root.setStyle("-fx-background-color: #FDF1E0;");

        //-------------------------------------------------------
        // Navigation Header Bar
        //-------------------------------------------------------
        Label logo = new Label("🍽 Urban Bites");
        logo.setStyle(
                "-fx-font-size: 24;" +
                "-fx-font-weight: bold;" +
                "-fx-text-fill: #FF6B35;"
        );

        Button cartButton = createPrimaryButton("🛒 View Cart");
        cartButton.setOnAction(e -> CartView.show(stage));

        Button backButton = createSecondaryButton("← Dashboard");
        backButton.setOnAction(e -> CustomerDashboard.show(stage));

        HBox navRight = new HBox(12, cartButton, backButton);
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
        // Title & Description Header
        //-------------------------------------------------------
        Label title = new Label("Explore Menu");
        title.setStyle(
                "-fx-font-size: 26;" +
                "-fx-font-weight: bold;" +
                "-fx-text-fill: #1A1A2E;"
        );

        Label subtitle = new Label("Choose your favorite delicious meals freshly prepared by Urban Bites.");
        subtitle.setStyle(
                "-fx-font-size: 13;" +
                "-fx-text-fill: #78716C;"
        );

        VBox titleBox = new VBox(4, title, subtitle);
        titleBox.setAlignment(Pos.CENTER_LEFT);
        titleBox.setPadding(new Insets(30, 50, 15, 50));

        //-------------------------------------------------------
        // Food Grid FlowPane Container
        //-------------------------------------------------------
        FlowPane foodPane = new FlowPane();
        foodPane.setHgap(25);
        foodPane.setVgap(25);
        foodPane.setAlignment(Pos.CENTER_LEFT);
        foodPane.setPadding(new Insets(10, 50, 40, 50));

        for (FoodItem item : AppData.system.getMenu()) {
            foodPane.getChildren().add(createFoodCard(item));
        }

        ScrollPane scroll = new ScrollPane(foodPane);
        scroll.setFitToWidth(true);
        scroll.setStyle(
                "-fx-background: #FDF1E0;" +
                "-fx-background-color: transparent;"
        );

        VBox centerContainer = new VBox(0, titleBox, scroll);
        centerContainer.setStyle("-fx-background-color: #FDF1E0;");
        root.setCenter(centerContainer);

        Scene scene = new Scene(root, 1280, 800);
        stage.setTitle("Urban Bites - Explore Menu");
        stage.setScene(scene);
        stage.show();
    }

    //-------------------------------------------------------
    // Aesthetic Individual Food Card Generator
    //-------------------------------------------------------
    private static VBox createFoodCard(FoodItem item) {
        ImageView imageView = new ImageView();
        Image image = ImageUtil.loadImage(item.getImagePath());

        if (image != null) {
            imageView.setImage(image);
        }

        imageView.setFitWidth(240);
        imageView.setFitHeight(140);
        imageView.setPreserveRatio(false);

        // Rounded corners clip for card image
        Rectangle clip = new Rectangle(240, 140);
        clip.setArcWidth(12);
        clip.setArcHeight(12);
        imageView.setClip(clip);

        Label name = new Label(item.getName());
        name.setStyle(
                "-fx-font-size: 16;" +
                "-fx-font-weight: bold;" +
                "-fx-text-fill: #1A1A2E;"
        );

        Label category = new Label(item.getCategory().toString());
        category.setStyle(
                "-fx-font-size: 11;" +
                "-fx-text-fill: #78716C;" +
                "-fx-font-weight: bold;"
        );

        Label price = new Label(String.format("৳ %.2f", item.calculatePrice()));
        price.setStyle(
                "-fx-font-size: 16;" +
                "-fx-font-weight: bold;" +
                "-fx-text-fill: #FF6B35;"
        );

        HBox topInfoRow = new HBox(name, price);
        HBox.setHgrow(name, Priority.ALWAYS);
        topInfoRow.setAlignment(Pos.CENTER_LEFT);

        TextField quantity = new TextField("1");
        quantity.setPromptText("Qty");
        quantity.setStyle(
                "-fx-background-color: #F3E7D8;" +
                "-fx-text-fill: #1A1A2E;" +
                "-fx-prompt-text-fill: #6B7280;" +
                "-fx-font-size: 13;" +
                "-fx-padding: 8 12;" +
                "-fx-background-radius: 8;" +
                "-fx-border-color: #EAD9C0;" +
                "-fx-border-radius: 8;"
        );

        Button addButton = new Button("Add To Cart");
        addButton.setMaxWidth(Double.MAX_VALUE);
        addButton.setStyle(
                "-fx-background-color: #FF6B35;" +
                "-fx-text-fill: white;" +
                "-fx-font-size: 13;" +
                "-fx-font-weight: bold;" +
                "-fx-background-radius: 8;" +
                "-fx-padding: 8 14;" +
                "-fx-cursor: hand;"
        );
        
        Button viewButton = new Button("View");

        viewButton.setStyle(
                "-fx-background-color: #FF6B35;" +
                "-fx-text-fill: white;" +
                "-fx-font-size: 12;" +
                "-fx-font-weight: bold;" +
                "-fx-background-radius: 15;" +
                "-fx-padding: 7 18;" +
                "-fx-cursor: hand;"
        );

        viewButton.setOnAction(e -> {
            FoodDetailView.show(
                    (Stage) viewButton.getScene().getWindow(),
                    item
            );
        });
        HBox actionRow = new HBox(
                10,
                viewButton,
                quantity,
                addButton
        );
        
        HBox.setHgrow(addButton, Priority.ALWAYS);
        actionRow.setAlignment(Pos.CENTER);

        addButton.setOnAction(e -> {
            try {
                int qty = Integer.parseInt(quantity.getText().trim());
                if (qty <= 0) throw new Exception();

                AppData.cart.addItem(item, qty);

                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setHeaderText(null);
                alert.setContentText(item.getName() + " added to cart successfully!");
                alert.showAndWait();

            } catch (Exception ex) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setHeaderText(null);
                alert.setContentText("Please enter a valid positive quantity.");
                alert.showAndWait();
            }
        });

        VBox card = new VBox(10, imageView, topInfoRow, category, actionRow);
        card.setAlignment(Pos.TOP_LEFT);
        card.setPrefWidth(260);
        card.setPadding(new Insets(10));
        card.setStyle(
                "-fx-background-color: #FFFFFF;" +
                "-fx-background-radius: 14;" +
                "-fx-border-color: #EAD9C0;" +
                "-fx-border-radius: 14;" +
                "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.3), 12, 0, 0, 4);"
        );

        return card;
    }

    //-------------------------------------------------------
    // Helper Buttons
    //-------------------------------------------------------
    private static Button createPrimaryButton(String text) {
        Button button = new Button(text);
        button.setStyle(
                "-fx-background-color: #FF6B35;" +
                "-fx-text-fill: white;" +
                "-fx-font-size: 13;" +
                "-fx-font-weight: bold;" +
                "-fx-background-radius: 8;" +
                "-fx-padding: 8 16;" +
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
                "-fx-padding: 8 16;" +
                "-fx-cursor: hand;"
        );
        return button;
    }
}