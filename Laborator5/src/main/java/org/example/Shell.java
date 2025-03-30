package org.example;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class Shell {
    private ImageRepo imageRepo;

    public Shell(ImageRepo imageRepo) {
        this.imageRepo = imageRepo;
    }

    public void startShell() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter command: ");

        while (true) {
            System.out.print("¯\\_(ツ)_/¯ ");
            String commandLine = scanner.nextLine().trim();
            String[] parts = commandLine.split(" ", 2);
            String command = parts[0].toLowerCase();
            String args = (parts.length > 1) ? parts[1] : "";

            switch (command) {
                case "add":
                    handleAdd(args);
                    break;
                case "remove":
                    handleRemove(args);
                    break;
                case "update":
                    handleUpdate(args);
                    break;
                case "list":
                    imageRepo.printImages();
                    break;
                case "open":
                    handleOpen(args);
                    break;
                case "save":
                    handleSave();
                    break;
                case "load":
                    handleLoad();
                    break;
                case "quit":
                    System.out.println("EXIT TERMINAL");
                    return;
                default:
                    System.out.println("Unknown command.");
            }
        }
    }

    // Handle adding an image
    private void handleAdd(String args) {
        String[] details = args.split(",");
        if (details.length < 4) {
            System.out.println("Usage: add name,creationDate,tag1;tag2;tag3,locationPath");
            return;
        }

        new AddCommand(imageRepo).execute(new String[]{
                details[0].trim(),               // name
                details[1].trim(),               // creationDate
                details[2].trim(),               // tags (semicolon-separated)
                details[3].trim()                // locationPath
        });

        System.out.println("Image added successfully.");
    }

    // Handle updating an image
    private void handleUpdate(String args) {
        String[] details = args.split(" ", 3);
        if (details.length < 3) {
            System.out.println("Usage: update <name> <field> <newValue>");
            return;
        }

        new UpdateCommand(imageRepo).execute(details);
    }

    // Handle removing an image
    private void handleRemove(String args) {
        String imageName = args.trim();

        // Check if the image exists in the repo
        boolean found = false;
        for (Image img : imageRepo.getRepo()) {
            if (img.name().equalsIgnoreCase(imageName)) {
                found = true;
                break;
            }
        }

        if (found) {
            new RemoveCommand(imageRepo).execute(new String[]{imageName});
            System.out.println("Image removed successfully.");
        } else {
            System.out.println("Image not found.");
        }
    }

    // Handle saving images to a JSON file
    private void handleSave() {
        new SaveCommand(imageRepo).execute(new String[]{});
    }

    // Handle loading images from a JSON file
    private void handleLoad() {
        new LoadCommand(imageRepo).execute(new String[]{});
    }

    // Handle opening an image
    private void handleOpen(String args) {
        for (Image img : imageRepo.getRepo()) {
            if (img.name().equalsIgnoreCase(args.trim())) {
                imageRepo.displayImage(img);
                return;
            }
        }
        System.out.println("Image not found.");
    }
}
