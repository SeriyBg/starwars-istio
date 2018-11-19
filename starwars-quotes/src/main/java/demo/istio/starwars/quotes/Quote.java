package demo.istio.starwars.quotes;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class Quote {
    private String character;
    private String quote;
}
