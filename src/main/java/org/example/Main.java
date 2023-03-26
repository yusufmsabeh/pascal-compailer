package org.example;

import org.example.util.FileScanner;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        ArrayList<Token> tokens = new ArrayList<Token>();
        FileScanner.readFile();
        FileScanner.nextLine();
        FileScanner.nextLine();
        while (FileScanner.scanner.hasNextLine()){
            String [] words = FileScanner.nextLine().split(" ");
            for (int i =0;i<words.length;i++){

            }
        }


    }
}