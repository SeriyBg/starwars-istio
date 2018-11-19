package demo.istio.starwars.deathstar;

import java.util.List;
import java.util.Random;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DeathStarService {

    private final Planets planets;

    @Autowired
    public DeathStarService(Planets planets) {
        this.planets = planets;
    }

    public Planet destroyRandomPlanet() {
        List<Planet> planets = this.planets.getPlanets();
        return planets.get(new Random(System.currentTimeMillis()).nextInt(planets.size()));
    }
}
