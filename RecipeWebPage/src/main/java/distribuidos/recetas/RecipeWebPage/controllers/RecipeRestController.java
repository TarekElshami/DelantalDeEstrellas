package distribuidos.recetas.RecipeWebPage.controllers;

import distribuidos.recetas.RecipeWebPage.entities.Recipe;
import distribuidos.recetas.RecipeWebPage.service.RecipeService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.List;

@RestController
@RequestMapping("/api/recipe")
public class RecipeRestController {

    private RecipeService recipeService;

    public RecipeRestController() {
        this.recipeService = RecipeService.getInstance();
    }

    @GetMapping("/all")
    public ResponseEntity<Collection<Recipe>> getAllRecipes(){
        return ResponseEntity.ok(recipeService.getAll());
    }
    @GetMapping("/{id}")
    public ResponseEntity<Recipe> getRecipe(@PathVariable Long id){
        Recipe recipe = recipeService.getRecipeById(id);
        if (recipe==null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(recipe);
    }

    @PostMapping("/new")
    public ResponseEntity<Recipe> newRecipe(@RequestBody Recipe recipe){
        recipeService.newRecipe(recipe);
        return ResponseEntity.status(201).body(recipe);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Recipe> substituteRecipe(@PathVariable Long id, @RequestParam Recipe recipe){
        if (recipeService.substitute(id, recipe) == null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(null);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Recipe> modifyRecipe(@PathVariable Long id, @RequestParam Recipe recipe){
        Recipe storedRecipe = recipeService.modifyToMatch(id, recipe);
        if (storedRecipe == null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(storedRecipe);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Recipe> newRecipe(@PathVariable Long id){
        Recipe deletedRecipe = recipeService.delete(id);
        if (deletedRecipe == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(deletedRecipe);
    }
}
