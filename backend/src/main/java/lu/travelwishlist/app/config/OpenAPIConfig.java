package lu.travelwishlist.app.config;


import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springframework.context.annotation.Configuration;


/**
 * OpenAPI (Swagger) configuration for the Travel Wishlist application.
 *
 * <p>
 * This class defines:
 * <ul>
 *   <li>General API metadata (title, version, description)</li>
 *   <li>Server information</li>
 *   <li>JWT Bearer authentication for secured endpoints</li>
 * </ul>
 *
 * <p>
 * It is used by Swagger UI to generate interactive API documentation.
 */
@Configuration
@OpenAPIDefinition(
        info = @Info(
                title = "Travel Wishlist App",
                version = "1.0.0",
                description = " API documentation for managing wishlist destinations. " +
                        "Admins define destinations and users manage their wishlists.",
                contact = @Contact(
                        name = "API Support",
                        email = "support@travelwishlist.lu"
                ),
                license = @License(
                        name = "MIT License",
                        url = "https://opensource.org/licenses/MIT"
                )
        ),
        servers = {
                @Server(
                        description = "Local Development Server",
                        url = "${server.url}"
                )
        },
        // We added this
        security = {
                @SecurityRequirement(name = "bearerAuth")
        }

)
@SecurityScheme(
        name = "bearerAuth",
        description = "JWT Authentication",
        scheme = "bearer",
        type = SecuritySchemeType.HTTP,
        bearerFormat = "JWT",
        in = SecuritySchemeIn.HEADER
)
public class OpenAPIConfig {
}