package demo.istio.starwars.ui;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

@Slf4j
public class QuoteService {

    private final RestTemplate restTemplate;
    private final String endpoint;

    public QuoteService(RestTemplate restTemplate, String endpoint) {
        this.restTemplate = restTemplate;
        this.endpoint = endpoint;
    }

    Quote quote(HttpHeaders headers) {
        final String url = endpoint + "/quote";
        log.info("Url: {}", url);
        try {
            ResponseEntity<Quote> exchange =
                    restTemplate.exchange(url, HttpMethod.GET, new HttpEntity<>("parameters", headers), Quote.class);
            return exchange.getBody();
        } catch (Exception e) {
            throw new RuntimeException("Error getting the quote", e);
        }
    }
}
