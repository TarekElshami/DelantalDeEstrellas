package distribuidos.recetas.RecipeWebPage.controllers;

import distribuidos.recetas.RecipeWebPage.entities.Recipe;
import distribuidos.recetas.RecipeWebPage.service.RecipeService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class RecipeRestController {

    private RecipeService recipeService;

    public RecipeRestController() {
        this.recipeService = RecipeService.getInstance();
    }

    @GetMapping("/recipe/{id}")
    public String showRecipe(@PathVariable Long id){
        Recipe recipe = recipeService.getRecipeById(id);
        //TODO: pass the recipe info to the model and return it
        return "";
    }

    @PostMapping("/recipe/{id}")
    public String newRecipe(@PathVariable Long id, @RequestParam Recipe recipe){
        recipeService.newRecipe(recipe);
        //TODO: return the correct view
        return "";
    }

    @PutMapping("/recipe/{id}")
    public String substituteRecipe(@PathVariable Long id, @RequestParam Recipe recipe){
        recipeService.substitute(id, recipe);
        //TODO: return the correct view
        return "";
    }

    @PatchMapping("/recipe/{id}")
    public String modifyRecipe(@PathVariable Long id, @RequestParam Recipe recipe){
        recipeService.modifyToMatch(id, recipe);
        //TODO: return the correct view
        return "";
    }

    @DeleteMapping("/recipe/{id}")
    public String newRecipe(@PathVariable Long id){
        recipeService.delete(id);
        //TODO: return the correct view
        return "";
    }
}
