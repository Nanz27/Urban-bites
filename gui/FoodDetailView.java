package gui;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

import model.FoodItem;

public class FoodDetailView {

    public static void show(Stage stage, FoodItem item) {

        //-------------------------------------------------------
        // ROOT
        //-------------------------------------------------------

        VBox root = new VBox(20);

        root.setAlignment(Pos.CENTER);
        root.setPadding(new Insets(40));

        root.setStyle(
                "-fx-background-color: linear-gradient(to bottom right, " +
                "#FDF1E0 0%, #FCE7D0 45%, #FBDFC0 100%);"
        );

        //-------------------------------------------------------
        // BACK BUTTON
        //-------------------------------------------------------

        Button backButton =
                new Button("← Back to Menu");

        backButton.setStyle(
                "-fx-background-color: transparent;" +
                "-fx-text-fill: #6B6259;" +
                "-fx-font-size: 14;" +
                "-fx-cursor: hand;"
        );

        backButton.setOnAction(e ->
                PublicMenu.show(stage)
        );

        //-------------------------------------------------------
        // ITEM IMAGE
        //-------------------------------------------------------

        Image image =
                ImageUtil.loadImage(
                        item.getImagePath()
                );

        ImageView imageView =
                new ImageView();

        if (image != null) {

            imageView.setImage(image);

            imageView.setFitWidth(220);
            imageView.setFitHeight(220);

            imageView.setPreserveRatio(false);
        }

        Rectangle clip =
                new Rectangle(
                        220,
                        220
                );

        clip.setArcWidth(28);
        clip.setArcHeight(28);

        imageView.setClip(clip);

        StackPane imagePane =
                new StackPane(imageView);

        imagePane.setPrefSize(
                220,
                220
        );

        imagePane.setStyle(
                "-fx-background-color:#FBEEDD;" +
                "-fx-background-radius:28;"
        );

        //-------------------------------------------------------
        // ITEM NAME
        //-------------------------------------------------------

        Label nameLabel =
                new Label(
                        item.getName()
                );

        nameLabel.setStyle(
                "-fx-font-size:26;" +
                "-fx-font-weight:bold;" +
                "-fx-text-fill:#211A14;"
        );

        //-------------------------------------------------------
        // CATEGORY
        //-------------------------------------------------------

        Label categoryLabel =
                new Label(
                        item.getCategory()
                                .toString()
                                .replace("_", " ")
                );

        categoryLabel.setStyle(
                "-fx-background-color:#EEE5D5;" +
                "-fx-text-fill:#6E5633;" +
                "-fx-font-size:11;" +
                "-fx-font-weight:bold;" +
                "-fx-background-radius:12;" +
                "-fx-padding:5 10;"
        );

        //-------------------------------------------------------
        // PRICE
        //-------------------------------------------------------

        Label priceLabel =
                new Label(
                        String.format(
                                "৳ %.2f",
                                item.getPrice()
                        )
                );

        priceLabel.setStyle(
                "-fx-font-size:20;" +
                "-fx-font-weight:bold;" +
                "-fx-text-fill:#E85A24;"
        );

        //-------------------------------------------------------
        // DESCRIPTION
        //-------------------------------------------------------

        Label descLabel =
                new Label(
                        item.getDescription()
                );

        descLabel.setWrapText(true);

        descLabel.setMaxWidth(400);

        descLabel.setStyle(
                "-fx-font-size:14;" +
                "-fx-text-fill:#6B6259;"
        );

        //-------------------------------------------------------
        // QUANTITY
        //-------------------------------------------------------

        final int[] quantity = {1};

        Label qtyLabel =
                new Label(
                        "Quantity: 1"
                );

        qtyLabel.setStyle(
                "-fx-font-size:16;" +
                "-fx-font-weight:bold;" +
                "-fx-text-fill:#211A14;"
        );

        Button minusBtn =
                new Button("-");

        Button plusBtn =
                new Button("+");

        String qtyBtnStyle =
                "-fx-background-color:#F3E7D8;" +
                "-fx-text-fill:#211A14;" +
                "-fx-font-weight:bold;" +
                "-fx-font-size:14;" +
                "-fx-cursor:hand;" +
                "-fx-background-radius:8;";

        minusBtn.setStyle(qtyBtnStyle);
        plusBtn.setStyle(qtyBtnStyle);

        //-------------------------------------------------------
        // MINUS
        //-------------------------------------------------------

        minusBtn.setOnAction(e -> {

            if (quantity[0] > 1) {

                quantity[0]--;

                qtyLabel.setText(
                        "Quantity: " + quantity[0]
                );
            }
        });

        //-------------------------------------------------------
        // PLUS
        //-------------------------------------------------------

        plusBtn.setOnAction(e -> {

            quantity[0]++;

            qtyLabel.setText(
                    "Quantity: " + quantity[0]
            );
        });

        HBox qtyBox =
                new HBox(
                        15,
                        minusBtn,
                        qtyLabel,
                        plusBtn
                );

        qtyBox.setAlignment(
                Pos.CENTER
        );

        //-------------------------------------------------------
        // ADD TO CART BUTTON
        //-------------------------------------------------------

        Button addToCartButton =
                new Button("Add to Cart");

        addToCartButton.setStyle(
                "-fx-background-color:#FF6B35;" +
                "-fx-text-fill:white;" +
                "-fx-font-size:15;" +
                "-fx-font-weight:bold;" +
                "-fx-background-radius:25;" +
                "-fx-padding:10 30;" +
                "-fx-cursor:hand;"
        );

        //-------------------------------------------------------
        // ADD TO CART ACTION
        //-------------------------------------------------------

        addToCartButton.setOnAction(e -> {

            //---------------------------------------------------
            // NOT LOGGED IN
            //---------------------------------------------------

            if (AppData.currentCustomer == null) {

                Alert alert =
                        new Alert(
                                Alert.AlertType.CONFIRMATION
                        );

                alert.setTitle(
                        "Login Required"
                );

                alert.setHeaderText(
                        "Please login to continue"
                );

                alert.setContentText(
                        "You need to login before adding items to your cart."
                );

                ButtonType loginButton =
                        new ButtonType(
                                "Login"
                        );

                ButtonType cancelButton =
                        new ButtonType(
                                "Cancel",
                                ButtonBar.ButtonData.CANCEL_CLOSE
                        );

                alert.getButtonTypes().setAll(
                        loginButton,
                        cancelButton
                );

                alert.showAndWait().ifPresent(
                        response -> {

                            if (response == loginButton) {

                                CustomerLogin.show(stage);

                            }

                        }
                );

                return;
            }

            //---------------------------------------------------
            // LOGGED IN
            //---------------------------------------------------

            AppData.cart.addItem(
                    item,
                    quantity[0]
            );

            //---------------------------------------------------
            // SUCCESS MESSAGE
            //---------------------------------------------------

            Alert alert =
                    new Alert(
                            Alert.AlertType.INFORMATION
                    );

            alert.setTitle(
                    "Added to Cart"
            );

            alert.setHeaderText(null);

            alert.setContentText(
                    quantity[0] +
                    " × " +
                    item.getName() +
                    " added to your cart."
            );

            alert.showAndWait();

            //---------------------------------------------------
            // GO TO CART
            //---------------------------------------------------

            CartView.show(stage);
        });

        //-------------------------------------------------------
        // CARD
        //-------------------------------------------------------

        VBox cardContainer =
                new VBox(
                        15,
                        imagePane,
                        nameLabel,
                        categoryLabel,
                        priceLabel,
                        descLabel,
                        qtyBox,
                        addToCartButton
                );

        cardContainer.setAlignment(
                Pos.CENTER
        );

        cardContainer.setPadding(
                new Insets(30)
        );

        cardContainer.setMaxWidth(
                480
        );

        cardContainer.setStyle(
                "-fx-background-color:#FFFFFF;" +
                "-fx-background-radius:26;" +
                "-fx-effect:dropshadow(" +
                "gaussian," +
                "rgba(120,80,40,0.18)," +
                "25,0,0,10" +
                ");"
        );

        //-------------------------------------------------------
        // ADD EVERYTHING
        //-------------------------------------------------------

        root.getChildren().addAll(
                backButton,
                cardContainer
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
                "Urban Bites - Food Details"
        );

        stage.setScene(scene);
        stage.show();
    }
}