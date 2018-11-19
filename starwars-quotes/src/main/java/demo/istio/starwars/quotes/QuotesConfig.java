package demo.istio.starwars.quotes;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import lombok.Getter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties
@ConfigurationProperties
public class QuotesConfig {

    @Getter
    private List<String> characters = new ArrayList<>();

    @Getter
    private Map<String, List<String>> quotes = new HashMap<>();

    public String character() {
        return characters.get(new Random().nextInt(characters.size()));
    }

    public String quote(String character) {
        String characterFormatted = character.toLowerCase().replaceAll(" ", "-");
        List<String> characterQuotes = quotes.get(characterFormatted);
        return characterQuotes.get(new Random().nextInt(characterQuotes.size()));
    }
}
