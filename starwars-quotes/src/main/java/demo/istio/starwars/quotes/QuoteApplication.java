package demo.istio.starwars.quotes;

import java.io.File;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.Objects;
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

        server.createContext("/quote", exchange -> {
            var classLoader = QuoteApplication.class.getClassLoader();
            var file = new File(Objects.requireNonNull(classLoader.getResource("quotes.yaml")).getFile());
            var quoteConfig = yamlObjectMapper.readValue(file, QuotesConfig.class);
            var quote = new QuoteService(quoteConfig).randomQuote();
            var responseBody = jsonObjectMapper.writeValueAsBytes(quote);
            exchange.sendResponseHeaders(200, responseBody.length);
            exchange.getResponseBody().write(responseBody);
            exchange.getResponseBody().close();
        });
        server.start();
    }
}
