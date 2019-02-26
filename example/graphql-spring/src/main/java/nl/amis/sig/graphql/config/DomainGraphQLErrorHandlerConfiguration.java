package nl.amis.sig.graphql.config;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import graphql.ExceptionWhileDataFetching;
import graphql.GraphQLError;
import graphql.servlet.GenericGraphQLError;
import graphql.servlet.GraphQLErrorHandler;

@Configuration
public class DomainGraphQLErrorHandlerConfiguration {
    @Bean
    public GraphQLErrorHandler errorHandler() {
        return new GraphQLErrorHandler() {
            @Override
            public List<GraphQLError> processErrors(List<GraphQLError> errors) {

                System.out.println("errors: " + errors);

                final List<GraphQLError> clientErrors = filterGraphQLErrors(errors);

                // add all businessRuleErrors
                errors.forEach(error -> {
                    if (error instanceof ExceptionWhileDataFetching) {
                        clientErrors.add(new GenericGraphQLError(
                                ((ExceptionWhileDataFetching) error).getException().getMessage()));
                    }
                });

                System.out.println("errors: " + clientErrors);

                return clientErrors;
            }

            protected List<GraphQLError> filterGraphQLErrors(List<GraphQLError> errors) {
                return errors.stream().filter(this::isClientError).collect(Collectors.toList());
            }

            protected boolean isClientError(GraphQLError error) {
                return !(error instanceof ExceptionWhileDataFetching || error instanceof Throwable);
            }
        };
    }
}