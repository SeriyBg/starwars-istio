package demo.istio.starwars.quotes;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class QuoteService {

    private final QuotesConfig quotesConfig;

    @Autowired
    public QuoteService(QuotesConfig quotesConfig) {
        this.quotesConfig = quotesConfig;
    }

    public Quote randomQuote() {
        String character = quotesConfig.character();
        String quote = quotesConfig.quote(character);
        return new Quote(character, quote);
    }
}
