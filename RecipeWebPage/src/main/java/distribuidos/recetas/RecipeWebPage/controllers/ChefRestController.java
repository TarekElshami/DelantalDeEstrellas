package distribuidos.recetas.RecipeWebPage.controllers;

import distribuidos.recetas.RecipeWebPage.entities.Chef;
import distribuidos.recetas.RecipeWebPage.service.ChefService;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api")
public class ChefRestController {

    private ChefService chefService;

    public ChefRestController() {
        this.chefService = ChefService.getInstance();
    }

    @GetMapping("/chef/{id}")
    public String showChef(@PathVariable Long id){
        Chef chef = chefService.getChefById(id);
        //TODO: pass the chef info to the model and return it
        return "";
    }

    @PostMapping("/chef/{id}")
    public String newChef(@PathVariable Long id, @RequestParam Chef chef){
        chefService.newChef(chef);
        //TODO: return the correct view
        return "";
    }

    @PutMapping("/chef/{id}")
    public String substituteChef(@PathVariable Long id, @RequestParam Chef chef){
        chefService.substitute(id, chef);
        //TODO: return the correct view
        return "";
    }

    @PatchMapping("/chef/{id}")
    public String modifyChef(@PathVariable Long id, @RequestParam Chef chef){
        chefService.modifyToMatch(id, chef);
        //TODO: return the correct view
        return "";
    }

    @DeleteMapping("/chef/{id}")
    public String newChef(@PathVariable Long id){
        chefService.delete(id);
        //TODO: return the correct view
        return "";
    }
}
