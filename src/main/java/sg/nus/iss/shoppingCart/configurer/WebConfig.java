/**
 * 
 */
package sg.nus.iss.shoppingCart.configurer;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Creator:
 * Date:11 Oct 2024
 * Explain:
 */
@Configuration
public class WebConfig {
	@Bean
	public WebMvcConfigurer corsConfigurer() {
		return new WebMvcConfigurer() {
			@Override
			public void addCorsMappings(CorsRegistry registry) {
				registry.addMapping("/**")
				        .allowedOrigins("http://localhost:3000")
				        .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
				        .allowedHeaders("*")
				        .allowCredentials(true);
			}
		};
	}
}
