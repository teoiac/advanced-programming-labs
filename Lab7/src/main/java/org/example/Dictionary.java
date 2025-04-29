package org.example;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;


public class Dictionary {
    Set<String> validWords;
    File file = new File("src/main/java/org/example/dictionary.txt");

    public Dictionary() {
        validWords = new HashSet<>();
        try (Scanner scanner = new Scanner(file)) {
            while (scanner.hasNextLine()) {
                validWords.add(scanner.nextLine().toLowerCase());
            }
        } catch (FileNotFoundException e) {
            System.err.println(" file not found.");
        }
    }


    public boolean isWord(String word) {
        if (validWords.contains(word.toLowerCase()))
            return true;
        return false;
    }
}
