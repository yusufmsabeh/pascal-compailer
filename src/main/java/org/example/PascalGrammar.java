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

        analyzeCompoundStatement();
    }

    private void analyzeCompoundStatement() {
        if (currentToken.token.equals("BEGIN")) {
            match("BEGIN", TokenType.KEYWORD);
            analyzeOptionalStatement();
            match("END", TokenType.KEYWORD);
        }

    }

    private void analyzeOptionalStatement() {
        if (!currentToken.token.equals("END")) {
            analyzeStatementList();
        }
    }

    private void analyzeStatementList() {
        analyzeStatement();
    }

    private void analyzeStatement() {
        switch (currentToken.token) {
            case "BEGIN" -> analyzeCompoundStatement();
            case "IF" -> {
                match("IF", TokenType.KEYWORD);
                analyzeExpression();
                match("THEN", TokenType.KEYWORD);
                analyzeStatement();
                match("ELSE", TokenType.KEYWORD);
                analyzeStatement();
                match(";",TokenType.KEYWORD);
            }
            case "WHILE" -> {
                match("WHILE", TokenType.KEYWORD);
                analyzeExpression();
                match("DO", TokenType.KEYWORD);
                analyzeStatement();
            }
            default -> analyzeVariableOrProcedureStatement();
        }
    }

    public void analyzeVariableOrProcedureStatement() {
        match(currentToken.token, TokenType.IDENTIFIER);
        analyzeVariableOrProcedureStatementRecursive();
    }

    public void analyzeVariableOrProcedureStatementRecursive() {
        switch (currentToken.token) {
            case "[" -> {
                match("[", TokenType.KEYWORD);
                analyzeExpression();
                match("]", TokenType.KEYWORD);
                match(":=", TokenType.KEYWORD);
                analyzeExpression();
            }
            case "(" -> {
                match("(", TokenType.KEYWORD);
                analyzeExpressionList();
                match(")", TokenType.KEYWORD);
            }
            default -> {
                match(":=", TokenType.KEYWORD);
                analyzeExpression();
            }
        }
    }


    public void analyzeExpression() {
        analyzeSimpleExpression();
        parseExpressionRecursive();
    }

    public void parseExpressionRecursive() {
        switch (currentToken.token) {
            case "=", "<>", "<", ">=", "=<", ">" -> {
                analyzeRelOp();
                analyzeSimpleExpression();
            }
        }
    }

    public void analyzeSimpleExpression() {
        if (currentToken.token.equals("+") || currentToken.token.equals("-")) {
            analyzeSign();
            return;
        }

        analyzeTerm();
        parseSimpleExpressionRecursive();
    }

    public void analyzeExpressionList() {
        analyzeExpression();
        analyzeExpressionListRecursive();
    }

    public void analyzeExpressionListRecursive() {
        if (currentToken.token.equals(",")) {
            match(",", TokenType.KEYWORD);
            analyzeExpression();
            analyzeExpressionListRecursive();
        }
    }

    public void analyzeSign() {
        if (currentToken.token.equals("+")) {
            match("+", TokenType.KEYWORD);
            return;
        }

        match("-", TokenType.KEYWORD);
    }

    public void parseSimpleExpressionRecursive() {
        if (currentToken.token.equals("+") || currentToken.token.equals("-")) {
            analyzeSign();
            analyzeTerm();
            parseSimpleExpressionRecursive();
        }
    }

    private void analyzeTerm() {
        analyzeFactor();
        analyzeFactorList();
    }

    private void analyzeFactorList() {
        if (currentToken.token.equals("*")) {
            match("*", TokenType.KEYWORD);
            analyzeFactor();
            analyzeIdentifierList();
        }
    }

    private void analyzeFactor() {
        if (currentToken.type == TokenType.IDENTIFIER) {
            match(currentToken.token, TokenType.IDENTIFIER);
            analyzeFactor();
        } else if (currentToken.type == TokenType.NUM_CONST) {
            match(currentToken.token, TokenType.NUM_CONST);
        } else if (currentToken.token.equals("(")) {
            match("(", TokenType.KEYWORD);
            analyzeExpressionList();
            match(")", TokenType.KEYWORD);
        } else if (currentToken.token.equals("NOT")) {
            match("NOT", TokenType.KEYWORD);
            analyzeFactor();
        } else if (currentToken.token.equals("+") || currentToken.token.equals("-") || currentToken.token.equals("/") || currentToken.token.equals("*") || currentToken.token.equals("MOD")) {
            switch (currentToken.token){
                case "+"->match("+",TokenType.KEYWORD);
                case "-"->match("-",TokenType.KEYWORD);
                case "*"->match("*",TokenType.KEYWORD);
                case "/"->match("/",TokenType.KEYWORD);
                case "MOD"->match("MOD",TokenType.KEYWORD);
            }
            analyzeFactor();
        }
    }

    private void analyzeRelOp() {
        switch (currentToken.token) {
            case "=", "<>", "<", ">=", "=<", ">" -> {
                match(currentToken.token, TokenType.KEYWORD);
                analyzeSimpleExpression();
            }
        }
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
//        System.out.println(expectedTokenName.equals(currentToken.token));
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
