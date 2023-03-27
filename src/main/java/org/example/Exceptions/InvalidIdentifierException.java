package org.example.Exceptions;

import org.example.Token;

public class InvalidIdentifierException extends Exception {
    public InvalidIdentifierException(Token token){
        super("Invalid Identifier Token: "+token.token+" Line: "+token.line);
    }
}
