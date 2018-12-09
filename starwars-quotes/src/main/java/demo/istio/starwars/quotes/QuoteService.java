package demo.istio.starwars.quotes;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Base64;

public class QuoteService {

    private final QuotesConfig quotesConfig;
    private static final System.Logger logger = System.getLogger(QuoteService.class.getName());

    public QuoteService(QuotesConfig quotesConfig) {
        this.quotesConfig = quotesConfig;
    }

    public Quote randomQuote() {
        var character = quotesConfig.character();
        var quote = quotesConfig.quote(character);
        Quote result = new Quote(character, quote);

        var characterFormatted = character.toLowerCase().replaceAll(" ", "-");
        String imageServiceEndpoint = System.getenv().getOrDefault("IMAGE_SERVICE_URL", "http://localhost:8084");
        logger.log(System.Logger.Level.INFO, "Request for character image to endpoint " + imageServiceEndpoint + " for character " + characterFormatted);
        HttpRequest.Builder httpRequest = HttpRequest.newBuilder(URI.create(imageServiceEndpoint + "/character/" + characterFormatted))
                .GET()
                .version(HttpClient.Version.HTTP_1_1)
                .setHeader("User-Agent", "Java/9");
        HttpClient httpClient = HttpClient.newHttpClient();

        try {
            HttpResponse<byte[]> response = httpClient.send(httpRequest.build(), HttpResponse.BodyHandlers.ofByteArray());
            logger.log(System.Logger.Level.INFO, "Received response with status code " + response.statusCode());
            String imgBase64 = Base64.getEncoder().encodeToString(response.body());
            result.setImage(imgBase64);
        } catch (IOException | InterruptedException e) {
            logger.log(System.Logger.Level.ERROR, "Error getting image: " + e);
            e.printStackTrace();
        }
        return result;
    }
}
