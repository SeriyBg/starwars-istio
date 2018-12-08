package demo.istio.starwars.quotes;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@RequiredArgsConstructor
@Getter
public class Quote {
    private final String character;
    private final String quote;
    @Setter
    private String image;
}
