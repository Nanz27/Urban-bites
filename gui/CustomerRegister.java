package gui;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import model.Customer;

public class CustomerRegister {

    public static void show(Stage stage) {

        //-------------------------------------------------------
        // ROOT
        //-------------------------------------------------------
        BorderPane root = new BorderPane();
        root.setStyle("-fx-background-color: #FDF1E0;");

        //-------------------------------------------------------
        // HEADER
        //-------------------------------------------------------
        Label logo = new Label("🍽 Urban Bites");
        logo.setStyle(
                "-fx-font-size: 24;" +
                "-fx-font-weight: bold;" +
                "-fx-text-fill: #FF6B35;"
        );

        Button backButton = createSecondaryButton("← Back to Login");
        backButton.setOnAction(e -> CustomerLogin.show(stage));

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
        // TITLE
        //-------------------------------------------------------
        Label title = new Label("Create Account");
        title.setStyle(
                "-fx-font-size: 24;" +
                "-fx-font-weight: bold;" +
                "-fx-text-fill: #1A1A2E;"
        );

        Label subtitle = new Label(
                "Register a new customer profile to start ordering."
        );

        subtitle.setStyle(
                "-fx-font-size: 13;" +
                "-fx-text-fill: #78716C;"
        );

        VBox titleBox = new VBox(4, title, subtitle);
        titleBox.setAlignment(Pos.CENTER_LEFT);

        //-------------------------------------------------------
        // USERNAME FIELD
        //-------------------------------------------------------
        VBox nameBox = new VBox(6);

        Label nameLabel = new Label("Username");
        nameLabel.setStyle(
                "-fx-text-fill: #3F3A34;" +
                "-fx-font-size: 13;" +
                "-fx-font-weight: bold;"
        );

        TextField nameField = new TextField();
        nameField.setPromptText("Enter your username");

        nameField.setStyle(
                "-fx-background-color: #F3E7D8;" +
                "-fx-text-fill: #1A1A2E;" +
                "-fx-prompt-text-fill: #6B7280;" +
                "-fx-font-size: 14;" +
                "-fx-padding: 10 14;" +
                "-fx-background-radius: 8;" +
                "-fx-border-color: #EAD9C0;" +
                "-fx-border-radius: 8;"
        );

        nameBox.getChildren().addAll(
                nameLabel,
                nameField
        );

        //-------------------------------------------------------
        // FULL NAME FIELD
        //-------------------------------------------------------
        VBox fullNameBox = new VBox(6);

        Label fullNameLabel = new Label("Full Name");
        fullNameLabel.setStyle(
                "-fx-text-fill: #3F3A34;" +
                "-fx-font-size: 13;" +
                "-fx-font-weight: bold;"
        );

        TextField fullNameField = new TextField();
        fullNameField.setPromptText("Enter your full name");

        fullNameField.setStyle(
                "-fx-background-color: #F3E7D8;" +
                "-fx-text-fill: #1A1A2E;" +
                "-fx-prompt-text-fill: #6B7280;" +
                "-fx-font-size: 14;" +
                "-fx-padding: 10 14;" +
                "-fx-background-radius: 8;" +
                "-fx-border-color: #EAD9C0;" +
                "-fx-border-radius: 8;"
        );

        fullNameBox.getChildren().addAll(
                fullNameLabel,
                fullNameField
        );

        //-------------------------------------------------------
        // PHONE FIELD
        //-------------------------------------------------------
        VBox phoneBox = new VBox(6);

        Label phoneLabel = new Label("Phone Number");
        phoneLabel.setStyle(
                "-fx-text-fill: #3F3A34;" +
                "-fx-font-size: 13;" +
                "-fx-font-weight: bold;"
        );

        TextField phoneField = new TextField();
        phoneField.setPromptText("Enter your phone number");

        phoneField.setStyle(
                "-fx-background-color: #F3E7D8;" +
                "-fx-text-fill: #1A1A2E;" +
                "-fx-prompt-text-fill: #6B7280;" +
                "-fx-font-size: 14;" +
                "-fx-padding: 10 14;" +
                "-fx-background-radius: 8;" +
                "-fx-border-color: #EAD9C0;" +
                "-fx-border-radius: 8;"
        );

        phoneBox.getChildren().addAll(
                phoneLabel,
                phoneField
        );

        //-------------------------------------------------------
        // PASSWORD FIELD
        //-------------------------------------------------------
        VBox passwordBox = new VBox(6);

        Label passwordLabel = new Label("Password");
        passwordLabel.setStyle(
                "-fx-text-fill: #3F3A34;" +
                "-fx-font-size: 13;" +
                "-fx-font-weight: bold;"
        );

        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText("Create a password");

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

        passwordBox.getChildren().addAll(
                passwordLabel,
                passwordField
        );

        //-------------------------------------------------------
        // CONFIRM PASSWORD
        //-------------------------------------------------------
        VBox confirmPasswordBox = new VBox(6);

        Label confirmPasswordLabel = new Label("Confirm Password");
        confirmPasswordLabel.setStyle(
                "-fx-text-fill: #3F3A34;" +
                "-fx-font-size: 13;" +
                "-fx-font-weight: bold;"
        );

        PasswordField confirmPasswordField = new PasswordField();
        confirmPasswordField.setPromptText("Re-enter your password");

        confirmPasswordField.setStyle(
                "-fx-background-color: #F3E7D8;" +
                "-fx-text-fill: #1A1A2E;" +
                "-fx-prompt-text-fill: #6B7280;" +
                "-fx-font-size: 14;" +
                "-fx-padding: 10 14;" +
                "-fx-background-radius: 8;" +
                "-fx-border-color: #EAD9C0;" +
                "-fx-border-radius: 8;"
        );

        confirmPasswordBox.getChildren().addAll(
                confirmPasswordLabel,
                confirmPasswordField
        );

        //-------------------------------------------------------
        // REGISTER BUTTON
        //-------------------------------------------------------
        Button registerButton = new Button("Register Account");

        registerButton.setMaxWidth(Double.MAX_VALUE);

        registerButton.setStyle(
                "-fx-background-color: #FF6B35;" +
                "-fx-text-fill: white;" +
                "-fx-font-size: 15;" +
                "-fx-font-weight: bold;" +
                "-fx-background-radius: 10;" +
                "-fx-padding: 12;" +
                "-fx-cursor: hand;"
        );

        //-------------------------------------------------------
        // REGISTER LOGIC
        //-------------------------------------------------------
        registerButton.setOnAction(e -> {

            try {

                String name = nameField.getText().trim();
                String fullName = fullNameField.getText().trim();
                String phone = phoneField.getText().trim();
                String password = passwordField.getText();
                String confirmPassword = confirmPasswordField.getText();

                //---------------------------------------------------
                // VALIDATION
                //---------------------------------------------------

                if (name.isEmpty() ||
                        fullName.isEmpty() ||
                        phone.isEmpty() ||
                        password.isEmpty() ||
                        confirmPassword.isEmpty()) {

                    throw new Exception(
                            "All fields are required."
                    );
                }

                if (!password.equals(confirmPassword)) {

                    throw new Exception(
                            "Passwords do not match."
                    );
                }

                if (password.length() < 4) {

                    throw new Exception(
                            "Password must contain at least 4 characters."
                    );
                }

                //---------------------------------------------------
                // CHECK WHETHER CUSTOMER ALREADY EXISTS
                //---------------------------------------------------

                if (AppData.system.searchCustomer(name) != null) {

                    throw new Exception(
                            "A customer with this username already exists."
                    );
                }

                //---------------------------------------------------
                // CREATE CUSTOMER
                // (Note: Update your Customer model constructor if you 
                // wish to store the full name separately, or pass it accordingly)
                //---------------------------------------------------

                int id = AppData.system.nextCustomerId();

                Customer customer =
                        new Customer(
                                id,
                                name,
                                phone,
                                password
                        );

                //---------------------------------------------------
                // SAVE CUSTOMER
                //---------------------------------------------------

                AppData.system.registerCustomer(customer);
                AppData.system.saveAll();

                AppData.currentCustomer = customer;

                //---------------------------------------------------
                // SUCCESS MESSAGE
                //---------------------------------------------------

                Alert alert =
                        new Alert(
                                Alert.AlertType.INFORMATION
                        );

                alert.setTitle("Registration Successful");
                alert.setHeaderText("Welcome to Urban Bites!");

                alert.setContentText(
                        "Your account has been created successfully.\n\n" +
                        "Customer ID: " + id
                );

                alert.showAndWait();

                //---------------------------------------------------
                // GO TO DASHBOARD
                //---------------------------------------------------

                CustomerDashboard.show(stage);

            } catch (Exception ex) {

                Alert alert =
                        new Alert(
                                Alert.AlertType.ERROR
                        );

                alert.setTitle("Registration Failed");
                alert.setHeaderText(null);

                alert.setContentText(
                        ex.getMessage()
                );

                alert.showAndWait();
            }
        });

        //-------------------------------------------------------
        // CARD
        //-------------------------------------------------------
        VBox card = new VBox(20);

        card.setAlignment(Pos.TOP_LEFT);
        card.setPadding(
                new Insets(35, 40, 35, 40)
        );

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
                nameBox,
                fullNameBox,
                phoneBox,
                passwordBox,
                confirmPasswordBox,
                registerButton
        );

        //-------------------------------------------------------
        // CENTER
        //-------------------------------------------------------
        VBox centerWrapper = new VBox(card);

        centerWrapper.setAlignment(Pos.CENTER);
        centerWrapper.setPadding(
                new Insets(50)
        );

        ScrollPane scrollPane =
                new ScrollPane(centerWrapper);

        scrollPane.setFitToWidth(true);

        scrollPane.setStyle(
                "-fx-background: #FDF1E0;" +
                "-fx-background-color: transparent;"
        );

        root.setCenter(scrollPane);

        //-------------------------------------------------------
        // SCENE
        //-------------------------------------------------------
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
                "Urban Bites - Customer Registration"
        );

        stage.setScene(scene);
        stage.show();
    }

    //-------------------------------------------------------
    // SECONDARY BUTTON
    //-------------------------------------------------------
    private static Button createSecondaryButton(
            String text
    ) {

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