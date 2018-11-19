package demo.istio.starwars.deathstar;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DeathStarController {

    private final DeathStarService deathStarService;

    @Autowired
    public DeathStarController(DeathStarService deathStarService) {
        this.deathStarService = deathStarService;
    }

    @GetMapping("/destroy")
    public Planet destroy() {
        return deathStarService.destroyRandomPlanet();
    }
}
