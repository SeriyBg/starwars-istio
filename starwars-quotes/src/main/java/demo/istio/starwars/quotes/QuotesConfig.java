package demo.istio.starwars.quotes;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import lombok.Getter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "data")
public class QuotesConfig {

    @Getter
    private List<String> characters = new ArrayList<>();

    @Getter
    private Map<String, List<String>> quotes = new HashMap<>();

    public String character() {
        return characters.get(new Random().nextInt(characters.size()));
    }

    public String quote(String character) {
        var characterFormatted = character.toLowerCase().replaceAll(" ", "-");
        var characterQuotes = quotes.get(characterFormatted);
        return characterQuotes.get(new Random().nextInt(characterQuotes.size()));
    }
}
