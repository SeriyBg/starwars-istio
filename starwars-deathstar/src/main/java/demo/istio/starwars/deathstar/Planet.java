package demo.istio.starwars.deathstar;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@Getter @Setter
@ToString
public class Planet {
    private String name;
    private String region;
    private String sector;
    private int moons;
}
