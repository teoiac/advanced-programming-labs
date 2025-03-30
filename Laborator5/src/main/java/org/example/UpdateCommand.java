package org.example;

import java.util.List;

/**
 * UpdateCommand can be used to modify the attributes of an existing image
 * Command format : update image_name field new_value
 * Field can be either name, tags or path.
 */
public class UpdateCommand implements Command {
    private final ImageRepo imageRepo;

    public UpdateCommand(ImageRepo repo) {
        this.imageRepo = repo;
    }

    @Override
    public void execute(String[] args) {
        try {
            if (args.length < 3) {
                throw new IllegalArgumentException("Invalid number of arguments");
            }
            String imageName = args[0];
            String field = args[1];
            String newValue = args[2];

            for (int i = 0; i < imageRepo.getRepo().size(); i++) {
                Image img = imageRepo.getRepo().get(i);
                if (img.name().equals(imageName)) {
                    Image updatedImage;
                    if (field.equals("name")) {
                        updatedImage = new Image(newValue, img.creationDate(), img.tags(), img.locationPath());
                    } else if (field.equals("tags")) {
                        updatedImage = new Image(img.name(), img.creationDate(), List.of(newValue.split(";")), img.locationPath());
                    } else if (field.equals("path")) {
                        updatedImage = new Image(img.name(), img.creationDate(), img.tags(), newValue);
                    } else {
                        System.out.println("Invalid field. Use: name, tags, or path.");
                        updatedImage = img;
                    }
                    imageRepo.getRepo().set(i, updatedImage);
                    System.out.println("Image updated successfully.");
                    return;
                }
            }
        } catch (IllegalArgumentException e) {
            System.out.println("Invalid field. Use: name, tags, or path.");
        }
        catch (Exception e) {
            System.out.println("Image not found.");
        }

    }
}
