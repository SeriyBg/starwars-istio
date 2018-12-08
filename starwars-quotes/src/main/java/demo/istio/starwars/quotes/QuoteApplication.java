package demo.istio.starwars.quotes;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.concurrent.Executors;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import com.sun.net.httpserver.HttpServer;

public class QuoteApplication {

    public static void main(String[] args) throws IOException {
        var server = HttpServer.create(new InetSocketAddress(8081), 0);
        server.setExecutor(Executors.newCachedThreadPool());
        var yamlObjectMapper = new ObjectMapper(new YAMLFactory());
        var jsonObjectMapper = new ObjectMapper();
        var classLoader = ClassLoader.getSystemClassLoader();
        var resource = classLoader.getResourceAsStream("quotes.yaml");
        var quoteConfig = yamlObjectMapper.readValue(resource, QuotesConfig.class);
        var quoteService = new QuoteService(quoteConfig);

        server.createContext("/quote", exchange -> {
            var responseBody = jsonObjectMapper.writeValueAsBytes(quoteService.randomQuote());
            exchange.sendResponseHeaders(200, responseBody.length);
            exchange.getResponseBody().write(responseBody);
            exchange.getResponseBody().close();
        });
        server.start();
    }
}
