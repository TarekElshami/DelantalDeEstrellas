package distribuidos.recetas.RecipeWebPage.controllers;

import distribuidos.recetas.RecipeWebPage.DTO.RecipeDTO;
import distribuidos.recetas.RecipeWebPage.entities.Ingredient;
import distribuidos.recetas.RecipeWebPage.entities.Recipe;
import distribuidos.recetas.RecipeWebPage.service.ChefService;
import distribuidos.recetas.RecipeWebPage.service.IngredientService;
import distribuidos.recetas.RecipeWebPage.service.RecipeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.List;

@RestController
@RequestMapping("/api/recipe")
public class RecipeRestController {

    @Autowired
    private RecipeService recipeService;
    @Autowired
    private ChefService chefService;
    @Autowired
    private IngredientService ingredientService;

    @GetMapping("/all")
    public ResponseEntity<Collection<Recipe>> getAllRecipes(){
        return ResponseEntity.ok(recipeService.getAll());
    }
    @GetMapping("/{id}")
    public ResponseEntity<RecipeDTO> getRecipe(@PathVariable Long id){
        Recipe recipe = recipeService.getRecipeById(id);
        if (recipe==null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(new RecipeDTO(recipe));
    }

    @PostMapping("/new")
    public ResponseEntity<RecipeDTO> newRecipe(@RequestBody RecipeDTO recipeDTO){
        if (recipeDTO.getName()==null || recipeDTO.getName().isEmpty()) return ResponseEntity.badRequest().build();
        if (recipeDTO.getDescription()==null || recipeDTO.getDescription().isEmpty()) return ResponseEntity.badRequest().build();
        Recipe recipe = new Recipe(recipeDTO);
        recipe.setChef(chefService.getChefById(recipeDTO.getChef()));
        recipe.setIngredients(ingredientService.getIngredientById(recipeDTO.getIngredients()));

        return ResponseEntity.status(201).body(new RecipeDTO(recipeService.newRecipe(recipe)));
    }

    @PutMapping("/{id}")
    public ResponseEntity<RecipeDTO> substituteRecipe(@PathVariable Long id, @RequestBody RecipeDTO recipeDTO){
        Recipe recipe = new Recipe(recipeDTO);
        recipe.setChef(chefService.getChefById(recipeDTO.getChef()));
        recipe.setIngredients(ingredientService.getIngredientById(recipeDTO.getIngredients()));
        if (!recipeService.isValidRecipe(recipe)) return ResponseEntity.badRequest().build();
        Recipe substitute = recipeService.substitute(id, recipe);
        if (substitute == null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(new RecipeDTO(substitute));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<RecipeDTO> modifyRecipe(@PathVariable Long id, @RequestBody RecipeDTO recipeDTO){
        if (recipeDTO.getName()!=null && recipeDTO.getName().isEmpty()) return ResponseEntity.badRequest().build();
        if (recipeDTO.getDescription()!=null && recipeDTO.getDescription().isEmpty()) return ResponseEntity.badRequest().build();
        Recipe recipe = new Recipe(recipeDTO);
        recipe.setChef(chefService.getChefById(recipeDTO.getChef()));
        recipe.setIngredients(ingredientService.getIngredientById(recipeDTO.getIngredients()));

        Recipe storedRecipe = recipeService.modifyToMatch(id, recipe);
        if (storedRecipe == null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(new RecipeDTO(storedRecipe));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<RecipeDTO> newRecipe(@PathVariable Long id){
        Recipe deletedRecipe = recipeService.delete(id);
        if (deletedRecipe == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(new RecipeDTO(deletedRecipe));
    }
}
