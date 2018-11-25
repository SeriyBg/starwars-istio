package demo.istio.starwars.deathstar;

import java.util.Random;

public class DeathStarService {

    private final Planets planets;

    public DeathStarService(Planets planets) {
        this.planets = planets;
    }

    public Planet destroyRandomPlanet() {
        var planets = this.planets.getPlanets();
        return planets.get(new Random(System.currentTimeMillis()).nextInt(planets.size()));
    }
}
