package org.example;

import java.util.List;

public class AddCommand implements Command {
    private ImageRepo repo;

    public AddCommand(ImageRepo repo) {
        this.repo = repo;
    }

    @Override
    public void execute(String[] args) {
        try {
            if (args.length < 4) {
                throw new IllegalArgumentException("Correct format of command: add name creationDate tags locationPath");
            }
            String name = args[0];
            String creationDate = args[1];
            List<String> tags = List.of(args[2].split(";"));
            String locationPath = args[3];

            repo.addImage(new Image(name, creationDate, tags, locationPath));
            System.out.println("Image added successfully.");
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Unexpected error " + e.getMessage());
        }
    }

}
