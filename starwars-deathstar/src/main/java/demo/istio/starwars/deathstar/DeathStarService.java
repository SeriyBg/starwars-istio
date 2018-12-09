package demo.istio.starwars.deathstar;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Base64;
import java.util.Random;

public class DeathStarService {

    private static final System.Logger logger = System.getLogger(DeathStarService.class.getName());

    private final Planets planets;

    public DeathStarService(Planets planets) {
        this.planets = planets;
    }

    public Planet destroyRandomPlanet() {
        var planets = this.planets.getPlanets();
        Planet planet = planets.get(new Random(System.currentTimeMillis()).nextInt(planets.size()));

        String imageServiceEndpoint = System.getenv().getOrDefault("IMAGE_SERVICE_URL", "http://localhost:8084");
        logger.log(System.Logger.Level.INFO, "Request for planet image to endpoint: " + imageServiceEndpoint);
        var planetNameFormatted = planet.getName().toLowerCase().replaceAll(" ", "-");
        HttpRequest.Builder httpRequest = HttpRequest.newBuilder(URI.create(imageServiceEndpoint + "/planet/" + planetNameFormatted))
                .GET()
                .version(HttpClient.Version.HTTP_1_1)
                .setHeader("User-Agent", "Java/9");
        HttpClient httpClient = HttpClient.newHttpClient();

        try {
            HttpResponse<byte[]> response = httpClient.send(httpRequest.build(), HttpResponse.BodyHandlers.ofByteArray());
            String imgBase64 = Base64.getEncoder().encodeToString(response.body());
            planet.setImage(imgBase64);
        } catch (IOException | InterruptedException e) {
            logger.log(System.Logger.Level.ERROR, "Error getting image for planet: " + e);
            e.printStackTrace();
        }
        return planet;
    }
}
