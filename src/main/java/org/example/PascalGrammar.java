package org.example;

import java.util.ArrayList;

public class PascalGrammar {
    ArrayList<Token> tokens;
    int currentIndex;
    Token currentToken;

    PascalGrammar(ArrayList<Token> tokens) {
        this.tokens = tokens;
        currentIndex = 0;
        currentToken = tokens.get(currentIndex);
        analyzeSyntax();
    }

    // MAIN method to analyze the code
    private void analyzeSyntax() {
        match("PROGRAM", TokenType.KEYWORD);
        match(currentToken.token, TokenType.IDENTIFIER);
        match(";", TokenType.KEYWORD);
        analyzeDeclarations();
        match(";", TokenType.KEYWORD);
        analyzeSubProgramDeclarations();
        match(";", TokenType.KEYWORD);
        analyzeCompoundStatement();
    }

    private void analyzeCompoundStatement() {
        match("BEGIN", TokenType.KEYWORD);
        analyzeOptionalStatement();
        match("END", TokenType.KEYWORD);
    }

    private void analyzeOptionalStatement() {

    }


    private void analyzeSubProgramDeclarations() {
        if (currentToken.token.equals("FUNCTION")) {
            match("FUNCTION", TokenType.KEYWORD);
            match(currentToken.token, TokenType.IDENTIFIER);
            analyzeArguments();
            match(";", TokenType.KEYWORD);
        }
    }

    private void analyzeArguments() {
        match("(", TokenType.KEYWORD);
        analyzeParameterList();
        match(")", TokenType.KEYWORD);
        match(":", TokenType.KEYWORD);
        analyzeStandardType();
    }

    private void analyzeParameterList() {
        match(currentToken.token, TokenType.IDENTIFIER);
        analyzeIdentifierList();
        match(":", TokenType.KEYWORD);
        analyzeType();
    }

    private void analyzeDeclarations() {
        if (currentToken.token.equals("VAR")) {
            match("VAR", TokenType.KEYWORD);
            match(currentToken.token, TokenType.IDENTIFIER);
            analyzeIdentifierList();
            match(":", TokenType.KEYWORD);
            analyzeType();
        }

    }

    private void analyzeIdentifierList() {
        if (currentToken.token.equals(",")) {
            match(",", TokenType.KEYWORD);
            match(currentToken.token, TokenType.IDENTIFIER);
            analyzeIdentifierList();
        }
    }

    private void analyzeType() {
        if (currentToken.token.equals("ARRAY")) {
            match("ARRAY", TokenType.KEYWORD);
            match("[", TokenType.KEYWORD);
            match("NUM", TokenType.KEYWORD);
            match("..", TokenType.KEYWORD);
            match("NUM", TokenType.KEYWORD);
            match("]", TokenType.KEYWORD);
            match("OF", TokenType.KEYWORD);
        }
        analyzeStandardType();
    }

    private void analyzeStandardType() {
        if (currentToken.token.equals("INTEGER")) {
            match("INTEGER", TokenType.KEYWORD);
        } else if (currentToken.token.equals("REAL")) {
            match("REAL", TokenType.KEYWORD);
        }
    }


    private void match(String expectedTokenName, TokenType expectedTokenType) {
        System.out.println("================");
        System.out.println(expectedTokenName);
        System.out.println(currentToken.token);
        System.out.println(expectedTokenType);
        System.out.println(currentToken.type);
        System.out.println(expectedTokenType == TokenType.KEYWORD);
        System.out.println(expectedTokenName.toUpperCase().equals(currentToken.token));
        System.out.println(expectedTokenType == currentToken.type);

        if ((expectedTokenType == TokenType.KEYWORD && expectedTokenName.toUpperCase().equals(currentToken.token) && expectedTokenType == currentToken.type) || (expectedTokenType != TokenType.KEYWORD && expectedTokenType == currentToken.type)) {
            nextToken();
        } else {
            error(expectedTokenName, expectedTokenType);
            System.exit(0);
        }
    }

    private void nextToken() {
        if (currentIndex < tokens.size() - 1) {
            currentToken = tokens.get(++currentIndex);
        } else {
            System.out.println("Pascal Grammar done without any errors");
            System.exit(0);
        }
    }

    private void error(String expectedTokenName, TokenType expectedTokenType) {
        switch (expectedTokenType) {
            case KEYWORD -> System.out.println(">>> It is expected to have a keyword " + expectedTokenName + " in line: " + currentToken.line);
            case IDENTIFIER -> System.out.println(">>> It is expected to have a identifier " + expectedTokenName + " in line: " + currentToken.line);
            case NUM_CONST -> System.out.println(">>> It is expected to have a numeric constant " + expectedTokenName + " in line: " + currentToken.line);
        }
    }

}
