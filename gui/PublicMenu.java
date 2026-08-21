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
import model.FoodCategory;

public class PublicMenu {

    // ===========================================================
    // DEFAULT PUBLIC MENU
    // ===========================================================

    public static void show(Stage stage) {
        show(stage, false);
    }

    // ===========================================================
    // MENU WITH VIEW TYPE
    // customerView = true  -> Customer navbar
    // customerView = false -> Public navbar
    // ===========================================================

    public static void show(Stage stage, boolean customerView) {

        // =======================================================
        // MAIN ROOT
        // =======================================================

        BorderPane root = new BorderPane();

        root.setStyle(
                "-fx-background-color:#F7F1E6;"
        );

        // =======================================================
        // NAVIGATION BAR
        // =======================================================

        Image logoImage = ImageUtil.loadImage("logo.png");

        ImageView logo = new ImageView(logoImage);

        logo.setFitWidth(145);
        logo.setFitHeight(105);
        logo.setPreserveRatio(true);
        logo.setSmooth(true);

        HBox navigation = new HBox(12);
        navigation.setAlignment(Pos.CENTER_RIGHT);

        // =======================================================
        // CUSTOMER NAVBAR
        // =======================================================

        if (customerView) {

            Button dashboardBtn =
                    createCustomerSecondaryButton("← Dashboard");

            Button ordersBtn =
                    createCustomerSecondaryButton("My Orders");

            Button cartBtn =
                    createCustomerPrimaryButton("View Cart");

            navigation.getChildren().addAll(
                    dashboardBtn,
                    ordersBtn,
                    cartBtn
            );

            // Back to Customer Dashboard
            dashboardBtn.setOnAction(
                    e -> CustomerDashboard.show(stage)
            );

            // My Orders
            ordersBtn.setOnAction(
                    e -> MyOrders.show(stage)
            );

            // View Cart
            cartBtn.setOnAction(
                    e -> CartView.show(stage)
            );

        }

        // =======================================================
        // PUBLIC / HOMEPAGE NAVBAR
        // =======================================================
        else {

            Button homeBtn = createNavButton("Home");
            Button adminBtn = createNavButton("Admin");
            Button loginBtn = createPrimaryButton("Login");
            Button registerBtn = createSecondaryButton("Register");
          

            navigation.setSpacing(22);

            navigation.getChildren().addAll(
                    homeBtn,
                    adminBtn,
                    loginBtn,
                    registerBtn
                    
            );

            // Home
            homeBtn.setOnAction(
                    e -> Main.showMainMenu(stage)
            );
            
            // Admin
            adminBtn.setOnAction(
                    e -> AdminLogin.show(stage)
            );

            // Login
            loginBtn.setOnAction(
                    e -> CustomerLogin.show(stage)
            );

            // Register
            registerBtn.setOnAction(
                    e -> CustomerRegister.show(stage)
            );

      
        }
        // =======================================================
        // HEADER
        // =======================================================

        Region spacer = new Region();

        HBox.setHgrow(
                spacer,
                Priority.ALWAYS
        );

        HBox header = new HBox(
                20,
                logo,
                spacer,
                navigation
        );

        header.setAlignment(
                Pos.CENTER_LEFT
        );

        header.setPadding(
                new Insets(
                        14,
                        45,
                        14,
                        45
                )
        );

        header.setStyle(
                "-fx-background-color:#F7F1E6;" +
                "-fx-border-color:#D8CDB9;" +
                "-fx-border-width:0 0 1 0;" +
                "-fx-effect:dropshadow(" +
                "gaussian," +
                "rgba(80,60,30,0.10)," +
                "12,0,0,3" +
                ");"
        );

        root.setTop(header);

        // =======================================================
        // TITLE SECTION
        // =======================================================

        Label titleLabel = new Label(
                "Explore Our Menu"
        );

        titleLabel.setStyle(
                "-fx-font-size:34;" +
                "-fx-font-weight:900;" +
                "-fx-font-family:'Segoe Script';" +
                "-fx-text-fill:#3F3020;"
        );

        Label subtitleLabel = new Label(
                "Discover delicious dishes freshly prepared by Urban Bites."
        );

        subtitleLabel.setStyle(
                "-fx-font-size:15;" +
                "-fx-text-fill:#756858;"
        );

        VBox titleBox = new VBox(
                7,
                titleLabel,
                subtitleLabel
        );

        titleBox.setAlignment(
                Pos.CENTER_LEFT
        );

        titleBox.setPadding(
                new Insets(
                        35,
                        55,
                        20,
                        55
                )
        );

        // =======================================================
        // MENU GRID
        // =======================================================

        FlowPane menuGrid = new FlowPane();

        menuGrid.setHgap(28);
        menuGrid.setVgap(28);

        menuGrid.setAlignment(Pos.TOP_CENTER);

        menuGrid.setPadding(
                new Insets(
                        10,
                        55,
                        50,
                        55
                )
        );

        menuGrid.setPrefWrapLength(1100);

        // =======================================================
        // POPULATE MENU
        // =======================================================

        if (AppData.system.getMenu().isEmpty()) {

            Label empty = new Label(
                    "No food items available at the moment."
            );

            empty.setStyle(
                    "-fx-font-size:16;" +
                    "-fx-text-fill:#756858;"
            );

            menuGrid.getChildren().add(empty);

        } else {

            for (FoodItem item : AppData.system.getMenu()) {

                menuGrid.getChildren().add(
                        createFoodCard(item, customerView)
                );
            }
        }

        // =======================================================
        // SCROLL PANE
        // =======================================================

        ScrollPane scrollPane =
                new ScrollPane(menuGrid);

        scrollPane.setFitToWidth(true);
        scrollPane.setFitToHeight(false);

        scrollPane.setHbarPolicy(
                ScrollPane.ScrollBarPolicy.NEVER
        );

        scrollPane.setVbarPolicy(
                ScrollPane.ScrollBarPolicy.AS_NEEDED
        );

        scrollPane.setStyle(
                "-fx-background-color:transparent;" +
                "-fx-background:transparent;"
        );

        // =======================================================
        // CENTER
        // =======================================================

        VBox centerContainer = new VBox(
                0,
                titleBox,
                scrollPane
        );

        centerContainer.setStyle(
                "-fx-background-color:transparent;"
        );

        VBox.setVgrow(
                scrollPane,
                Priority.ALWAYS
        );

        root.setCenter(centerContainer);

        // =======================================================
        // SCENE
        // =======================================================

        Scene scene = new Scene(
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
                "Urban Bites - Explore Menu"
        );

        stage.setScene(scene);
        stage.show();
    }

    // ===========================================================
    // FOOD CARD
    // ===========================================================

    private static VBox createFoodCard(
            FoodItem item,
            boolean customerView
    ) {

        double imageWidth = 240;
        double imageHeight = 300;

        // =======================================================
        // IMAGE
        // =======================================================

        Image image =
                ImageUtil.loadImage(
                        item.getImagePath()
                );

        ImageView imageView;

        if (image != null) {
            imageView = new ImageView(image);
        } else {
            imageView = new ImageView();
        }

        imageView.setFitWidth(imageWidth);
        imageView.setFitHeight(imageHeight);
        imageView.setPreserveRatio(false);

        // Rounded image
        Rectangle clip =
                new Rectangle(
                        imageWidth,
                        imageHeight
                );

        clip.setArcWidth(22);
        clip.setArcHeight(22);

        imageView.setClip(clip);

        StackPane imagePane =
                new StackPane(imageView);

        imagePane.setPrefSize(
                imageWidth,
                imageHeight
        );

        imagePane.setMaxSize(
                imageWidth,
                imageHeight
        );

        // =======================================================
        // NAME
        // =======================================================

        Label name =
                new Label(
                        item.getName()
                );

        name.setWrapText(true);

        name.setMaxWidth(
                imageWidth
        );

        name.setStyle(
                "-fx-font-size:17;" +
                "-fx-font-weight:bold;" +
                "-fx-text-fill:#3F3020;"
        );

        // =======================================================
        // CATEGORY
        // =======================================================

        Label category =
                createCategoryBadge(
                        item.getCategory()
                );

        // =======================================================
        // PRICE
        // =======================================================

        Label price =
                new Label(
                        String.format(
                                "৳ %.2f",
                                item.calculatePrice()
                        )
                );

        price.setStyle(
                "-fx-font-size:18;" +
                "-fx-font-weight:bold;" +
                "-fx-text-fill:#92713A;"
        );

        // =======================================================
        // ORDER BUTTON
        // =======================================================

     Button orderButton = 
             createGoldButton("Order Now");

     orderButton.setOnAction(e -> {

         Stage currentStage =
                 (Stage) orderButton
                         .getScene()
                         .getWindow();

         FoodDetailView.show(
                 currentStage,
                 item
         );
     });

        // =======================================================
        // BUTTON ROW
        // =======================================================

        HBox buttonRow =
                new HBox(
                        10,
                        orderButton
                );

        buttonRow.setAlignment(
                Pos.CENTER
        );

        // =======================================================
        // CARD
        // =======================================================

        VBox card =
                new VBox(
                        12,
                        imagePane,
                        name,
                        category,
                        price,
                        buttonRow
                );

        card.setAlignment(
                Pos.CENTER_LEFT
        );

        card.setPadding(
                new Insets(16)
        );

        card.setPrefWidth(282);
        card.setMinWidth(282);
        card.setMaxWidth(282);

        // =======================================================
        // CARD STYLE
        // =======================================================

        String defaultCardStyle =
                "-fx-background-color:#FFFDF8;" +
                "-fx-background-radius:20;" +
                "-fx-border-color:#E3D7C3;" +
                "-fx-border-width:1;" +
                "-fx-border-radius:20;" +
                "-fx-effect:dropshadow(" +
                "gaussian," +
                "rgba(80,60,30,0.12)," +
                "12,0,0,5" +
                ");";

        String hoverCardStyle =
                "-fx-background-color:#FFFDF8;" +
                "-fx-background-radius:20;" +
                "-fx-border-color:#92713A;" +
                "-fx-border-width:1.5;" +
                "-fx-border-radius:20;" +
                "-fx-effect:dropshadow(" +
                "gaussian," +
                "rgba(146,113,58,0.22)," +
                "16,0,0,7" +
                ");";

        card.setStyle(
                defaultCardStyle
        );

        card.setOnMouseEntered(e ->
                card.setStyle(
                        hoverCardStyle
                )
        );

        card.setOnMouseExited(e ->
                card.setStyle(
                        defaultCardStyle
                )
        );

        return card;
    }

    // ===========================================================
    // CATEGORY BADGE
    // ===========================================================

    private static Label createCategoryBadge(
            FoodCategory category
    ) {

        Label badge =
                new Label(
                        category
                                .toString()
                                .replace(
                                        "_",
                                        " "
                                )
                );

        badge.setStyle(
                "-fx-background-color:#EEE5D5;" +
                "-fx-text-fill:#6E5633;" +
                "-fx-font-size:11;" +
                "-fx-font-weight:bold;" +
                "-fx-background-radius:12;" +
                "-fx-padding:5 10;"
        );

        return badge;
    }

    // ===========================================================
    // PUBLIC NAVIGATION BUTTON
    // ===========================================================

    private static Button createNavButton(
            String text
    ) {

        Button button =
                new Button(text);

        button.setPrefWidth(100);
        button.setPrefHeight(42);

        button.setStyle(
                "-fx-background-color:transparent;" +
                "-fx-font-size:14;" +
                "-fx-font-weight:bold;" +
                "-fx-text-fill:#4A3822;" +
                "-fx-background-radius:25;" +
                "-fx-padding:10 18;" +
                "-fx-cursor:hand;"
        );

        button.setOnMouseEntered(e ->
                button.setStyle(
                        "-fx-background-color:#E8DECD;" +
                        "-fx-font-size:14;" +
                        "-fx-font-weight:bold;" +
                        "-fx-text-fill:#92713A;" +
                        "-fx-background-radius:25;" +
                        "-fx-padding:10 18;" +
                        "-fx-cursor:hand;"
                )
        );

        button.setOnMouseExited(e ->
                button.setStyle(
                        "-fx-background-color:transparent;" +
                        "-fx-font-size:14;" +
                        "-fx-font-weight:bold;" +
                        "-fx-text-fill:#4A3822;" +
                        "-fx-background-radius:25;" +
                        "-fx-padding:10 18;" +
                        "-fx-cursor:hand;"
                )
        );

        return button;
    }

    // ===========================================================
    // GOLD BUTTON
    // ===========================================================

    private static Button createGoldButton(
            String text
    ) {

        Button button =
                new Button(text);

        button.setPrefWidth(105);
        button.setPrefHeight(40);

        button.setStyle(
                "-fx-background-color:#92713A;" +
                "-fx-text-fill:#FFFDF8;" +
                "-fx-font-size:13;" +
                "-fx-font-weight:bold;" +
                "-fx-background-radius:20;" +
                "-fx-padding:8 14;" +
                "-fx-cursor:hand;"
        );

        return button;
    }

    // ===========================================================
    // OUTLINE BUTTON
    // ===========================================================

    private static Button createOutlineButton(
            String text
    ) {

        Button button =
                new Button(text);

        button.setPrefWidth(80);
        button.setPrefHeight(40);

        button.setStyle(
                "-fx-background-color:#F7F1E6;" +
                "-fx-text-fill:#92713A;" +
                "-fx-font-size:13;" +
                "-fx-font-weight:bold;" +
                "-fx-border-color:#92713A;" +
                "-fx-border-width:1.5;" +
                "-fx-border-radius:20;" +
                "-fx-background-radius:20;" +
                "-fx-padding:8 14;" +
                "-fx-cursor:hand;"
        );

        return button;
    }

    // ===========================================================
    // PUBLIC PRIMARY NAVBAR BUTTON
    // ===========================================================

    private static Button createPrimaryButton(
            String text
    ) {

        Button button =
                new Button(text);

        button.setPrefWidth(110);
        button.setPrefHeight(42);

        button.setStyle(
                "-fx-background-color:#92713A;" +
                "-fx-text-fill:#FFFDF8;" +
                "-fx-font-size:14;" +
                "-fx-font-weight:bold;" +
                "-fx-background-radius:25;" +
                "-fx-padding:10 24;" +
                "-fx-cursor:hand;"
        );

        return button;
    }

    // ===========================================================
    // PUBLIC SECONDARY NAVBAR BUTTON
    // ===========================================================

    private static Button createSecondaryButton(
            String text
    ) {

        Button button =
                new Button(text);

        button.setPrefWidth(110);
        button.setPrefHeight(42);

        button.setStyle(
                "-fx-background-color:#F7F1E6;" +
                "-fx-text-fill:#92713A;" +
                "-fx-font-size:14;" +
                "-fx-font-weight:bold;" +
                "-fx-border-color:#92713A;" +
                "-fx-border-width:2;" +
                "-fx-border-radius:25;" +
                "-fx-background-radius:25;" +
                "-fx-padding:10 24;" +
                "-fx-cursor:hand;"
        );

        return button;
    }

    // ===========================================================
    // CUSTOMER PRIMARY BUTTON
    // Used ONLY for customer navbar
    // ===========================================================

    private static Button createCustomerPrimaryButton(
            String text
    ) {

        Button button =
                new Button(text);

        button.setStyle(
                "-fx-background-color:#FF6B35;" +
                "-fx-text-fill:white;" +
                "-fx-font-size:13;" +
                "-fx-font-weight:bold;" +
                "-fx-background-radius:8;" +
                "-fx-padding:10 16;" +
                "-fx-cursor:hand;"
        );

        return button;
    }

    // ===========================================================
    // CUSTOMER SECONDARY BUTTON
    // Used ONLY for customer navbar
    // ===========================================================

    private static Button createCustomerSecondaryButton(
            String text
    ) {

        Button button =
                new Button(text);

        button.setStyle(
                "-fx-background-color:#F3E7D8;" +
                "-fx-text-fill:#3F3A34;" +
                "-fx-font-size:13;" +
                "-fx-font-weight:bold;" +
                "-fx-background-radius:8;" +
                "-fx-border-color:#EAD9C0;" +
                "-fx-border-radius:8;" +
                "-fx-padding:8 16;" +
                "-fx-cursor:hand;"
        );

        return button;
    }
}