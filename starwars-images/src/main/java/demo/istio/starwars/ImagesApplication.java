package demo.istio.starwars;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.concurrent.Executors;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpServer;

public class ImagesApplication {

    private static final System.Logger logger = System.getLogger(ImagesApplication.class.getName());

    public static void main(String[] args) throws IOException {
        var server = HttpServer.create(new InetSocketAddress(8084), 0);
        server.setExecutor(Executors.newCachedThreadPool());

        server.createContext("/character", exchange -> handleRequest(exchange, "characters", "png"));
        server.createContext("/planet", exchange -> handleRequest(exchange, "planets", "jpg"));
        server.start();
    }

    private static void handleRequest(HttpExchange exchange, String requestPath, String extension) throws IOException {
        logger.log(System.Logger.Level.INFO, "Receiving request for image with path " + requestPath);
        var url = exchange.getRequestURI().toString();
        var split = url.split("/");
        var character = split[split.length - 1];
        var image = new ImageService(requestPath, extension).findImage(character);
        exchange.sendResponseHeaders(200, 0);
        image.transferTo(exchange.getResponseBody());
        exchange.getResponseBody().close();
    }
}
