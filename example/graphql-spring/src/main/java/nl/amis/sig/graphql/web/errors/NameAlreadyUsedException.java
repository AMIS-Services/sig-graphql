package nl.amis.sig.graphql.web.errors;

import java.util.List;

import graphql.ErrorType;
import graphql.GraphQLError;
import graphql.language.SourceLocation;

public class NameAlreadyUsedException extends RuntimeException implements GraphQLError {
    private static final long serialVersionUID = 1L;

    private String message;

    public NameAlreadyUsedException(String name) {
        super("Name \"" + name + "\" is already in use!");
        this.message = "Name \"" + name + "\" is already in use!";
    }

    @Override
    public String getMessage() {
        return this.message;
    }

    @Override
    public List<SourceLocation> getLocations() {
        return null;
    }

    @Override
    public ErrorType getErrorType() {
        return ErrorType.ValidationError;
    }
}