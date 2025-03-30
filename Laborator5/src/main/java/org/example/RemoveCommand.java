package org.example;

public class RemoveCommand implements Command {
    private final ImageRepo repo;

    public RemoveCommand(ImageRepo repo) {
        this.repo = repo;
    }

    @Override
    public void execute(String[] args) {
        try {
            if (args.length < 1) {
                throw new IllegalArgumentException("Invalid number of arguments");

            }
            repo.removeImage(args[0]);
            System.out.println("Image removed successfully.");
        }
        catch (IllegalArgumentException e) {
            System.out.println("Invalid argument " + e.getMessage());
        }
        catch (Exception e) {
            System.out.println("Unexpected error " + e.getMessage());
        }
    }
}
