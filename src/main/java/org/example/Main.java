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
                    if(Helpers.isExist(word.trim())){
                        tokens.add(new Token(TokenType.KEYWORD,word,FileScanner.getLineNumber()));
                    }else  if(Helpers.isNumeric(words)){
                        tokens.add(new Token(TokenType.NUM_CONST,word,FileScanner.getLineNumber()));
                    }else {
                        tokens.add(new Token(TokenType.IDENTIFIER,word,FileScanner.getLineNumber()));
                    }
            }
        }


    }
}