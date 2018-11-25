package demo.istio.starwars.deathstar;

import java.util.ArrayList;
import java.util.List;
import lombok.Getter;

public class Planets {

    @Getter
    private List<Planet> planets = new ArrayList<>();
}
