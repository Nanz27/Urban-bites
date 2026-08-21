package gui;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

public class AdminLogin {

    public static void show(Stage stage) {

        //-------------------------------------------------------
        // Root Layout & Theme (Deep Modern Dark Contrast)
        //-------------------------------------------------------
        BorderPane root = new BorderPane();
        root.setStyle("-fx-background-color: #FDF1E0;");

        //-------------------------------------------------------
        // Header Bar with Back Button
        //-------------------------------------------------------
        Label logo = new Label("🍽 Urban Bites");
        logo.setStyle(
                "-fx-font-size: 24;" +
                "-fx-font-weight: bold;" +
                "-fx-text-fill: #FF6B35;"
        );

        Button backButton = createSecondaryButton("← Back to Main Menu");
        backButton.setOnAction(e -> Main.showMainMenu(stage));

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
        // Form Components
        //-------------------------------------------------------
        Label title = new Label("Admin Portal");
        title.setStyle(
                "-fx-font-size: 24;" +
                "-fx-font-weight: bold;" +
                "-fx-text-fill: #1A1A2E;"
        );

        Label subtitle = new Label("Sign in to access system management and inventory controls.");
        subtitle.setStyle(
                "-fx-font-size: 13;" +
                "-fx-text-fill: #78716C;"
        );

        VBox titleBox = new VBox(4, title, subtitle);
        titleBox.setAlignment(Pos.CENTER_LEFT);

        // Username Field
        VBox usernameBox = new VBox(6);
        Label usernameLabel = new Label("Username");
        usernameLabel.setStyle("-fx-text-fill: #3F3A34; -fx-font-size: 13; -fx-font-weight: bold;");
        TextField usernameField = new TextField();
        usernameField.setPromptText("Enter admin username");
        usernameField.setStyle(
                "-fx-background-color: #F3E7D8;" +
                "-fx-text-fill: #1A1A2E;" +
                "-fx-prompt-text-fill: #6B7280;" +
                "-fx-font-size: 14;" +
                "-fx-padding: 10 14;" +
                "-fx-background-radius: 8;" +
                "-fx-border-color: #EAD9C0;" +
                "-fx-border-radius: 8;"
        );
        usernameBox.getChildren().addAll(usernameLabel, usernameField);

        // Password Field
        VBox passwordBox = new VBox(6);
        Label passwordLabel = new Label("Password");
        passwordLabel.setStyle("-fx-text-fill: #3F3A34; -fx-font-size: 13; -fx-font-weight: bold;");
        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText("Enter admin password");
        passwordField.setStyle(
                "-fx-background-color: #F3E7D8;" +
                "-fx-text-fill: #1A1A2E;" +
                "-fx-prompt-text-fill: #6B7280;" +
                "-fx-font-size: 14;" +
                "-fx-padding: 10 14;" +
                "-fx-background-radius: 8;" +
                "-fx-border-color: #EAD9C0;" +
                "-fx-border-radius: 8;"
        );
        passwordBox.getChildren().addAll(passwordLabel, passwordField);

        // Login Button
        Button loginButton = new Button("Login to Dashboard");
        loginButton.setMaxWidth(Double.MAX_VALUE);
        loginButton.setStyle(
                "-fx-background-color: #FF6B35;" +
                "-fx-text-fill: white;" +
                "-fx-font-size: 15;" +
                "-fx-font-weight: bold;" +
                "-fx-background-radius: 10;" +
                "-fx-padding: 12;" +
                "-fx-cursor: hand;"
        );

        loginButton.setOnAction(e -> {
            String username = usernameField.getText().trim();
            String password = passwordField.getText().trim();

            if (username.equals(AppData.admin.getUsername())
                    && password.equals(AppData.admin.getPassword())) {
                FoodMenu.show(stage);
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText("Login Failed");
                alert.setContentText("Invalid username or password.");
                alert.showAndWait();
            }
        });

        //-------------------------------------------------------
        // Card Container Assembly
        //-------------------------------------------------------
        VBox card = new VBox(20);
        card.setAlignment(Pos.TOP_LEFT);
        card.setPadding(new Insets(35, 40, 35, 40));
        card.setMaxWidth(480);
        card.setStyle(
                "-fx-background-color: #FFFFFF;" +
                "-fx-background-radius: 20;" +
                "-fx-border-color: #EAD9C0;" +
                "-fx-border-radius: 20;" +
                "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.4), 20, 0, 0, 6);"
        );

        card.getChildren().addAll(
                titleBox,
                usernameBox,
                passwordBox,
                loginButton
        );

        VBox centerWrapper = new VBox(card);
        centerWrapper.setAlignment(Pos.CENTER);
        centerWrapper.setPadding(new Insets(50));

        ScrollPane scrollPane = new ScrollPane(centerWrapper);
        scrollPane.setFitToWidth(true);
        scrollPane.setStyle(
                "-fx-background: #FDF1E0;" +
                "-fx-background-color: transparent;"
        );

        root.setCenter(scrollPane);

        Scene scene = new Scene(root, 1280, 800);
        scene.getStylesheets().add(Main.class.getResource("style.css").toExternalForm());
        stage.setTitle("Urban Bites - Admin Portal");
        stage.setScene(scene);
        stage.show();
    }

    //-------------------------------------------------------
    // Helper Buttons
    //-------------------------------------------------------
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