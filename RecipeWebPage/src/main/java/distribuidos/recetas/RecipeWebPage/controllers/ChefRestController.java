package distribuidos.recetas.RecipeWebPage.controllers;

import distribuidos.recetas.RecipeWebPage.entities.Chef;
import distribuidos.recetas.RecipeWebPage.service.ChefService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;


@RestController
@RequestMapping("/api")
public class ChefRestController {

    private ChefService chefService;

    public ChefRestController() {
        this.chefService = ChefService.getInstance();
    }

    @GetMapping("/chefs")
    public Collection<Chef> showChefs(){
        return chefService.getAll();
    }
    @GetMapping("/chefs/{id}")
    public ResponseEntity<Chef> showChef(@PathVariable Long id){
        Chef chef = chefService.getChefById(id);
        if (chef != null){
            return ResponseEntity.status(200).body(chef);
        } else {
            return ResponseEntity.notFound().build();
        }

    }

    @PostMapping("/chefs/new")
    public ResponseEntity<Chef> newChef(@RequestBody Chef chef){
        chefService.newChef(chef);
        return ResponseEntity.status(200).body(null);
    }

    @PutMapping("/chefs/{id}")
    public ResponseEntity<Chef> substituteChef(@PathVariable Long id, @RequestBody Chef chef){
        Chef oldChef = chefService.getChefById(id);
        if (oldChef != null){
            chef.setId(id);
            chefService.substitute(id, chef);
            return ResponseEntity.ok(chef);
        }
        return ResponseEntity.notFound().build();
    }

    @PatchMapping("/chefs/{id}")
    public ResponseEntity<Chef> modifyChef(@PathVariable Long id, @RequestBody Chef chef){
        chefService.modifyToMatch(id, chef);
        //TODO: return the correct view
        return ResponseEntity.status(200).body(null);
    }

    @DeleteMapping("/chefs/{id}")
    public ResponseEntity<Chef> newChef(@PathVariable Long id){
        Chef chef = chefService.getChefById(id);
        if (chef != null){
            chefService.delete(id);
            return ResponseEntity.ok(chef);
        }
        return ResponseEntity.notFound().build();
    }
}
