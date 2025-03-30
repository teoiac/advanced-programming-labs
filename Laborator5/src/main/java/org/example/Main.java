package org.example;

public class Main {
    public static void main(String[] args) {
        ImageRepo imageRepo = new ImageRepo();
        Shell shell = new Shell(imageRepo);
        shell.startShell();
    }
}
