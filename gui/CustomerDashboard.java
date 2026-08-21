package gui;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Separator;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import model.FoodItem;

public class CustomerDashboard {

    public static void show(Stage stage) {

        //-------------------------------------------------------
        // Root Layout & Theme (Deep Modern Dark Contrast)
        //-------------------------------------------------------
        BorderPane root = new BorderPane();
        root.setStyle("-fx-background-color: #FDF1E0;");

        //-------------------------------------------------------
        // Header Bar with Personalized Welcome & Logout
        //-------------------------------------------------------
        String customerName = (AppData.currentCustomer != null && AppData.currentCustomer.getName() != null)
                ? AppData.currentCustomer.getName()
                : "Guest";

        Label logo = new Label("🍽 Welcome back to Urban Bites, " + customerName + "!");
        logo.setStyle(
                "-fx-font-size: 18;" +
                "-fx-font-weight: bold;" +
                "-fx-text-fill: #FF6B35;"
        );

        Button logoutButton = createSecondaryButton("🚪 Logout");
        logoutButton.setOnAction(e -> {
            AppData.currentCustomer = null;
            Main.showMainMenu(stage);
        });

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        HBox header = new HBox(logo, spacer, logoutButton);
        header.setAlignment(Pos.CENTER_LEFT);
        header.setPadding(new Insets(18, 50, 18, 50));
        header.setStyle(
                "-fx-background-color: #FFFFFF;" +
                "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.3), 15, 0, 0, 4);"
        );
        root.setTop(header);

        //-------------------------------------------------------
        // Welcome Banner Section
        //-------------------------------------------------------
        Label title = new Label("Customer Dashboard");
        title.setStyle(
                "-fx-font-size: 26;" +
                "-fx-font-weight: bold;" +
                "-fx-text-fill: #1A1A2E;"
        );
        
        Label welcome = new Label("Manage your account, track orders, and explore popular dishes below.");
        welcome.setStyle(
                "-fx-font-size: 13;" +
                "-fx-text-fill: #78716C;"
        );

        VBox welcomeBox = new VBox(4, title, welcome);
        welcomeBox.setAlignment(Pos.CENTER_LEFT);

        //-------------------------------------------------------
        // Interactive Dashboard Quick Action Cards (Non-redundant navigation)
        //-------------------------------------------------------
        VBox menuCard = createCard("🍔", "Explore Menu", "Browse delicious dishes & beverages");
        VBox cartCard = createCard("🛒", "Your Cart", "Review items ready for checkout");
        VBox orderCard = createCard("📦", "My Orders", "Track current and past deliveries");
        VBox profileCard = createCard("👤", "My Profile", "Manage your personal account details");

        HBox topCards = new HBox(20, menuCard, cartCard, orderCard, profileCard);
        topCards.setAlignment(Pos.CENTER);

        // Connect actions to cards cleanly without duplicate button grids
        menuCard.setOnMouseClicked(e -> PublicMenu.show(stage, true));
        cartCard.setOnMouseClicked(e -> CartView.show(stage));
        orderCard.setOnMouseClicked(e -> MyOrders.show(stage));
        profileCard.setOnMouseClicked(e -> CustomerProfile.show(stage));

        //-------------------------------------------------------
        // Popular Specialties Section (Homepage layout integration)
        //-------------------------------------------------------
        Label featuredTitle = new Label("Popular Specialties");
        featuredTitle.setStyle(
                "-fx-font-size: 20;" +
                "-fx-font-weight: bold;" +
                "-fx-text-fill: #1A1A2E;"
        );

        Label featuredSubtitle = new Label("Handpicked favourites ready for quick order.");
        featuredSubtitle.setStyle(
                "-fx-font-size: 13;" +
                "-fx-text-fill: #78716C;"
        );

        VBox popularTitleBox = new VBox(3, featuredTitle, featuredSubtitle);

        HBox foodCardsBox = new HBox(18);
        foodCardsBox.setAlignment(Pos.CENTER);

        if (AppData.system.getMenu().isEmpty()) {
            Label empty = new Label("No food items available.");
            empty.setStyle("-fx-font-size: 14; -fx-text-fill: #78716C;");
            foodCardsBox.getChildren().add(empty);
        } else {
            int shown = 0;
            for (FoodItem item : AppData.system.getMenu()) {
                foodCardsBox.getChildren().add(createFoodCard(stage, item));
                shown++;
                if (shown == 4) break; // Show top 4 items cleanly in dashboard
            }
        }

        VBox popularSection = new VBox(15, popularTitleBox, foodCardsBox);

        //-------------------------------------------------------
        // Main Content Container Structure
        //-------------------------------------------------------
        VBox contentCard = new VBox(25);
        contentCard.setAlignment(Pos.TOP_LEFT);
        contentCard.setPadding(new Insets(35, 40, 35, 40));
        contentCard.setMaxWidth(960);
        contentCard.setStyle(
                "-fx-background-color: #FFFFFF;" +
                "-fx-background-radius: 20;" +
                "-fx-border-color: #EAD9C0;" +
                "-fx-border-radius: 20;" +
                "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.4), 20, 0, 0, 6);"
        );

        contentCard.getChildren().addAll(
                welcomeBox,
                new Separator() {{ setStyle("-fx-background-color: #EAD9C0;"); }},
                topCards,
                new Separator() {{ setStyle("-fx-background-color: #EAD9C0;"); }},
                popularSection
        );

        VBox centerWrapper = new VBox(contentCard);
        centerWrapper.setAlignment(Pos.CENTER);
        centerWrapper.setPadding(new Insets(30, 50, 40, 50));

        ScrollPane scrollPane = new ScrollPane(centerWrapper);
        scrollPane.setFitToWidth(true);
        scrollPane.setStyle(
                "-fx-background: #FDF1E0;" +
                "-fx-background-color: transparent;"
        );

        root.setCenter(scrollPane);

        Scene scene = new Scene(root, 1280, 800);
        scene.getStylesheets().add(Main.class.getResource("style.css").toExternalForm());
        stage.setTitle("Urban Bites - Customer Dashboard");
        stage.setScene(scene);
        stage.show();
    }

    //-------------------------------------------------------
    // Helper: Mini Food Item Card for Dashboard
    //-------------------------------------------------------
    private static VBox createFoodCard(Stage stage, FoodItem item) {
        double size = 85;

        Image image = ImageUtil.loadImage(item.getImagePath());
        ImageView imageView = image != null ? new ImageView(image) : new ImageView();

        imageView.setFitWidth(size);
        imageView.setFitHeight(size);
        imageView.setPreserveRatio(false);

        Circle clip = new Circle(size / 2, size / 2, size / 2);
        imageView.setClip(clip);
        
        StackPane imagePane = new StackPane(imageView);
        imagePane.setPrefSize(size, size);
        imagePane.setMaxSize(size, size);
        imagePane.setAlignment(Pos.CENTER);
        imagePane.setStyle(
                "-fx-background-color:#F3E7D8;" +
                "-fx-background-radius:45;" +
                "-fx-border-color: #EAD9C0;" +
                "-fx-border-radius: 45;"
        );

        Label name = new Label(item.getName());
        name.setStyle(
                "-fx-font-size: 13;" +
                "-fx-font-weight: bold;" +
                "-fx-text-fill: #1A1A2E;"
        );

        Button viewButton = new Button("View");
        viewButton.setStyle(
                "-fx-background-color: #FF6B35;" +
                "-fx-text-fill: white;" +
                "-fx-font-size: 11;" +
                "-fx-font-weight: bold;" +
                "-fx-background-radius: 15;" +
                "-fx-padding: 5 14;" +
                "-fx-cursor: hand;"
        );
        viewButton.setOnAction(e -> FoodDetailView.show(stage, item));

        VBox card = new VBox(8, imagePane, name, viewButton);
        card.setAlignment(Pos.CENTER);
        card.setPadding(new Insets(12));
        card.setPrefWidth(160);
        card.setStyle(
                "-fx-background-color: #F3E7D8;" +
                "-fx-background-radius: 14;" +
                "-fx-border-color: #EAD9C0;" +
                "-fx-border-radius: 14;"
        );

        return card;
    }

    //-------------------------------------------------------
    // Aesthetic Styling & Component Helpers
    //-------------------------------------------------------
    private static VBox createCard(String icon, String titleText, String descText) {
        Label iconLabel = new Label(icon);
        iconLabel.setStyle("-fx-font-size: 24;");

        Label titleLabel = new Label(titleText);
        titleLabel.setStyle("-fx-text-fill: #1A1A2E; -fx-font-size: 13; -fx-font-weight: bold;");

        Label descLabel = new Label(descText);
        descLabel.setStyle("-fx-text-fill: #78716C; -fx-font-size: 10;");
        descLabel.setWrapText(true);
        descLabel.setAlignment(Pos.CENTER);

        VBox card = new VBox(6, iconLabel, titleLabel, descLabel);
        card.setAlignment(Pos.CENTER);
        card.setPrefSize(180, 110);
        card.setPadding(new Insets(10));
        card.setStyle(
                "-fx-background-color: #F3E7D8;" +
                "-fx-background-radius: 12;" +
                "-fx-border-color: #EAD9C0;" +
                "-fx-border-radius: 12;" +
                "-fx-cursor: hand;"
        );

        // Hover effect
        card.setOnMouseEntered(e -> card.setStyle(
                "-fx-background-color: #EAD9C0;" +
                "-fx-background-radius: 12;" +
                "-fx-border-color: #FF6B35;" +
                "-fx-border-radius: 12;" +
                "-fx-cursor: hand;"
        ));
        card.setOnMouseExited(e -> card.setStyle(
                "-fx-background-color: #F3E7D8;" +
                "-fx-background-radius: 12;" +
                "-fx-border-color: #EAD9C0;" +
                "-fx-border-radius: 12;" +
                "-fx-cursor: hand;"
        ));

        return card;
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