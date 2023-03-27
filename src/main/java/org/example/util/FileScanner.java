package org.example.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.URL;
import java.security.CodeSource;
import java.security.ProtectionDomain;
import java.util.Scanner;


public class FileScanner {

    public static Scanner scanner;
    public static int lineNumber = 0;

    public static void readFile() {
        try {
            if (scanner == null) {
                // getting the file path
                Class<FileScanner> clazz = FileScanner.class;
                ProtectionDomain protectionDomain = clazz.getProtectionDomain();
                CodeSource codeSource = protectionDomain.getCodeSource();
                URL locaction = codeSource.getLocation();
                String path = locaction.getPath();
                int cutIndex = path.indexOf("build");
                if(cutIndex==-1){
                    cutIndex=path.indexOf("bin");
                }
                path = path.substring(0, cutIndex).replace("%20", " ");

                // opening file
                File pascalCode = new File(path, "\\src\\main\\java\\org\\example\\util\\pascalcode.txt");
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
            lineNumber++;
        }
        return data;
    }

    public static int getLineNumber() {
        return lineNumber;
    }
}
