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

    private void analyzeSyntax() {
        match("PROGRAM", TokenType.KEYWORD);
        match(currentToken.token, TokenType.IDENTIFIER);
        match(";", TokenType.KEYWORD);
        analyzeDeclarations();
        match(";", TokenType.KEYWORD);
        analyzeSubProgramDeclarations();
        match(";", TokenType.KEYWORD);
        analyzeCompoundStatment();

    }

    private void analyzeCompoundStatment() {
        match("BEGIN", TokenType.KEYWORD);
        analyzeOptionalStatement();
        match("END", TokenType.KEYWORD);
    }

    private void analyzeOptionalStatement() {
        if (!currentToken.token.equals("END"))
            analyzeStatementList();
    }

    private void analyzeStatementList() {
        if (currentToken.type == TokenType.IDENTIFIER) {
            match(currentToken.token, TokenType.IDENTIFIER);
            match(":=", TokenType.KEYWORD);
            analyzeExpression();
        }
    }
private void analyzeStatement(){
    if (currentToken.type == TokenType.IDENTIFIER) {
        match(currentToken.token, TokenType.IDENTIFIER);
        match(":=", TokenType.KEYWORD);
        analyzeExpression();
    }
    if(currentToken.token.equals("IF")){
        match("IF",TokenType.KEYWORD);
        analyzeExpression();
        match("THEN",TokenType.KEYWORD);
        analyzeStatement();
        match("ELSE",TokenType.KEYWORD);
        analyzeStatement();
    }
    if(currentToken.token.equals("WHILE")){
        match("WHILE",TokenType.KEYWORD);
        analyzeExpression();
        match("DO",TokenType.KEYWORD);
        analyzeStatement();
    }
}


    private void analyzeExpression() {
        analyzeSimpleExpression();
    }

    private void analyzeSimpleExpression() {
        analyzeTerm();

    }

    private void analyzeTerm() {
        analyzeFactory();
        analyzeTerms();
    }

    private void analyzeTerms() {
        if (currentToken.token.equals("*")) {
            match("*", TokenType.KEYWORD);
            analyzeFactory();
            analyzeTerm();
        }
    }

    private void analyzeFactory() {
        if (currentToken.type == TokenType.IDENTIFIER) {
            match(currentToken.token, TokenType.IDENTIFIER);
            analyzeFactory();
        }
        if (currentToken.type == TokenType.NUM_CONST) {
            match(currentToken.token, TokenType.NUM_CONST);
        }
        if (currentToken.token.equals("(")) {
            match("(", TokenType.KEYWORD);
            analyzeExpression();
            match(")", TokenType.KEYWORD);
        }
        if (currentToken.token.equals("NOT")) {
            match("NOT", TokenType.KEYWORD);
            analyzeFactory();
        }
    }

    private void analyzeFactories() {
        if (currentToken.token.equals("(")) {
            match("(", TokenType.KEYWORD);
            analyzeExpression();
            match(")", TokenType.KEYWORD);
        }
    }

    private void analyzeSubProgramDeclarations() {
        if (currentToken.token.equals("FUNCTION")) {
            match("FUNCTION", TokenType.KEYWORD);
            match(currentToken.token, TokenType.IDENTIFIER);
            analyzeArguments();
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
        match("VAR", TokenType.KEYWORD);
        match(currentToken.token, TokenType.IDENTIFIER);
        analyzeIdentifierList();
        match(":", TokenType.KEYWORD);
        analyzeType();
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
            match("[", TokenType.KEYWORD);
            match("NUM", TokenType.KEYWORD);
            match(".", TokenType.KEYWORD);
            match(".", TokenType.KEYWORD);
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
