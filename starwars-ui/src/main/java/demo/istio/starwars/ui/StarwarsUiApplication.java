package demo.istio.starwars.ui;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class StarwarsUiApplication {

    public static void main(String[] args) {
        SpringApplication.run(StarwarsUiApplication.class, args);
    }

    @Bean
    RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @Bean
    QuoteService quoteService(RestTemplate restTemplate,
                                  @Value("${backend.quote-service.url}") String endpoint) {
        return new QuoteService(restTemplate,  endpoint);
    }
}
