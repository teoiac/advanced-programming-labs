package org.example.Images;
/**
 * This class implements a simple image repository. It is like a custom collection of image records.
 */

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ImageRepo {
    private List<org.example.Image> images;

    public ImageRepo() {
        this.images = new ArrayList<org.example.Image>();
    }

    public void addImage(org.example.Image image) {
        this.images.add(image);
    }

    /**
     * This function displays on the screen a specific image from a specific location
     * @param image
     */

    public void displayImage(org.example.Image image) {
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
        for (org.example.Image image : this.images) {
            System.out.println(image);
        }
    }

    public void removeImage(String name) {
        images.removeIf(img -> img.name().equals(name));
    }

    public List<org.example.Image> getRepo() {
        return images;
    }
}
