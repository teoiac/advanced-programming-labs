package org.example.Commands;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.Command;
import org.example.ImageRepo;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class SaveCommand implements Command {
    private final ImageRepo imageRepo;
    private static final ObjectMapper objectMapper = new ObjectMapper();

    public SaveCommand(ImageRepo repo) {
        this.imageRepo = repo;
    }

    @Override
    public void execute(String[] args) {
        try {
            List<Image> images = imageRepo.getRepo();
            if (images.isEmpty()) {
                System.out.println("No images found.");
                return;
            }

            objectMapper.writeValue(new File("images.json"), images);
            System.out.println("Images saved successfully.");
        } catch (IOException e) {
            System.out.println("Error saving images: " + e.getMessage());
        }
    }
}
