package org.example;
/**
 * This class implements a simple image repository. It is like a custom collection of image records.
 */

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ImageRepo {
    private List<Image> images;

    public ImageRepo() {
        this.images = new ArrayList<Image>();
    }

    public void addImage(Image image) {
        this.images.add(image);
    }

    /**
     * This function displays on the screen a specific image from a specific location
     * @param image
     */

    public void displayImage(Image image) {
        File imageFile = new File(image.locationPath());
        if (imageFile.exists() && Desktop.isDesktopSupported()) {
            try {
                Desktop.getDesktop().open(imageFile);
            } catch (IOException e) {
                System.out.println("Error opening file");
            }
        } else {
            System.out.println("Image not found");
        }
    }

    public void printImages() {
        for (Image image : this.images) {
            System.out.println(image);
        }
    }

    public void removeImage(String name) {
        images.removeIf(img -> img.name().equals(name));
    }

    public List<Image> getRepo() {
        return images;
    }
}
