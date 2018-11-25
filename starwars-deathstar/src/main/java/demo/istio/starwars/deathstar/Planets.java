package demo.istio.starwars.deathstar;

import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "data")
public class Planets {

    @Getter
    private List<Planet> planets = new ArrayList<>();
}
