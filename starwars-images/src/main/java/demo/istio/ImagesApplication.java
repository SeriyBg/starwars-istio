package demo.istio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.file.Files;
import java.util.concurrent.Executors;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpServer;

public class ImagesApplication {

    public static void main(String[] args) throws IOException {
        var server = HttpServer.create(new InetSocketAddress(8084), 0);
        server.setExecutor(Executors.newCachedThreadPool());

        server.createContext("/character", exchange -> {
            handleRequest(exchange, "characters", "png");
        });
        server.createContext("/planet", exchange -> {
            handleRequest(exchange, "planets", "jpg");
        });
        server.start();
    }

    private static void handleRequest(HttpExchange exchange, String requestPath, String extension) throws IOException {
        var url = exchange.getRequestURI().toString();
        var split = url.split("/");
        var character = split[split.length - 1];
        var image = new ImageService(requestPath, extension).findImage(character);
        if (image != null) {
            exchange.sendResponseHeaders(200, image.length());
            Files.copy(image.toPath(), exchange.getResponseBody());
        } else {
            exchange.sendResponseHeaders(200, 0);
        }
        exchange.getResponseBody().close();
    }
}
