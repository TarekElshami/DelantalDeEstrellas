package distribuidos.recetas.RecipeWebPage.controllers;

import distribuidos.recetas.RecipeWebPage.entities.Recipe;
import distribuidos.recetas.RecipeWebPage.service.RecipeService;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<Recipe> showRecipe(@PathVariable Long id){
        Recipe recipe = recipeService.getRecipeById(id);
        //TODO: pass the recipe info to the model and return it
        return ResponseEntity.status(200).body(null);
    }

    @PostMapping("/recipe/{id}")
    public ResponseEntity<Recipe> newRecipe(@PathVariable Long id, @RequestParam Recipe recipe){
        recipeService.newRecipe(recipe);
        //TODO: return the correct view
        return ResponseEntity.status(200).body(null);
    }

    @PutMapping("/recipe/{id}")
    public ResponseEntity<Recipe> substituteRecipe(@PathVariable Long id, @RequestParam Recipe recipe){
        recipeService.substitute(id, recipe);
        //TODO: return the correct view
        return ResponseEntity.status(200).body(null);
    }

    @PatchMapping("/recipe/{id}")
    public ResponseEntity<Recipe> modifyRecipe(@PathVariable Long id, @RequestParam Recipe recipe){
        recipeService.modifyToMatch(id, recipe);
        //TODO: return the correct view
        return ResponseEntity.status(200).body(null);
    }

    @DeleteMapping("/recipe/{id}")
    public ResponseEntity<Recipe> newRecipe(@PathVariable Long id){
        recipeService.delete(id);
        //TODO: return the correct view
        return ResponseEntity.status(200).body(null);
    }
}
