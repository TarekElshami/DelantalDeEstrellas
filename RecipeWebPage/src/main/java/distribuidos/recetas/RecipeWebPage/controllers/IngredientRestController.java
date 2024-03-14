package distribuidos.recetas.RecipeWebPage.controllers;

import distribuidos.recetas.RecipeWebPage.DTO.IngredientDTO;
import distribuidos.recetas.RecipeWebPage.entities.Chef;
import distribuidos.recetas.RecipeWebPage.entities.Ingredient;
import distribuidos.recetas.RecipeWebPage.entities.Recipe;
import distribuidos.recetas.RecipeWebPage.service.IngredientService;
import distribuidos.recetas.RecipeWebPage.service.RecipeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequestMapping("/api")
public class IngredientRestController {

    @Autowired
    private IngredientService ingredientService;
    @Autowired
    private RecipeService recipeService;


    //Get All
    @GetMapping("/ingredients")
    public ResponseEntity<Collection<Ingredient>> getAllIngredients() {
        return ResponseEntity.ok(ingredientService.getAll());
    }

    //Create Ingredient
    @PostMapping("/ingredient")
    public ResponseEntity<IngredientDTO> createIngredient(@RequestBody IngredientDTO ingredientDTO) {
        Ingredient ingredient = new Ingredient(ingredientDTO);
        ingredient.setBestRecipes(recipeService.getRecipeById(ingredientDTO.getBestRecipes()));
        ingredient.setMatchesWith(ingredientService.getIngredientById(ingredientDTO.getMatchesWith()));

        return ResponseEntity.status(201).body(new IngredientDTO(ingredientService.newIngredient(ingredient)));
    }

    //Get Ingredient by ID
    @GetMapping("/ingredient/{id}")
    public ResponseEntity<IngredientDTO> showIngredient(@PathVariable Long id){
        Ingredient ingredient = ingredientService.getIngredientById(id);
        if(ingredient == null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(new IngredientDTO(ingredient));
    }

    @PutMapping("/ingredient/{id}")
    public ResponseEntity<IngredientDTO> substituteIngredient(@PathVariable Long id, @RequestBody IngredientDTO ingredientDTO){
        Ingredient ingredient = new Ingredient(ingredientDTO);
        ingredient.setBestRecipes(recipeService.getRecipeById(ingredientDTO.getBestRecipes()));
        ingredient.setMatchesWith(ingredientService.getIngredientById(ingredientDTO.getMatchesWith()));

        Ingredient updateIngredient = ingredientService.substitute(id, ingredient);
        if(updateIngredient == null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(new IngredientDTO(updateIngredient));
    }

    @PatchMapping("/ingredient/{id}")
    public ResponseEntity<IngredientDTO> modifyIngredient(@PathVariable Long id, @RequestBody IngredientDTO ingredientDTO){
        Ingredient existingIngredient = ingredientService.getIngredientById(id);
        if (existingIngredient == null) {
            return ResponseEntity.notFound().build();
        }
        Ingredient ingredient = new Ingredient(ingredientDTO);
        ingredient.setBestRecipes(recipeService.getRecipeById(ingredientDTO.getBestRecipes()));
        ingredient.setMatchesWith(ingredientService.getIngredientById(ingredientDTO.getMatchesWith()));

        ingredientService.modifyToMatch(id, ingredient);
        return ResponseEntity.ok(new IngredientDTO(ingredientService.getIngredientById(id)));
    }

    @DeleteMapping("/ingredient/{id}")
    public ResponseEntity<IngredientDTO> deleteIngredient(@PathVariable Long id){
        Ingredient deletedIngredient = ingredientService.delete(id);
        if (deletedIngredient == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(new IngredientDTO(deletedIngredient));
    }
}
