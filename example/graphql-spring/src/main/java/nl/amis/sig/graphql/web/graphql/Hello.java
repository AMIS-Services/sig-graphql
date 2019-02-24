package nl.amis.sig.graphql.web.graphql;

import com.coxautodev.graphql.tools.GraphQLQueryResolver;

import org.springframework.stereotype.Controller;

@Controller
public class Hello implements GraphQLQueryResolver {
    public String hello() {
        return "Hello world!";
    }
}