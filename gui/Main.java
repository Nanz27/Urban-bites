package gui;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import model.FoodItem;
import model.FoodCategory;

public class Main extends Application {

    // Load saved data before the application UI starts
    @Override
    public void init() {
        AppData.system.loadAll();
    }

    @Override
    public void start(Stage stage) {
    	System.out.println("========== NEW MAIN.JAVA IS RUNNING ==========");

        stage.setTitle("Urban Bites - Premium Food Ordering");
        showMainMenu(stage);

        stage.show();
    }

    // Save data whenever the application closes
    @Override
    public void stop() {
        AppData.system.saveAll();
    }

    public static void showMainMenu(Stage stage) {

        BorderPane root = new BorderPane();

        // Main page background
        root.setStyle(
                "-fx-background-color: linear-gradient(to bottom right, " +
                "#FDF1E0 0%, #FCE7D0 45%, #FBDFC0 100%);"
        );

        // =======================================================
        // NAVIGATION BAR
        // =======================================================

        Image logoImage = ImageUtil.loadImage("logo.png");

        ImageView logoView = new ImageView(logoImage);

        logoView.setFitWidth(145);
        logoView.setFitHeight(105);
        logoView.setPreserveRatio(true);
        logoView.setSmooth(true);

        Button adminBtn = createNavButton("Admin");
        Button exitBtn = createNavButton("Exit");
        Button registerBtn = createSecondaryButton("Register");
        Button loginBtn = createPrimaryButton("Login");

        HBox navigation = new HBox(
                22,
                loginBtn,
                registerBtn,
                adminBtn,
                exitBtn
        );

        navigation.setAlignment(Pos.CENTER_RIGHT);

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        HBox header = new HBox();

        header.getChildren().addAll(
        		logoView,
                spacer,
                navigation
        );

        header.setAlignment(Pos.CENTER_LEFT);
        header.setPadding(new Insets(16, 45, 16, 45));

        header.setStyle(
                "-fx-background-color:#F7F1E6;" +
                "-fx-background-radius:0 0 22 22;" +
                "-fx-border-color:#D8CDB9;" +
                "-fx-border-width:0 0 1 0;" +
                "-fx-effect:dropshadow(gaussian,rgba(80,60,30,0.12),14,0,0,4);"
       
        );

        root.setTop(header);


        // =======================================================
        // HERO SECTION
        // Heading
        // Pictures
        // Description
        // Explore Menu
        // =======================================================

        Label heroTitle = new Label(
                "Delicious Food Is Waiting For You"
        );

        heroTitle.setAlignment(Pos.CENTER);
        heroTitle.setTextAlignment(
                javafx.scene.text.TextAlignment.CENTER
        );

        heroTitle.setStyle(
                "-fx-font-size:40;" +
                "-fx-font-family:'Bradley Hand ITC';" +
                "-fx-font-weight:bold;" +
                "-fx-text-fill:#92713A;"
        );

        // =======================================================
        // FOUR FOOD PICTURES
        // =======================================================

        HBox heroPhotoRow = new HBox(5);

        heroPhotoRow.setAlignment(Pos.CENTER);
        String[] heroPhotos = {
                "main1.jpg",
                "main2.jpg",
                "main3.jpg",
                "main4.jpg"
        };

        for (String photo : heroPhotos) {
            heroPhotoRow.getChildren().add(
                    createHeroPhoto(photo)
            );
        }


        // =======================================================
        // DESCRIPTION BELOW PICTURES
        // =======================================================

        Label heroSubtitle = new Label(
                "Order your favourite meals from Urban Bites. "
                        + "Enjoy freshly prepared dishes, premium ingredients, "
                        + "and quick delivery straight to your doorstep."
        );

        heroSubtitle.setWrapText(true);
        heroSubtitle.setMaxWidth(700);
        heroSubtitle.setAlignment(Pos.CENTER);

        heroSubtitle.setTextAlignment(
                javafx.scene.text.TextAlignment.CENTER
        );

        heroSubtitle.setStyle(
                "-fx-font-size:15;" +
                "-fx-text-fill:#6B6259;"
        );


        // =======================================================
        // EXPLORE MENU BUTTON
        // =======================================================

        Button exploreButton = createPrimaryButton(
                "Explore Menu"
        );

        exploreButton.setPrefWidth(170);

        exploreButton.setOnAction(
                e -> PublicMenu.show(stage)
        );


        // =======================================================
        // PUT HERO ELEMENTS TOGETHER
        // =======================================================

        VBox heroContent = new VBox(
                24,
                heroTitle,
                heroPhotoRow,
                heroSubtitle,
                exploreButton
        );

        heroContent.setAlignment(Pos.CENTER);
        heroContent.setPadding(
                new Insets(30, 40, 35, 40)
        );


        VBox heroWrapper = new VBox(heroContent);
        heroWrapper.setAlignment(Pos.CENTER);
        heroWrapper.setMaxWidth(1220);
        heroWrapper.setPadding(
                new Insets(12, 40, 12, 40)
        );


        // =======================================================
        // POPULAR SPECIALTIES
        // =======================================================
        Label popularLabel = new Label("✦  CUSTOMER FAVOURITES");

        popularLabel.setStyle(
                "-fx-font-size: 11;" +
                "-fx-font-weight: bold;" +
                "-fx-text-fill: #FF6B35;"
        );


        Label featuredTitle = new Label(
                "Popular Specialties"
        );

        featuredTitle.setStyle(
                "-fx-font-size: 30;" +
                "-fx-font-weight: bold;" +
                "-fx-text-fill: #211A14;"
        );


        Label featuredSubtitle = new Label(
                "Discover the dishes our customers love the most."
        );

        featuredSubtitle.setStyle(
                "-fx-font-size: 14;" +
                "-fx-text-fill: #786F66;"
        );


        VBox titleBox = new VBox(
                6,
                popularLabel,
                featuredTitle,
                featuredSubtitle
        );

        titleBox.setAlignment(Pos.CENTER_LEFT);


        // Orange accent line
        Region accentLine = new Region();

        accentLine.setPrefHeight(3);
        accentLine.setMaxWidth(55);

        accentLine.setStyle(
                "-fx-background-color: #FF6B35;" +
                "-fx-background-radius: 5;"
        );


        // Food cards
        HBox cards = new HBox(24);

        cards.setAlignment(Pos.CENTER);

     // =======================================================
     // FOOD CARDS
     // =======================================================

     cards.setPadding(
             new Insets(5, 5, 10, 5)
     );


     if (AppData.system.getMenu().isEmpty()) {

         Label empty = new Label(
                 "No food items available."
         );

         empty.setStyle(
                 "-fx-font-size: 15;" +
                 "-fx-text-fill: #6B6259;" +
                 "-fx-padding: 30;"
         );

         cards.getChildren().add(empty);

     } else {

         int shown = 0;

         for (FoodItem item : AppData.system.getMenu()) {

             cards.getChildren().add(
                     createFoodCard(stage, item)
             );

             shown++;

             if (shown == 4) {
                 break;
             }
         }
     }


     // =======================================================
     // POPULAR SPECIALTIES PANEL
     // =======================================================

     VBox featuredSection = new VBox(
    	        18,
    	        titleBox,
    	        accentLine,
    	        cards
    	);

     featuredSection.setPadding(
             new Insets(32, 38, 38, 38)
     );

     featuredSection.setStyle(
             "-fx-background-color: #FFF9F3;" +
             "-fx-background-radius: 28;" +
             "-fx-border-color: #F1D9C2;" +
             "-fx-border-width: 1;" +
             "-fx-border-radius: 28;" +
             "-fx-effect: dropshadow(gaussian, rgba(90,55,25,0.12), 22, 0, 0, 7);"
     );
        // =======================================================
        // WHY CHOOSE US
        // =======================================================

        Label whyTitle = new Label(
                "Why Choose Urban Bites?"
        );

        whyTitle.setStyle(
                "-fx-font-size:24;" +
                "-fx-font-weight:800;" +
                "-fx-text-fill:#211A14;"
        );


        VBox feature1 = createInfoCard(
                "🚀",
                "Fast Delivery",
                "Fresh meals delivered quickly to your doorstep.",
                "#3B82F6"
        );


        VBox feature2 = createInfoCard(
                "🥗",
                "Fresh Ingredients",
                "Prepared daily using high quality ingredients.",
                "#22C55E"
        );


        VBox feature3 = createInfoCard(
                "⭐",
                "Top Rated",
                "Loved by hundreds of satisfied customers.",
                "#F5A524"
        );


        HBox infoCards = new HBox(
                25,
                feature1,
                feature2,
                feature3
        );

        infoCards.setAlignment(Pos.CENTER);


        VBox whySection = new VBox(
                20,
                whyTitle,
                infoCards
        );

        whySection.setPadding(
                new Insets(20, 45, 40, 45)
        );

        whySection.setAlignment(Pos.CENTER);


        // =======================================================
        // FOOTER
        // =======================================================

        VBox footer = new VBox(8);

        footer.setAlignment(Pos.CENTER);

        footer.setPadding(
                new Insets(38)
        );

        footer.setStyle(
                "-fx-background-color: #FFFFFF;" +
                "-fx-background-radius: 28 28 0 0;" +
                "-fx-border-color: #EAD9C0;" +
                "-fx-border-width: 1 0 0 0;"
        );

        // Footer message
        Label footerText = new Label(
                "Fresh food. Fast delivery. Great experience."
        );

        footerText.setStyle(
                "-fx-text-fill: #78716C;" +
                "-fx-font-size: 13;" +
                "-fx-font-weight: bold;"
        );

        footer.getChildren().add(
                footerText
        );

        // =======================================================
        // MAIN CONTENT
        // =======================================================

        VBox content = new VBox(
                15,
                heroWrapper,
                featuredSection,
                whySection,
                footer
        );

        content.setStyle(
                "-fx-background-color: transparent;"
        );


        // =======================================================
        // SCROLL PANE
        // =======================================================

        ScrollPane scroll = new ScrollPane(
                content
        );

        scroll.setFitToWidth(true);

        scroll.setStyle(
                "-fx-background: transparent;" +
                "-fx-background-color:transparent;"
        );


        root.setCenter(scroll);


        // =======================================================
        // NAVIGATION EVENTS
        // =======================================================

        loginBtn.setOnAction(
                e -> CustomerLogin.show(stage)
        );

        registerBtn.setOnAction(
                e -> CustomerRegister.show(stage)
        );

        adminBtn.setOnAction(
                e -> AdminLogin.show(stage)
        );

        exitBtn.setOnAction(
                e -> stage.close()
        );


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

        stage.setScene(scene);
    }


    // ===========================================================
    // HERO PHOTO
    // ===========================================================
    private static ImageView createHeroPhoto(String imagePath) {

        Image image = ImageUtil.loadImage(imagePath);

        ImageView imageView =
                image != null
                        ? new ImageView(image)
                        : new ImageView();

        // EXACT reference-style proportions
        double width = 253;
        double height = 400;

        imageView.setFitWidth(width);
        imageView.setFitHeight(height);

        // Crop the image to completely fill the panel
        imageView.setPreserveRatio(false);
        imageView.setSmooth(true);

        // Rounded rectangle exactly like the reference
        Rectangle clip = new Rectangle(width, height);

        clip.setArcWidth(50);
        clip.setArcHeight(50);

        imageView.setClip(clip);

        return imageView;
    }
    // ===========================================================
    // FOOD CARD
    // ===========================================================

    private static VBox createFoodCard(
            Stage stage,
            FoodItem item
    ) {

        double size = 150;


        Image image = ImageUtil.loadImage(
                item.getImagePath()
        );


        ImageView imageView =
                image != null
                        ? new ImageView(image)
                        : new ImageView();


        imageView.setFitWidth(size);
        imageView.setFitHeight(size);

        imageView.setPreserveRatio(false);


        Rectangle clip = new Rectangle(
                size,
                size
        );

        clip.setArcWidth(20);
        clip.setArcHeight(20);


        imageView.setClip(clip);


        StackPane imagePane =
                new StackPane(imageView);


        imagePane.setPrefSize(
                size,
                size
        );

        imagePane.setMaxSize(
                size,
                size
        );

        imagePane.setAlignment(
                Pos.CENTER
        );


        imagePane.setStyle(
                "-fx-background-color:#FBEEDD;" +
                "-fx-background-radius:20;"
        );


        // Food name
        Label name = new Label(
                item.getName()
        );

        name.setStyle(
                "-fx-font-size:15;" +
                "-fx-font-weight:bold;" +
                "-fx-text-fill:#211A14;"
        );


        // Category
        Label categoryBadge =
                createCategoryBadge(
                        item.getCategory()
                );


        // Price
        Label price = new Label(
                String.format(
                        "$%.2f",
                        item.calculatePrice()
                )
        );

        price.setStyle(
                "-fx-font-size:15;" +
                "-fx-font-weight:800;" +
                "-fx-text-fill:#E85A24;"
        );


        HBox metaRow =
                new HBox(categoryBadge);

        metaRow.setAlignment(
                Pos.CENTER
        );


        // View button
        Button viewButton =
                createPrimaryButton("View");

        viewButton.setPrefWidth(120);


        viewButton.setOnAction(
                e -> FoodDetailView.show(
                        stage,
                        item
                )
        );


        VBox card = new VBox(
                10,
                imagePane,
                name,
                metaRow,
                price,
                viewButton
        );


        card.setAlignment(
                Pos.CENTER
        );

        card.setPadding(
                new Insets(18)
        );

        card.setPrefWidth(190);


        String defaultCardStyle =
                "-fx-background-color:#FFFFFF;" +
                "-fx-background-radius:22;" +
                "-fx-effect:dropshadow(gaussian,rgba(120,80,40,0.12),16,0,0,6);";


        String hoverCardStyle =
                "-fx-background-color:#FFFFFF;" +
                "-fx-background-radius:22;" +
                "-fx-border-color:#FF6B35;" +
                "-fx-border-width:1.5;" +
                "-fx-border-radius:22;" +
                "-fx-effect:dropshadow(gaussian,rgba(255,107,53,0.25),20,0,0,8);";


        card.setStyle(
                defaultCardStyle
        );


        card.setOnMouseEntered(
                e -> card.setStyle(
                        hoverCardStyle
                )
        );


        card.setOnMouseExited(
                e -> card.setStyle(
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

        Label badge = new Label(
                category
                        .toString()
                        .replace("_", " ")
        );


        switch (category) {

            case VEG ->
                    badge.getStyleClass()
                            .add("badge-veg");

            case NON_VEG ->
                    badge.getStyleClass()
                            .add("badge-nonveg");

            case BEVERAGE ->
                    badge.getStyleClass()
                            .add("badge-beverage");
        }


        return badge;
    }


    // ===========================================================
    // INFORMATION CARD
    // ===========================================================

    private static VBox createInfoCard(
            String icon,
            String title,
            String description,
            String accentColor
    ) {

        Label iconLabel =
                new Label(icon);

        iconLabel.setStyle(
                "-fx-font-size:30;"
        );


        StackPane iconCircle =
                new StackPane(iconLabel);


        iconCircle.setPrefSize(
                64,
                64
        );

        iconCircle.setMaxSize(
                64,
                64
        );


        iconCircle.setStyle(
                "-fx-background-color:derive(" +
                accentColor +
                ",85%);" +
                "-fx-background-radius:32;" +
                "-fx-border-color:" +
                accentColor +
                ";" +
                "-fx-border-width:1.5;" +
                "-fx-border-radius:32;"
        );


        Label titleLabel =
                new Label(title);


        titleLabel.setStyle(
                "-fx-font-size:18;" +
                "-fx-font-weight:bold;" +
                "-fx-text-fill:#211A14;"
        );


        Label descriptionLabel =
                new Label(description);


        descriptionLabel.setWrapText(
                true
        );

        descriptionLabel.setMaxWidth(
                220
        );


        descriptionLabel.setStyle(
                "-fx-font-size:13;" +
                "-fx-text-fill:#6B6259;" +
                "-fx-text-alignment:center;"
        );


        VBox card = new VBox(
                12,
                iconCircle,
                titleLabel,
                descriptionLabel
        );


        card.setAlignment(
                Pos.CENTER
        );

        card.setPrefWidth(
                250
        );

        card.setPadding(
                new Insets(25)
        );


        card.setStyle(
                "-fx-background-color:#FFFFFF;" +
                "-fx-background-radius:18;" +
                "-fx-effect:dropshadow(gaussian,rgba(120,80,40,0.10),14,0,0,5);"
        );


        return card;
    }


    // ===========================================================
    // NAVIGATION BUTTON
    // ===========================================================

    private static Button createNavButton(String text) {

    	    Button button = new Button(text);

    	    button.setPrefWidth(90);
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

    	    button.setOnMouseEntered(e -> button.setStyle(
    	            "-fx-background-color:#E8DECD;" +
    	            "-fx-font-size:14;" +
    	            "-fx-font-weight:bold;" +
    	            "-fx-text-fill:#92713A;" +
    	            "-fx-background-radius:25;" +
    	            "-fx-padding:10 18;" +
    	            "-fx-cursor:hand;"
    	    ));

    	    button.setOnMouseExited(e -> button.setStyle(
    	            "-fx-background-color:transparent;" +
    	            "-fx-font-size:14;" +
    	            "-fx-font-weight:bold;" +
    	            "-fx-text-fill:#4A3822;" +
    	            "-fx-background-radius:25;" +
    	            "-fx-padding:10 18;" +
    	            "-fx-cursor:hand;"
    	    ));

    	    return button;
    	}

    // ===========================================================
    // PRIMARY BUTTON
    // ===========================================================

    private static Button createPrimaryButton(String text) {

        Button button = new Button(text);

        button.setPrefWidth(100);
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
    // SECONDARY BUTTON
    // ===========================================================
    	private static Button createSecondaryButton(String text) {

    	    Button button = new Button(text);

    	    button.setPrefWidth(110);
    	    button.setPrefHeight(42);

    	    button.setStyle(
    	            "-fx-background-color:#F7F1E6;" +
    	            "-fx-text-fill:#92713A;" +
    	            "-fx-font-size:14;" +
    	            "-fx-font-weight:bold;" +
    	            "-fx-border-color:#92713A;" +
    	            "-fx-border-width:2;" +
    	            "-fx-border-style:solid;" +
    	            "-fx-border-radius:25;" +
    	            "-fx-background-radius:25;" +
    	            "-fx-padding:10 24;" +
    	            "-fx-cursor:hand;"
    	    );

    	    return button;
    	}


    // ===========================================================
    // MAIN
    // ===========================================================

    public static void main(String[] args) {
        launch(args);
    }
}