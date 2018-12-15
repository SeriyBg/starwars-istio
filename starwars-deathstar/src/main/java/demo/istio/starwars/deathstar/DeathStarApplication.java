package demo.istio.starwars.deathstar;

import java.io.IOException;
import java.net.InetSocketAddress;
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
        var classLoader = DeathStarApplication.class.getClassLoader();
        var resource = classLoader.getResourceAsStream("planets.yaml");
        var planets = yamlObjectMapper.readValue(resource, Planets.class);
        var deathStar = new DeathStarService(planets);

        server.createContext("/destroy", exchange -> {
            var responseBody = jsonObjectMapper.writeValueAsBytes(deathStar.destroyRandomPlanet(exchange.getRequestHeaders()));
            exchange.sendResponseHeaders(200, responseBody.length);
            exchange.getResponseBody().write(responseBody);
            exchange.getResponseBody().close();
        });
        server.start();
    }
}
