package gui;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import javafx.scene.image.Image;

public class ImageUtil {

    // Folder where uploaded food images are stored, e.g. ./images/pizza.jpg
    public static final String IMAGE_FOLDER = "images";

    private static final String DEFAULT_IMAGE = "default.jpg";

    /**
     * Copies a file chosen via FileChooser into the images folder, so it
     * can be found again later just by file name.
     *
     * @return the file name to store on the FoodItem (e.g. "pizza.jpg"),
     *         or DEFAULT_IMAGE if the copy failed.
     */
    public static String saveImage(File sourceFile) {

        if (sourceFile == null) {
            return DEFAULT_IMAGE;
        }

        try {

            Path folder = Paths.get(IMAGE_FOLDER);

            if (!Files.exists(folder)) {
                Files.createDirectories(folder);
            }

            Path destination = folder.resolve(sourceFile.getName());

            Files.copy(
                    sourceFile.toPath(),
                    destination,
                    StandardCopyOption.REPLACE_EXISTING
            );

            return sourceFile.getName();

        } catch (IOException e) {

            System.err.println(
                    "Could not save image "
                    + sourceFile.getName()
                    + ": "
                    + e.getMessage()
            );

            return DEFAULT_IMAGE;

        }
    }

    /**
     * Loads a food image by file name from the images folder.
     * Falls back to default.jpg, and returns null (never throws) if
     * neither the requested image nor the default can be found, so
     * callers can just check for null and skip setting an image.
     */
    public static Image loadImage(String imageName) {

        Image image = loadFromFolder(imageName);

        if (image != null) {
            return image;
        }

        // fall back to default.jpg
        return loadFromFolder(DEFAULT_IMAGE);
    }

    private static Image loadFromFolder(String imageName) {

        if (imageName == null || imageName.isBlank()) {
            return null;
        }

        try {

            File file = Paths.get(IMAGE_FOLDER, imageName).toFile();

            if (!file.exists()) {
                return null;
            }

            return new Image(file.toURI().toString());

        } catch (Exception e) {

            return null;

        }
    }
}