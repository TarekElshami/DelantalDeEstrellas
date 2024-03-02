package distribuidos.recetas.RecipeWebPage.controllers;

import distribuidos.recetas.RecipeWebPage.entities.Ingredient;
import distribuidos.recetas.RecipeWebPage.service.IngredientService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class IngredientRestController {

    private IngredientService ingredientService;

    public IngredientRestController() {
        this.ingredientService = IngredientService.getInstance();
    }

    @GetMapping("/ingredient/{id}")
    public String showIngredient(@PathVariable Long id){
        Ingredient ingredient = ingredientService.getIngredientById(id);
        //TODO: pass the ingredient info to the model and return it
        return "";
    }

    @PostMapping("/ingredient/{id}")
    public String newIngredient(@PathVariable Long id, @RequestParam Ingredient ingredient){
        ingredientService.newIngredient(ingredient);
        //TODO: return the correct view
        return "";
    }

    @PutMapping("/ingredient/{id}")
    public String substituteIngredient(@PathVariable Long id, @RequestParam Ingredient ingredient){
        ingredientService.substitute(id, ingredient);
        //TODO: return the correct view
        return "";
    }

    @PatchMapping("/ingredient/{id}")
    public String modifyIngredient(@PathVariable Long id, @RequestParam Ingredient ingredient){
        ingredientService.modifyToMatch(id, ingredient);
        //TODO: return the correct view
        return "";
    }

    @DeleteMapping("/ingredient/{id}")
    public String newIngredient(@PathVariable Long id){
        ingredientService.delete(id);
        //TODO: return the correct view
        return "";
    }
}
