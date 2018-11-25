package demo.istio.starwars.deathstar;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties(Planets.class)
public class StarwarsDeathstarApplication {

	public static void main(String[] args) {
		SpringApplication.run(StarwarsDeathstarApplication.class, args);
	}
}
