package org.example;

import org.example.util.FileScanner;

import java.util.ArrayList;

import org.example.StaticFiles;

public class Main {
    public static void main(String[] args) {
        ArrayList<Token> tokens = new ArrayList<Token>();
        String[] PASCALKEYWORDS = StaticFiles.PASCALKEYWORDS;
        FileScanner.readFile();
        while (FileScanner.scanner.hasNextLine()) {
            String[] words = FileScanner.nextLine().split(" ");
            for (String word : words) {

            }
        }


    }
}