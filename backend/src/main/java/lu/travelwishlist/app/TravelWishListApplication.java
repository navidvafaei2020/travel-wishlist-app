package lu.travelwishlist.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


/**
 * Main entry point for the Travel Wish List Spring Boot application.
 *
 * <p>This class bootstraps the application and starts the embedded Spring container.</p>
 */
@SpringBootApplication
public class TravelWishListApplication {

    /**
     * Main method to launch the Spring Boot application.
     *
     * @param args Command-line arguments (not typically used).
     */
    public static void main(String[] args)
    {
        SpringApplication.run(TravelWishListApplication.class, args);
    }
}
