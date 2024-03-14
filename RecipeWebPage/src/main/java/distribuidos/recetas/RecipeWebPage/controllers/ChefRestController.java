package distribuidos.recetas.RecipeWebPage.controllers;

import distribuidos.recetas.RecipeWebPage.entities.Chef;
import distribuidos.recetas.RecipeWebPage.service.ChefService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;


@RestController
@RequestMapping("/api")
public class ChefRestController {

    @Autowired
    private ChefService chefService;

    @GetMapping("/chefs")
    public Collection<Chef> showChefs(){
        return chefService.getAll();
    }
    @GetMapping("/chef/{id}")
    public ResponseEntity<Chef> showChef(@PathVariable Long id){
        Chef chef = chefService.getChefById(id);
        if (chef != null){
            return ResponseEntity.status(200).body(chef);
        } else {
            return ResponseEntity.notFound().build();
        }

    }

    @PostMapping("/chef/new")
    public ResponseEntity<Chef> newChef(@RequestBody Chef chef){
        if (chef.getId() != null){
            return ResponseEntity.badRequest().build();
        }
        chefService.newChef(chef);
        return ResponseEntity.status(200).body(null);
    }

    @PutMapping("/chef/{id}")
    public ResponseEntity<Chef> substituteChef(@PathVariable Long id, @RequestBody Chef chef){
        Chef oldChef = chefService.getChefById(id);
        if (oldChef != null | chef.getId() == null){
            chef.setId(id);
            chefService.substitute(id, chef);
            return ResponseEntity.ok(chef);
        } else if (chef.getId() != null){
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.notFound().build();
    }

    @PatchMapping("/chef/{id}")
    public ResponseEntity<Chef> modifyChef(@PathVariable Long id, @RequestBody Chef chef){
        Chef oldChef = chefService.getChefById(id);
        if (chef.getId() != null) {
            if (oldChef != null) {
                return ResponseEntity.notFound().build();
            }
            chefService.modifyToMatch(id, chef);
            return ResponseEntity.ok(chefService.getChefById(id));
        }
        return ResponseEntity.badRequest().build();
    }

    @DeleteMapping("/chef/{id}")
    public ResponseEntity<Chef> newChef(@PathVariable Long id){
        Chef chef = chefService.getChefById(id);
        if (chef != null){
            chefService.delete(id);
            return ResponseEntity.ok(chef);
        }
        return ResponseEntity.notFound().build();
    }
}
