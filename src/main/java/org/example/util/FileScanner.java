package org.example.util;

import jdk.swing.interop.SwingInterOpUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.URISyntaxException;
import java.util.Scanner;

public class FileScanner {

    static Scanner scanner;

    public static void readFile() {
        try {
            if (scanner == null) {


                String path = System.getProperty("user.dir") + "\\src\\main\\java\\org\\example\\util\\pascalcode.txt";
                File pascalCode = new File(path);
                System.out.println(pascalCode.getPath());
                scanner = new Scanner(pascalCode);
            }


        } catch (FileNotFoundException e) {
            System.out.println("file not found ");
            e.printStackTrace();
        }
    }

    public static String nextLine() {
        if (scanner == null) {
            readFile();
        }
        String data = "end of file";
        if (scanner.hasNextLine()) {
            data = scanner.nextLine();
        }
        return data;
    }
}
