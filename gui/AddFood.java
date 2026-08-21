package gui;

import exception.DuplicateFoodException;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import java.io.File;
import javafx.stage.FileChooser;
import model.BeverageItem;
import model.FoodItem;
import model.NonVegItem;
import model.VegItem;

public class AddFood {

	public static void show(Stage stage) {

	    Label title = new Label("Add Food Item");
	    title.setFont(new Font(24));

	    TextField nameField = new TextField();
	    nameField.setPromptText("Food Name");

	    TextField priceField = new TextField();
	    priceField.setPromptText("Price");

	    TextArea descriptionField = new TextArea();
	    descriptionField.setPromptText("Food Description");
	    descriptionField.setPrefRowCount(3);

	    ComboBox<String> categoryBox = new ComboBox<>();
	    categoryBox.getItems().addAll("Veg", "Non Veg", "Beverage");
	    categoryBox.setValue("Veg");

	    Button chooseImage = new Button("Choose Image");

	    Label imageLabel = new Label("No image selected");

	    final String[] imageName = { "default.jpg" };

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

	        if(file != null){

	            String savedName = ImageUtil.saveImage(file);

	            imageName[0] = savedName;

	            imageLabel.setText(savedName);

	        }

	    });

	    Button addButton = new Button("Add Food");

	    Button backButton = new Button("Back");

	    addButton.setOnAction(e -> {

	        try{

	            int id = AppData.system.nextFoodItemId();

	            String name = nameField.getText();

	            double price =
	                    Double.parseDouble(priceField.getText());

	            if(price <= 0){

	                new Alert(
	                        Alert.AlertType.ERROR,
	                        "Price must be greater than zero."
	                ).showAndWait();

	                return;

	            }

	            String description =
	                    descriptionField.getText();

	            FoodItem item;

	            switch(categoryBox.getValue()){

	                case "Veg":

	                    item = new VegItem(
	                            id,
	                            name,
	                            price,
	                            description,
	                            imageName[0]
	                    );

	                    break;

	                case "Non Veg":

	                    item = new NonVegItem(
	                            id,
	                            name,
	                            price,
	                            description,
	                            imageName[0]
	                    );

	                    break;

	                default:

	                    item = new BeverageItem(
	                            id,
	                            name,
	                            price,
	                            description,
	                            imageName[0]
	                    );

	            }

	            AppData.system.addFoodItem(item);

	            AppData.system.saveAll();

	            Alert alert =
	                    new Alert(Alert.AlertType.INFORMATION);

	            alert.setHeaderText(null);
	            alert.setContentText("Food Added Successfully!");

	            alert.showAndWait();

	            FoodMenu.show(stage);

	        }

	        catch(NumberFormatException ex){

	            new Alert(
	                    Alert.AlertType.ERROR,
	                    "Enter valid numbers."
	            ).showAndWait();

	        }

	        catch(DuplicateFoodException ex){

	            new Alert(
	                    Alert.AlertType.ERROR,
	                    ex.getMessage()
	            ).showAndWait();

	        }

	    });

	    backButton.setOnAction(e ->
	            FoodMenu.show(stage));

	    GridPane form = new GridPane();

	    form.setVgap(15);
	    form.setHgap(15);

	    form.add(new Label("Food Name"),0,0);
	    form.add(nameField,1,0);

	    form.add(new Label("Price"),0,1);
	    form.add(priceField,1,1);

	    form.add(new Label("Category"),0,2);
	    form.add(categoryBox,1,2);

	    form.add(new Label("Description"),0,3);
	    form.add(descriptionField,1,3);

	    form.add(chooseImage,0,4);
	    form.add(imageLabel,1,4);

	    VBox root = new VBox(20);

	    root.setPadding(new Insets(30));

	    root.setAlignment(Pos.CENTER);

	    root.getChildren().addAll(
	            title,
	            form,
	            addButton,
	            backButton
	    );

	    Scene scene =
	            new Scene(root,650,600);
	    scene.getStylesheets().add(
	            Main.class.getResource("style.css").toExternalForm()
	    );
	    stage.setScene(scene);

	}
}