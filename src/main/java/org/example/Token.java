package org.example;

public class Token {
    public TokenType type;
    public String token;
    public int line;

    public Token(TokenType type, String token, int line) {
        this.type = type;
        this.token = token;
        this.line = line;
    }

    @Override
    public String toString() {
        return "Token: " + this.token + " Type: " + this.type + " Line: " + this.line;
    }
}
