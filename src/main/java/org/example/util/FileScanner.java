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
                System.out.println(path);
                int cutIndex = path.indexOf("build");
                if(cutIndex==-1){
                    path = path+"org/example/util/pascalcode.txt";
                }else{
                    path = path.substring(0, cutIndex)+"\\src\\main\\java\\org\\example\\util\\pascalcode.txt";

                }
                path  =path.replace("%20", " ");
                System.out.println(path);
                // opening file
                File pascalCode = new File(path);
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
