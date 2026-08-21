package gui;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.Customer;

public class CustomerLogin {

    public static void show(Stage stage) {

        //-------------------------------------------------------
        // ROOT LAYOUT & THEME
        //-------------------------------------------------------

        VBox root = new VBox();

        root.setAlignment(Pos.CENTER);

        root.setStyle(
                "-fx-background-color: #FDF1E0;"
        );

        //-------------------------------------------------------
        // BACK TO HOME BUTTON
        //-------------------------------------------------------

        Button backBtn = new Button("← Back to Home");

        backBtn.setStyle(
                "-fx-background-color: transparent;" +
                "-fx-text-fill: #78716C;" +
                "-fx-font-size: 13;" +
                "-fx-font-weight: bold;" +
                "-fx-cursor: hand;"
        );

        backBtn.setOnAction(e ->
                Main.showMainMenu(stage)
        );

        //-------------------------------------------------------
        // LOGIN CARD
        //-------------------------------------------------------

        VBox card = new VBox(20);

        card.setAlignment(Pos.CENTER_LEFT);

        card.setPadding(
                new Insets(40, 45, 40, 45)
        );

        card.setMaxWidth(440);

        card.setStyle(
                "-fx-background-color: #FFFFFF;" +
                "-fx-background-radius: 20;" +
                "-fx-border-color: #EAD9C0;" +
                "-fx-border-radius: 20;" +
                "-fx-effect: dropshadow(" +
                "gaussian," +
                "rgba(0,0,0,0.4)," +
                "20,0,0,6" +
                ");"
        );

        //-------------------------------------------------------
        // HEADER
        //-------------------------------------------------------

        Label title = new Label("Welcome Back");

        title.setStyle(
                "-fx-font-size: 26;" +
                "-fx-font-weight: bold;" +
                "-fx-text-fill: #1A1A2E;"
        );

        Label subtitle = new Label(
                "Log in to order delicious meals from Urban Bites"
        );

        subtitle.setStyle(
                "-fx-font-size: 13;" +
                "-fx-text-fill: #78716C;"
        );

        VBox headerBox = new VBox(
                6,
                title,
                subtitle
        );

        headerBox.setAlignment(
                Pos.CENTER_LEFT
        );

        //-------------------------------------------------------
        // FORM FIELDS
        //-------------------------------------------------------

        VBox formFields = new VBox(15);

        //-------------------------------------------------------
        // CUSTOMER NAME
        //-------------------------------------------------------

        VBox nameBox = new VBox(6);

        Label nameLabel = new Label(
                "Customer Username"
        );

        nameLabel.setStyle(
                "-fx-text-fill: #3F3A34;" +
                "-fx-font-size: 13;" +
                "-fx-font-weight: bold;"
        );

        TextField nameField = new TextField();

        nameField.setPromptText(
                "Enter your username"
        );

        nameField.setStyle(
                "-fx-background-color: #F3E7D8;" +
                "-fx-text-fill: #1A1A2E;" +
                "-fx-prompt-text-fill: #6B7280;" +
                "-fx-font-size: 14;" +
                "-fx-padding: 12 16;" +
                "-fx-background-radius: 10;" +
                "-fx-border-color: #EAD9C0;" +
                "-fx-border-radius: 10;"
        );

        nameBox.getChildren().addAll(
                nameLabel,
                nameField
        );

        //-------------------------------------------------------
        // PASSWORD
        //-------------------------------------------------------

        VBox passBox = new VBox(6);

        Label passLabel = new Label(
                "Password"
        );

        passLabel.setStyle(
                "-fx-text-fill: #3F3A34;" +
                "-fx-font-size: 13;" +
                "-fx-font-weight: bold;"
        );

        PasswordField passwordField =
                new PasswordField();

        passwordField.setPromptText(
                "Enter your password"
        );

        passwordField.setStyle(
                "-fx-background-color: #F3E7D8;" +
                "-fx-text-fill: #1A1A2E;" +
                "-fx-prompt-text-fill: #6B7280;" +
                "-fx-font-size: 14;" +
                "-fx-padding: 12 16;" +
                "-fx-background-radius: 10;" +
                "-fx-border-color: #EAD9C0;" +
                "-fx-border-radius: 10;"
        );

        passBox.getChildren().addAll(
                passLabel,
                passwordField
        );

        //-------------------------------------------------------
        // ADD FIELDS TO FORM
        //-------------------------------------------------------

        formFields.getChildren().addAll(
                nameBox,
                passBox
        );

        //-------------------------------------------------------
        // LOGIN BUTTON
        //-------------------------------------------------------

        Button loginButton = new Button(
                "Login"
        );

        loginButton.setMaxWidth(
                Double.MAX_VALUE
        );

        loginButton.setStyle(
                "-fx-background-color: #FF6B35;" +
                "-fx-text-fill: white;" +
                "-fx-font-size: 15;" +
                "-fx-font-weight: bold;" +
                "-fx-background-radius: 10;" +
                "-fx-padding: 12;" +
                "-fx-cursor: hand;"
        );

        //-------------------------------------------------------
        // LOGIN LOGIC
        //-------------------------------------------------------

        loginButton.setOnAction(e -> {

            // Get entered name
            String name =
                    nameField.getText().trim();

            // Get entered password
            String password =
                    passwordField.getText();

            //---------------------------------------------------
            // CHECK EMPTY NAME
            //---------------------------------------------------

            if (name.isEmpty()) {

                showAlert(
                        Alert.AlertType.WARNING,
                        "Validation Error",
                        "Please enter your name."
                );

                return;
            }

            //---------------------------------------------------
            // CHECK EMPTY PASSWORD
            //---------------------------------------------------

            if (password.isEmpty()) {

                showAlert(
                        Alert.AlertType.WARNING,
                        "Validation Error",
                        "Please enter your password."
                );

                return;
            }

            //---------------------------------------------------
            // FIND CUSTOMER
            //---------------------------------------------------

            Customer customer =
                    AppData.system.searchCustomer(name);

            //---------------------------------------------------
            // CHECK CUSTOMER + PASSWORD
            //---------------------------------------------------

            if (customer != null &&
                    customer.getPassword().equals(password)) {

                //------------------------------------------------
                // LOGIN SUCCESSFUL
                //------------------------------------------------

                AppData.currentCustomer =
                        customer;

                showAlert(
                        Alert.AlertType.INFORMATION,
                        "Success",
                        "Welcome back, "
                                + customer.getName()
                                + "!"
                );

                //------------------------------------------------
                // GO TO CUSTOMER DASHBOARD
                //------------------------------------------------

                CustomerDashboard.show(stage);

            } else {

                //------------------------------------------------
                // LOGIN FAILED
                //------------------------------------------------

                showAlert(
                        Alert.AlertType.ERROR,
                        "Login Failed",
                        "Incorrect name or password."
                );
            }
        });

        //-------------------------------------------------------
        // REGISTER FOOTER
        //-------------------------------------------------------

        Label noAccountLabel =
                new Label(
                        "Don't have an account?"
                );

        noAccountLabel.setStyle(
                "-fx-text-fill: #78716C;" +
                "-fx-font-size: 13;"
        );

        Button registerRedirectBtn =
                new Button("Register");

        registerRedirectBtn.setStyle(
                "-fx-background-color: transparent;" +
                "-fx-text-fill: #FF6B35;" +
                "-fx-font-size: 13;" +
                "-fx-font-weight: bold;" +
                "-fx-cursor: hand;" +
                "-fx-padding: 0;"
        );

        registerRedirectBtn.setOnAction(e ->
                CustomerRegister.show(stage)
        );

        HBox registerRow = new HBox(
                6,
                noAccountLabel,
                registerRedirectBtn
        );

        registerRow.setAlignment(
                Pos.CENTER
        );

        registerRow.setMaxWidth(
                Double.MAX_VALUE
        );

        //-------------------------------------------------------
        // ASSEMBLE LOGIN CARD
        //-------------------------------------------------------

        card.getChildren().addAll(
                headerBox,
                formFields,
                loginButton,
                registerRow
        );

        //-------------------------------------------------------
        // MAIN CONTAINER
        //-------------------------------------------------------

        VBox mainContainer = new VBox(
                20,
                backBtn,
                card
        );

        mainContainer.setAlignment(
                Pos.CENTER
        );

        mainContainer.setPadding(
                new Insets(30)
        );

        root.getChildren().add(
                mainContainer
        );

        //-------------------------------------------------------
        // SCENE
        //-------------------------------------------------------

        Scene scene = new Scene(
                root,
                1280,
                800
        );

        stage.setTitle(
                "Urban Bites - Customer Login"
        );

        stage.setScene(scene);

        stage.show();
    }

    //-------------------------------------------------------
    // ALERT HELPER
    //-------------------------------------------------------

    private static void showAlert(
            Alert.AlertType type,
            String title,
            String message
    ) {

        Alert alert =
                new Alert(type);

        alert.setHeaderText(null);

        alert.setTitle(title);

        alert.setContentText(message);

        alert.showAndWait();
    }
}