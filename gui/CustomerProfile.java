package gui;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

public class CustomerProfile {

    public static void show(Stage stage) {

        //-------------------------------------------------------
        // ROOT LAYOUT
        //-------------------------------------------------------

        BorderPane root = new BorderPane();

        root.setStyle(
                "-fx-background-color: #FDF1E0;"
        );

        //-------------------------------------------------------
        // HEADER BAR
        //-------------------------------------------------------

        Label logo = new Label("🍽 Urban Bites");

        logo.setStyle(
                "-fx-font-size: 24;" +
                "-fx-font-weight: bold;" +
                "-fx-text-fill: #FF6B35;"
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

        HBox header =
                new HBox(
                        logo,
                        spacer,
                        backButton
                );

        header.setAlignment(
                Pos.CENTER_LEFT
        );

        header.setPadding(
                new Insets(
                        18,
                        50,
                        18,
                        50
                )
        );

        header.setStyle(
                "-fx-background-color: #FFFFFF;" +
                "-fx-effect: dropshadow(" +
                "gaussian," +
                "rgba(0,0,0,0.3)," +
                "15,0,0,4" +
                ");"
        );

        root.setTop(header);

        //-------------------------------------------------------
        // TITLE
        //-------------------------------------------------------

        Label title =
                new Label("Profile");

        title.setStyle(
                "-fx-font-size: 24;" +
                "-fx-font-weight: bold;" +
                "-fx-text-fill: #1A1A2E;"
        );

        Label subtitle =
                new Label(
                        "Update your personal details, delivery address, " +
                        "password, and view your order summary."
                );

        subtitle.setWrapText(true);

        subtitle.setStyle(
                "-fx-font-size: 13;" +
                "-fx-text-fill: #78716C;"
        );

        VBox titleBox =
                new VBox(
                        4,
                        title,
                        subtitle
                );

        titleBox.setAlignment(
                Pos.CENTER_LEFT
        );

        //-------------------------------------------------------
        // DETAILS CONTAINER
        //-------------------------------------------------------

        VBox detailsBox =
                new VBox(15);

        detailsBox.setPadding(
                new Insets(
                        10,
                        0,
                        10,
                        0
                )
        );

        //-------------------------------------------------------
        // CHECK CUSTOMER LOGIN
        //-------------------------------------------------------

        if (AppData.currentCustomer != null) {

            //---------------------------------------------------
            // CUSTOMER ID
            //---------------------------------------------------

            VBox idRow =
                    createDetailRow(
                            "Customer ID (Non-editable)",
                            String.valueOf(
                                    AppData.currentCustomer
                                            .getPersonId()
                            ),
                            false,
                            null
                    );

            //---------------------------------------------------
            // USERNAME
            //---------------------------------------------------

            TextField usernameField =
                    new TextField(
                            AppData.currentCustomer.getName()
                    );

            //---------------------------------------------------
            // FULL NAME
            //---------------------------------------------------

            TextField fullNameField =
                    new TextField(
                            AppData.currentCustomer.getName()
                    );

            //---------------------------------------------------
            // PHONE
            //---------------------------------------------------

            TextField phoneField =
                    new TextField(
                            AppData.currentCustomer
                                    .getPhoneNumber()
                    );

            //---------------------------------------------------
            // ADDRESS
            //---------------------------------------------------

            String currentAddress = "";

            if (AppData.currentCustomer.getAddress() != null) {

                currentAddress =
                        AppData.currentCustomer.getAddress();

            }

            TextField addressField =
                    new TextField(currentAddress);

            addressField.setPromptText(
                    "Enter your delivery address"
            );

            //---------------------------------------------------
            // EDITABLE ROWS
            //---------------------------------------------------

            VBox usernameRow =
                    createEditableRow(
                            "Username",
                            usernameField
                    );

            VBox fullNameRow =
                    createEditableRow(
                            "Full Name",
                            fullNameField
                    );

            VBox phoneRow =
                    createEditableRow(
                            "Phone Number",
                            phoneField
                    );

            VBox addressRow =
                    createEditableRow(
                            "Delivery Address",
                            addressField
                    );

            //---------------------------------------------------
            // ORDER HISTORY
            //---------------------------------------------------

            Label orderHistoryTitle =
                    new Label(
                            "ORDER SUMMARY & HISTORY"
                    );

            orderHistoryTitle.setStyle(
                    "-fx-text-fill: #78716C;" +
                    "-fx-font-size: 11;" +
                    "-fx-font-weight: bold;"
            );

            VBox historyList =
                    new VBox(8);

            if (AppData.currentCustomer
                    .getOrderHistory()
                    .isEmpty()) {

                Label noOrders =
                        new Label(
                                "No past orders found."
                        );

                noOrders.setStyle(
                        "-fx-text-fill: #6B7280;" +
                        "-fx-font-size: 13;"
                );

                historyList
                        .getChildren()
                        .add(noOrders);

            } else {

                Label summaryCount =
                        new Label(
                                "Total Orders Placed: " +
                                AppData.currentCustomer
                                        .getOrderHistory()
                                        .size()
                        );

                summaryCount.setStyle(
                        "-fx-text-fill: #1A1A2E;" +
                        "-fx-font-size: 14;" +
                        "-fx-font-weight: bold;"
                );

                historyList
                        .getChildren()
                        .add(summaryCount);
            }

            VBox historyRow =
                    new VBox(
                            6,
                            orderHistoryTitle,
                            historyList
                    );

            historyRow.setStyle(
                    "-fx-background-color: #F3E7D8;" +
                    "-fx-background-radius: 10;" +
                    "-fx-padding: 12 16;" +
                    "-fx-border-color: #EAD9C0;" +
                    "-fx-border-radius: 10;"
            );

            //---------------------------------------------------
            // SAVE PROFILE BUTTON
            //---------------------------------------------------

            Button saveButton =
                    new Button(
                            "Save Changes"
                    );

            saveButton.setMaxWidth(
                    Double.MAX_VALUE
            );

            saveButton.setStyle(
                    "-fx-background-color: #FF6B35;" +
                    "-fx-text-fill: white;" +
                    "-fx-font-size: 14;" +
                    "-fx-font-weight: bold;" +
                    "-fx-background-radius: 10;" +
                    "-fx-padding: 10 20;" +
                    "-fx-cursor: hand;"
            );

            saveButton.setOnAction(e -> {

                String username =
                        usernameField.getText().trim();

                String fullName =
                        fullNameField.getText().trim();

                String phone =
                        phoneField.getText().trim();

                String address =
                        addressField.getText().trim();

                if (username.isEmpty() ||
                        fullName.isEmpty() ||
                        phone.isEmpty()) {

                    showAlert(
                            Alert.AlertType.WARNING,
                            "Missing Information",
                            "Username, full name, and phone number cannot be empty."
                    );

                    return;
                }

                AppData.currentCustomer
                        .setName(username);

                AppData.currentCustomer
                        .setFullName(fullName);

                AppData.currentCustomer
                        .setPhoneNumber(phone);

                AppData.currentCustomer
                        .setAddress(address);

                AppData.system.saveAll();

                showAlert(
                        Alert.AlertType.INFORMATION,
                        "Success",
                        "Profile changes saved successfully!"
                );
            });

            //---------------------------------------------------
            // CHANGE PASSWORD SECTION
            //---------------------------------------------------

            Label passwordTitle =
                    new Label(
                            "CHANGE PASSWORD"
                    );

            passwordTitle.setStyle(
                    "-fx-text-fill: #78716C;" +
                    "-fx-font-size: 11;" +
                    "-fx-font-weight: bold;"
            );

            //---------------------------------------------------
            // CURRENT PASSWORD
            //---------------------------------------------------

            PasswordField currentPasswordField =
                    new PasswordField();

            currentPasswordField.setPromptText(
                    "Enter current password"
            );

            //---------------------------------------------------
            // NEW PASSWORD
            //---------------------------------------------------

            PasswordField newPasswordField =
                    new PasswordField();

            newPasswordField.setPromptText(
                    "Enter new password"
            );

            //---------------------------------------------------
            // CONFIRM PASSWORD
            //---------------------------------------------------

            PasswordField confirmPasswordField =
                    new PasswordField();

            confirmPasswordField.setPromptText(
                    "Confirm new password"
            );

            //---------------------------------------------------
            // PASSWORD ROWS
            //---------------------------------------------------

            VBox currentPasswordRow =
                    createPasswordRow(
                            "Current Password",
                            currentPasswordField
                    );

            VBox newPasswordRow =
                    createPasswordRow(
                            "New Password",
                            newPasswordField
                    );

            VBox confirmPasswordRow =
                    createPasswordRow(
                            "Confirm New Password",
                            confirmPasswordField
                    );

            //---------------------------------------------------
            // CHANGE PASSWORD BUTTON
            //---------------------------------------------------

            Button changePasswordButton =
                    new Button(
                            "Change Password"
                    );

            changePasswordButton.setMaxWidth(
                    Double.MAX_VALUE
            );

            changePasswordButton.setStyle(
                    "-fx-background-color: #FF6B35;" +
                    "-fx-text-fill: white;" +
                    "-fx-font-size: 14;" +
                    "-fx-font-weight: bold;" +
                    "-fx-background-radius: 10;" +
                    "-fx-padding: 10 20;" +
                    "-fx-cursor: hand;"
            );

            //---------------------------------------------------
            // CHANGE PASSWORD LOGIC
            //---------------------------------------------------

            changePasswordButton.setOnAction(e -> {

                String currentPassword =
                        currentPasswordField.getText();

                String newPassword =
                        newPasswordField.getText();

                String confirmPassword =
                        confirmPasswordField.getText();

                //------------------------------------------------
                // EMPTY CHECK
                //------------------------------------------------

                if (currentPassword.isEmpty() ||
                        newPassword.isEmpty() ||
                        confirmPassword.isEmpty()) {

                    showAlert(
                            Alert.AlertType.WARNING,
                            "Missing Information",
                            "Please fill in all password fields."
                    );

                    return;
                }

                //------------------------------------------------
                // CURRENT PASSWORD CHECK
                //------------------------------------------------

                if (!currentPassword.equals(
                        AppData.currentCustomer
                                .getPassword())) {

                    showAlert(
                            Alert.AlertType.ERROR,
                            "Incorrect Password",
                            "Your current password is incorrect."
                    );

                    return;
                }

                //------------------------------------------------
                // NEW PASSWORD MATCH
                //------------------------------------------------

                if (!newPassword.equals(
                        confirmPassword)) {

                    showAlert(
                            Alert.AlertType.ERROR,
                            "Password Mismatch",
                            "The new passwords do not match."
                    );

                    return;
                }

                //------------------------------------------------
                // SAME PASSWORD CHECK
                //------------------------------------------------

                if (newPassword.equals(
                        currentPassword)) {

                    showAlert(
                            Alert.AlertType.WARNING,
                            "Invalid Password",
                            "Your new password must be different " +
                            "from your current password."
                    );

                    return;
                }

                //------------------------------------------------
                // UPDATE PASSWORD
                //------------------------------------------------

                AppData.currentCustomer
                        .setPassword(newPassword);

                //------------------------------------------------
                // SAVE TO FILE
                //------------------------------------------------

                AppData.system.saveAll();

                //------------------------------------------------
                // CLEAR FIELDS
                //------------------------------------------------

                currentPasswordField.clear();
                newPasswordField.clear();
                confirmPasswordField.clear();

                //------------------------------------------------
                // SUCCESS MESSAGE
                //------------------------------------------------

                showAlert(
                        Alert.AlertType.INFORMATION,
                        "Password Changed",
                        "Your password has been changed successfully."
                );
            });

            //---------------------------------------------------
            // PASSWORD SECTION
            //---------------------------------------------------

            VBox passwordSection =
                    new VBox(
                            10,
                            passwordTitle,
                            currentPasswordRow,
                            newPasswordRow,
                            confirmPasswordRow,
                            changePasswordButton
                    );

            passwordSection.setStyle(
                    "-fx-background-color: #F3E7D8;" +
                    "-fx-background-radius: 10;" +
                    "-fx-padding: 15 16;" +
                    "-fx-border-color: #EAD9C0;" +
                    "-fx-border-radius: 10;"
            );

            //---------------------------------------------------
            // ADD EVERYTHING TO DETAILS
            //---------------------------------------------------

            detailsBox.getChildren().addAll(
                    idRow,
                    usernameRow,
                    fullNameRow,
                    phoneRow,
                    addressRow,
                    historyRow,
                    saveButton,
                    passwordSection
            );

        } else {

            //---------------------------------------------------
            // NO CUSTOMER
            //---------------------------------------------------

            Label noUserLabel =
                    new Label(
                            "No customer currently logged in."
                    );

            noUserLabel.setStyle(
                    "-fx-text-fill: #EF4444;" +
                    "-fx-font-size: 14;" +
                    "-fx-font-weight: bold;"
            );

            detailsBox
                    .getChildren()
                    .add(noUserLabel);
        }

        //-------------------------------------------------------
        // CARD
        //-------------------------------------------------------

        VBox card =
                new VBox(
                        25
                );

        card.setAlignment(
                Pos.TOP_LEFT
        );

        card.setPadding(
                new Insets(
                        35,
                        40,
                        35,
                        40
                )
        );

        card.setMaxWidth(520);

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

        Separator separator =
                new Separator();

        separator.setStyle(
                "-fx-background-color: #EAD9C0;"
        );

        card.getChildren().addAll(
                titleBox,
                separator,
                detailsBox
        );

        //-------------------------------------------------------
        // CENTER WRAPPER
        //-------------------------------------------------------

        VBox centerWrapper =
                new VBox(card);

        centerWrapper.setAlignment(
                Pos.CENTER
        );

        centerWrapper.setPadding(
                new Insets(40)
        );

        //-------------------------------------------------------
        // SCROLL PANE
        //-------------------------------------------------------

        ScrollPane scrollPane =
                new ScrollPane(
                        centerWrapper
                );

        scrollPane.setFitToWidth(true);

        scrollPane.setStyle(
                "-fx-background: #FDF1E0;" +
                "-fx-background-color: transparent;"
        );

        root.setCenter(
                scrollPane
        );

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
                "Urban Bites - Customer Profile"
        );

        stage.setScene(scene);

        stage.show();
    }

    // ===========================================================
    // DETAIL ROW
    // ===========================================================

    private static VBox createDetailRow(
            String labelText,
            String valueText,
            boolean editable,
            Control control) {

        Label lbl =
                new Label(
                        labelText.toUpperCase()
                );

        lbl.setStyle(
                "-fx-text-fill: #78716C;" +
                "-fx-font-size: 11;" +
                "-fx-font-weight: bold;"
        );

        VBox row =
                new VBox(4);

        if (!editable) {

            Label val =
                    new Label(valueText);

            val.setStyle(
                    "-fx-text-fill: #1A1A2E;" +
                    "-fx-font-size: 15;" +
                    "-fx-font-weight: bold;"
            );

            row.getChildren().addAll(
                    lbl,
                    val
            );
        }

        row.setStyle(
                "-fx-background-color: #F3E7D8;" +
                "-fx-background-radius: 10;" +
                "-fx-padding: 10 16;" +
                "-fx-border-color: #EAD9C0;" +
                "-fx-border-radius: 10;"
        );

        return row;
    }

    // ===========================================================
    // EDITABLE ROW
    // ===========================================================

    private static VBox createEditableRow(
            String labelText,
            TextField textField) {

        Label lbl =
                new Label(
                        labelText.toUpperCase()
                );

        lbl.setStyle(
                "-fx-text-fill: #78716C;" +
                "-fx-font-size: 11;" +
                "-fx-font-weight: bold;"
        );

        textField.setStyle(
                "-fx-background-color: #FFFFFF;" +
                "-fx-text-fill: #1A1A2E;" +
                "-fx-font-size: 14;" +
                "-fx-border-color: #EAD9C0;" +
                "-fx-border-radius: 6;" +
                "-fx-background-radius: 6;" +
                "-fx-padding: 6;"
        );

        VBox row =
                new VBox(
                        4,
                        lbl,
                        textField
                );

        row.setStyle(
                "-fx-background-color: #F3E7D8;" +
                "-fx-background-radius: 10;" +
                "-fx-padding: 10 16;" +
                "-fx-border-color: #EAD9C0;" +
                "-fx-border-radius: 10;"
        );

        return row;
    }

    // ===========================================================
    // PASSWORD ROW
    // ===========================================================

    private static VBox createPasswordRow(
            String labelText,
            PasswordField passwordField) {

        Label lbl =
                new Label(
                        labelText.toUpperCase()
                );

        lbl.setStyle(
                "-fx-text-fill: #78716C;" +
                "-fx-font-size: 11;" +
                "-fx-font-weight: bold;"
        );

        passwordField.setStyle(
                "-fx-background-color: #FFFFFF;" +
                "-fx-text-fill: #1A1A2E;" +
                "-fx-font-size: 14;" +
                "-fx-border-color: #EAD9C0;" +
                "-fx-border-radius: 6;" +
                "-fx-background-radius: 6;" +
                "-fx-padding: 6;"
        );

        VBox row =
                new VBox(
                        4,
                        lbl,
                        passwordField
                );

        row.setStyle(
                "-fx-background-color: #F3E7D8;" +
                "-fx-background-radius: 10;" +
                "-fx-padding: 10 16;" +
                "-fx-border-color: #EAD9C0;" +
                "-fx-border-radius: 10;"
        );

        return row;
    }

    // ===========================================================
    // SECONDARY BUTTON
    // ===========================================================

    private static Button createSecondaryButton(
            String text) {

        Button button =
                new Button(text);

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

    // ===========================================================
    // ALERT
    // ===========================================================

    private static void showAlert(
            Alert.AlertType type,
            String title,
            String message) {

        Alert alert =
                new Alert(type);

        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);

        alert.showAndWait();
    }
}