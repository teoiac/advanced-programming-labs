package org.example.Commands;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.Command;
import org.example.Image;
import org.example.ImageRepo;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class LoadCommand implements Command {
    private final ImageRepo imageRepo;
    private static final ObjectMapper objectMapper = new ObjectMapper();

    public LoadCommand(ImageRepo repo) {
        this.imageRepo = repo;
    }

    @Override
    public void execute(String[] args) {
        try {
            File file = new File("images.json");
            if (file.exists()) {
                List<Image> images = objectMapper.readValue(file, objectMapper.getTypeFactory().constructCollectionType(List.class, Image.class));
                for (Image image : images) {
                    imageRepo.addImage(image);
                }
                System.out.println("Images loaded.");
            } else {
                System.out.println("No images found.");
            }
        } catch (IOException e) {
            System.out.println("Error loading images: " + e.getMessage());
        }
    }
}
