package gui;

import exception.FoodNotFoundException;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import java.io.File;
import model.BeverageItem;
import model.FoodCategory;
import model.FoodItem;
import model.NonVegItem;
import model.VegItem;

public class EditFood {

    public static void show(Stage stage, FoodItem item) {

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

        Button backButton = createSecondaryButton("← Back to Menu");
        backButton.setOnAction(e -> FoodMenu.show(stage));

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
        // Title & Description Header
        //-------------------------------------------------------
        Label title = new Label("Edit Food Item");
        title.setStyle(
                "-fx-font-size: 26;" +
                "-fx-font-weight: bold;" +
                "-fx-text-fill: #1A1A2E;"
        );

        Label subtitle = new Label("Update details, pricing, or media for this dish.");
        subtitle.setStyle(
                "-fx-font-size: 13;" +
                "-fx-text-fill: #78716C;"
        );

        VBox titleBox = new VBox(4, title, subtitle);
        titleBox.setAlignment(Pos.CENTER_LEFT);

        //-------------------------------------------------------
        // Form Controls Creation
        //-------------------------------------------------------
        TextField nameField = new TextField(item.getName());
        styleTextField(nameField);

        TextField priceField = new TextField(String.valueOf(item.getPrice()));
        styleTextField(priceField);

        TextArea descriptionField = new TextArea(item.getDescription());
        descriptionField.setPrefRowCount(3);
        descriptionField.setWrapText(true);
        descriptionField.setStyle(
                "-fx-control-inner-background: #F3E7D8;" +
                "-fx-text-fill: #1A1A2E;" +
                "-fx-prompt-text-fill: #6B7280;" +
                "-fx-font-size: 14;" +
                "-fx-background-radius: 8;" +
                "-fx-border-color: #EAD9C0;" +
                "-fx-border-radius: 8;"
        );

        final String[] imageName = { item.getImagePath() };

        Label imageLabel = new Label(
                item.getImagePath() == null || item.getImagePath().isBlank()
                        ? "No image selected"
                        : item.getImagePath()
        );
        imageLabel.setStyle("-fx-text-fill: #78716C; -fx-font-size: 13;");

        Button chooseImage = createSecondaryButton("📁 Choose Image");

        chooseImage.setOnAction(e -> {
            FileChooser chooser = new FileChooser();
            chooser.setTitle("Choose Food Image");
            chooser.getExtensionFilters().add(
                    new FileChooser.ExtensionFilter(
                            "Images",
                            "*.jpg",
                            "*.jpeg",
                            "*.png"
                    )
            );

            File file = chooser.showOpenDialog(stage);

            if (file != null) {
                String savedName = ImageUtil.saveImage(file);
                imageName[0] = savedName;
                imageLabel.setText(savedName);
            }
        });

        HBox imageBox = new HBox(15, chooseImage, imageLabel);
        imageBox.setAlignment(Pos.CENTER_LEFT);

        ComboBox<String> categoryBox = new ComboBox<>();
        categoryBox.getItems().addAll("Veg", "Non Veg", "Beverage");
        categoryBox.setMaxWidth(Double.MAX_VALUE);
        categoryBox.setStyle(
                "-fx-background-color: #F3E7D8;" +
                "-fx-text-fill: #1A1A2E;" +
                "-fx-font-size: 14;" +
                "-fx-background-radius: 8;" +
                "-fx-border-color: #EAD9C0;" +
                "-fx-border-radius: 8;"
        );

        if (item.getCategory() == FoodCategory.VEG) {
            categoryBox.setValue("Veg");
        } else if (item.getCategory() == FoodCategory.NON_VEG) {
            categoryBox.setValue("Non Veg");
        } else {
            categoryBox.setValue("Beverage");
        }

        //-------------------------------------------------------
        // Form Grid Layout Assembly
        //-------------------------------------------------------
        GridPane form = new GridPane();
        form.setHgap(20);
        form.setVgap(18);

        form.add(createFormLabel("Food Name"), 0, 0);
        form.add(nameField, 1, 0);

        form.add(createFormLabel("Price (৳)"), 0, 1);
        form.add(priceField, 1, 1);

        form.add(createFormLabel("Category"), 0, 2);
        form.add(categoryBox, 1, 2);

        form.add(createFormLabel("Description"), 0, 3);
        form.add(descriptionField, 1, 3);

        form.add(createFormLabel("Food Image"), 0, 4);
        form.add(imageBox, 1, 4);

        ColumnConstraints col1 = new ColumnConstraints();
        col1.setMinWidth(120);
        ColumnConstraints col2 = new ColumnConstraints();
        col2.setHgrow(Priority.ALWAYS);
        form.getColumnConstraints().addAll(col1, col2);

        //-------------------------------------------------------
        // Action Buttons Panel
        //-------------------------------------------------------
        Button saveButton = createPrimaryButton("💾 Save Changes");
        saveButton.setMaxWidth(Double.MAX_VALUE);

        saveButton.setOnAction(e -> {
            try {
                int id = item.getItemId();
                String name = nameField.getText().trim();
                
                if (name.isEmpty()) {
                    throw new Exception("Food name cannot be empty.");
                }

                double price = Double.parseDouble(priceField.getText().trim());

                if (price <= 0) {
                    new Alert(
                            Alert.AlertType.ERROR,
                            "Price must be greater than zero."
                    ).showAndWait();
                    return;
                }

                String description = descriptionField.getText().trim();
                String image = imageName[0];
                FoodItem updated;

                switch (categoryBox.getValue()) {
                    case "Veg":
                        updated = new VegItem(id, name, price, description, image);
                        break;
                    case "Non Veg":
                        updated = new NonVegItem(id, name, price, description, image);
                        break;
                    default:
                        updated = new BeverageItem(id, name, price, description, image);
                }

                AppData.system.updateFoodItem(id, updated);
                AppData.system.saveAll();

                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setHeaderText(null);
                alert.setContentText("Food updated successfully!");
                alert.showAndWait();

                FoodMenu.show(stage);

            } catch (NumberFormatException ex) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Enter a valid numeric price.");
                alert.showAndWait();
            } catch (FoodNotFoundException ex) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText(ex.getMessage());
                alert.showAndWait();
            } catch (Exception ex) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText(ex.getMessage());
                alert.showAndWait();
            }
        });

        //-------------------------------------------------------
        // Card Container Structure
        //-------------------------------------------------------
        VBox card = new VBox(25);
        card.setAlignment(Pos.TOP_LEFT);
        card.setPadding(new Insets(35, 40, 35, 40));
        card.setMaxWidth(680);
        card.setStyle(
                "-fx-background-color: #FFFFFF;" +
                "-fx-background-radius: 20;" +
                "-fx-border-color: #EAD9C0;" +
                "-fx-border-radius: 20;" +
                "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.4), 20, 0, 0, 6);"
        );

        card.getChildren().addAll(
                titleBox,
                new Separator() {{ setStyle("-fx-background-color: #EAD9C0;"); }},
                form,
                saveButton
        );

        VBox centerWrapper = new VBox(card);
        centerWrapper.setAlignment(Pos.CENTER);
        centerWrapper.setPadding(new Insets(40, 50, 40, 50));

        ScrollPane scrollPane = new ScrollPane(centerWrapper);
        scrollPane.setFitToWidth(true);
        scrollPane.setStyle(
                "-fx-background: #FDF1E0;" +
                "-fx-background-color: transparent;"
        );

        root.setCenter(scrollPane);

        Scene scene = new Scene(root, 1280, 800);
        scene.getStylesheets().add(EditFood.class.getResource("style.css").toExternalForm());
        stage.setTitle("Urban Bites - Edit Food Item");
        stage.setScene(scene);
        stage.show();
    }

    //-------------------------------------------------------
    // Aesthetic Styling & Component Helpers
    //-------------------------------------------------------
    private static Label createFormLabel(String text) {
        Label label = new Label(text);
        label.setStyle("-fx-text-fill: #3F3A34; -fx-font-size: 13; -fx-font-weight: bold;");
        return label;
    }

    private static void styleTextField(TextField field) {
        field.setStyle(
                "-fx-background-color: #F3E7D8;" +
                "-fx-text-fill: #1A1A2E;" +
                "-fx-prompt-text-fill: #6B7280;" +
                "-fx-font-size: 14;" +
                "-fx-padding: 10 14;" +
                "-fx-background-radius: 8;" +
                "-fx-border-color: #EAD9C0;" +
                "-fx-border-radius: 8;"
        );
    }

    private static Button createPrimaryButton(String text) {
        Button button = new Button(text);
        button.setStyle(
                "-fx-background-color: #FF6B35;" +
                "-fx-text-fill: white;" +
                "-fx-font-size: 14;" +
                "-fx-font-weight: bold;" +
                "-fx-background-radius: 10;" +
                "-fx-padding: 12;" +
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