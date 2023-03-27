package org.example;

import org.example.util.FileScanner;

import java.util.ArrayList;
import org.example.StaticFiles;
import  org.example.Helpers;

public class Main {
    public static void main(String[] args) {
        ArrayList<Token> tokens = new ArrayList<Token>();
        String[] PASCALKEYWORDS = StaticFiles.PASCALKEYWORDS;
        FileScanner.readFile();
        while (FileScanner.scanner.hasNextLine()) {
            String[] words = FileScanner.nextLine().split(" ");

            for (String word : words) {
                if(word.trim()=="")
                    continue;
                    if(Helpers.isExist(word.trim())){
                        tokens.add(new Token(TokenType.KEYWORD,word,FileScanner.getLineNumber()));
                        continue;
                    }else  if(Helpers.isNumber(word)){
                        tokens.add(new Token(TokenType.NUM_CONST,word,FileScanner.getLineNumber()));
                        continue;

                    }else {
                        tokens.add(new Token(TokenType.IDENTIFIER,word,FileScanner.getLineNumber()));
                        continue;
                    }
            }
        }
    for(Token token : tokens){
        System.out.println(token);
    }

    }
}