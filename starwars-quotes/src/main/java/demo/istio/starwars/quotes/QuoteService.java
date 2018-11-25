package demo.istio.starwars.quotes;

public class QuoteService {

    private final QuotesConfig quotesConfig;

    public QuoteService(QuotesConfig quotesConfig) {
        this.quotesConfig = quotesConfig;
    }

    public Quote randomQuote() {
        var character = quotesConfig.character();
        var quote = quotesConfig.quote(character);
        return new Quote(character, quote);
    }
}
