package szp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * This is the SzpApplication class that serves as the entry point for the Spring Boot application.
 * It is annotated with @SpringBootApplication, a convenience annotation that adds all of the following:
 * @Configuration: Tags the class as a source of bean definitions for the application context.
 * @EnableAutoConfiguration: Tells Spring Boot to start adding beans based on classpath settings, other beans, and various property settings.
 * @ComponentScan: Tells Spring to look for other components, configurations, and services in the 'szp' package, allowing it to find the controllers.
 */
@SpringBootApplication
public class SzpApplication {

    /**
     * This is the main method which serves as the entry point for the JVM.
     * @param args The command line arguments.
     */
    public static void main(String[] args) {
        // The SpringApplication.run() method launches an application. Here it's launching the SzpApplication.
        SpringApplication.run(SzpApplication.class, args);
    }

}