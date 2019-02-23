package nl.amis.sig.graphql;

import java.net.InetAddress;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.env.Environment;

@SpringBootApplication
public class GraphqlSpringApp {

    private static final Logger log = LoggerFactory.getLogger(GraphqlSpringApp.class);

    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(GraphqlSpringApp.class);
        Environment env = app.run(args).getEnvironment();
        String hostAddress = "localhost";
        try {
            hostAddress = InetAddress.getLocalHost().getHostAddress();
        } catch (Exception e) {
            log.warn("The host name could not be determined, using `localhost` as fallback");
        }
        //@formatter:off
        log.info("\n----------------------------------------------------------\n\t" +
                "Application '{}' is running! Access URL:\n\t" +
                "Local: http://localhost:{}\n\t" +
                "External: http://{}:{}\n\t" +
                "Profile(s): \t{}\n----------------------------------------------------------",
            env.getProperty("spring.application.name"),
            env.getProperty("server.port"),
            hostAddress,
            env.getProperty("server.port"),
            env.getActiveProfiles());
        //@formatter:on
    }

}
