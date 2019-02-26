package nl.amis.sig.graphql.web.graphql;

import com.coxautodev.graphql.tools.GraphQLQueryResolver;

import org.springframework.stereotype.Component;

@Component
public class HelloResolver implements GraphQLQueryResolver {
    public String hello() {
        return "Hello world!";
    }
}