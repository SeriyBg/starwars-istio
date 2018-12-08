package demo.istio.starwars.deathstar;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@RequiredArgsConstructor
@NoArgsConstructor
@Getter @Setter
@ToString
public class Planet {
    @NonNull private String name;
    @NonNull private String region;
    @NonNull private String sector;
    @NonNull private int moons;

    private String image;
}
