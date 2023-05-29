package org.example;

import org.example.Exceptions.InvalidIdentifierException;
import org.example.util.FileScanner;

import java.util.ArrayList;

public class LexicalAnalyzer {

    static ArrayList<Token> tokanize() {

        ArrayList<Token> tokens = new ArrayList<Token>();

        String exceptionMessage = "";
        FileScanner.readFile();
        try {
            while (FileScanner.scanner.hasNextLine()) {

                String[] words = FileScanner.nextLine().split(" ");

                for (String word : words) {

                    if (word.trim() == "") continue;
                    word = word.toUpperCase();
                    if (Helpers.isExist(word.trim())) {
                        tokens.add(new Token(TokenType.KEYWORD, word, FileScanner.getLineNumber()));

                    } else if (Helpers.isNumber(word)) {
                        tokens.add(new Token(TokenType.NUM_CONST, word, FileScanner.getLineNumber()));

                    } else {
                        if (Helpers.isNumber(String.valueOf(word.charAt(0)))) {
                            throw new InvalidIdentifierException(new Token(TokenType.IDENTIFIER, word, FileScanner.getLineNumber()));
                        }
                        tokens.add(new Token(TokenType.IDENTIFIER, word, FileScanner.getLineNumber()));
                    }
                }
            }
        } catch (InvalidIdentifierException invalidIdentifierException) {
            exceptionMessage = invalidIdentifierException.toString();
        }

        for (Token token : tokens) {
            System.out.println(token);
        }
        System.out.println("\033[1;31m" + "\n" + exceptionMessage + "\033[0m");
        return tokens;

    }
}
