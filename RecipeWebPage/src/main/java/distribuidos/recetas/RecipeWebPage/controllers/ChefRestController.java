package distribuidos.recetas.RecipeWebPage.controllers;

import distribuidos.recetas.RecipeWebPage.entities.Chef;
import distribuidos.recetas.RecipeWebPage.service.ChefService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api")
public class ChefRestController {

    private ChefService chefService;

    public ChefRestController() {
        this.chefService = ChefService.getInstance();
    }

    @GetMapping("/chef/{id}")
    public ResponseEntity<Chef> showChef(@PathVariable Long id){
        Chef chef = chefService.getChefById(id);
        //TODO: pass the chef info to the model and return it
        return ResponseEntity.status(200).body(null);
    }

    @PostMapping("/chef/{id}")
    public ResponseEntity<Chef> newChef(@PathVariable Long id, @RequestParam Chef chef){
        chefService.newChef(chef);
        //TODO: return the correct view
        return ResponseEntity.status(200).body(null);
    }

    @PutMapping("/chef/{id}")
    public ResponseEntity<Chef> substituteChef(@PathVariable Long id, @RequestParam Chef chef){
        chefService.substitute(id, chef);
        //TODO: return the correct view
        return ResponseEntity.status(200).body(null);
    }

    @PatchMapping("/chef/{id}")
    public ResponseEntity<Chef> modifyChef(@PathVariable Long id, @RequestParam Chef chef){
        chefService.modifyToMatch(id, chef);
        //TODO: return the correct view
        return ResponseEntity.status(200).body(null);
    }

    @DeleteMapping("/chef/{id}")
    public ResponseEntity<Chef> newChef(@PathVariable Long id){
        chefService.delete(id);
        //TODO: return the correct view
        return ResponseEntity.status(200).body(null);
    }
}
