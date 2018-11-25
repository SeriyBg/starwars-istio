package demo.istio.starwars.deathstar;

import java.io.File;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.Objects;
import java.util.concurrent.Executors;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import com.sun.net.httpserver.HttpServer;

public class DeathStarApplication {

    public static void main(String[] args) throws IOException {
        var server = HttpServer.create(new InetSocketAddress(8082), 0);
        server.setExecutor(Executors.newCachedThreadPool());
        var yamlObjectMapper = new ObjectMapper(new YAMLFactory());
        var jsonObjectMapper = new ObjectMapper();

        server.createContext("/destroy", exchange -> {
            var classLoader = DeathStarApplication.class.getClassLoader();
            var file = new File(Objects.requireNonNull(classLoader.getResource("planets.yaml")).getFile());
            var planets = yamlObjectMapper.readValue(file, Planets.class);
            var planet = new DeathStarService(planets).destroyRandomPlanet();
            var responseBody = jsonObjectMapper.writeValueAsBytes(planet);
            exchange.sendResponseHeaders(200, responseBody.length);
            exchange.getResponseBody().write(responseBody);
            exchange.getResponseBody().close();
        });
        server.start();
    }
}
