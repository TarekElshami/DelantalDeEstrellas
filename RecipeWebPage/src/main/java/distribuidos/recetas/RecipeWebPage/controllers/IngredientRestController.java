package distribuidos.recetas.RecipeWebPage.controllers;

import distribuidos.recetas.RecipeWebPage.DTO.IngredientDTO;
import distribuidos.recetas.RecipeWebPage.entities.Ingredient;
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


    //Get All
    @GetMapping("/ingredients")
    public ResponseEntity<Collection<IngredientDTO>> getAllIngredients() {
        return ResponseEntity.ok(IngredientDTO.toDTO(ingredientService.getAll()));
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

    //Create Ingredient
    @PostMapping("/ingredient")
    public ResponseEntity<IngredientDTO> createIngredient(@RequestBody IngredientDTO ingredientDTO) {
        if(!ingredientService.isValidIngredient(ingredientDTO)) return ResponseEntity.badRequest().build();
        Ingredient ingredient = new Ingredient(ingredientDTO);
        //ingredient.setBestRecipes(recipeService.getRecipeById(ingredientDTO.getBestRecipes()));

        return ResponseEntity.status(201).body(new IngredientDTO(ingredientService.newIngredient(ingredient)));
    }

    @PutMapping("/ingredient/{id}")
    public ResponseEntity<IngredientDTO> substituteIngredient(@PathVariable Long id, @RequestBody IngredientDTO ingredientDTO){
        if(!ingredientService.isValidIngredient(ingredientDTO)) return ResponseEntity.badRequest().build();
        Ingredient ingredient = new Ingredient(ingredientDTO);
        //ingredient.setBestRecipes(recipeService.getRecipeById(ingredientDTO.getBestRecipes()));

        Ingredient updateIngredient = ingredientService.substitute(id, ingredient);
        if(updateIngredient == null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(new IngredientDTO(updateIngredient));
    }

    @PatchMapping("/ingredient/{id}")
    public ResponseEntity<IngredientDTO> modifyIngredient(@PathVariable Long id, @RequestBody IngredientDTO ingredientDTO){
        if (ingredientDTO.getBestRecipes() != null) return ResponseEntity.badRequest().build();
        if (ingredientDTO.getName()!=null && ingredientDTO.getName().isEmpty()) return ResponseEntity.badRequest().build();
        if (ingredientDTO.getDescription()!=null && ingredientDTO.getDescription().isEmpty()) return ResponseEntity.badRequest().build();
        if (ingredientDTO.getImage()!= null && ingredientDTO.getImage().isEmpty()) return ResponseEntity.badRequest().build();
        Ingredient existingIngredient = ingredientService.getIngredientById(id);
        if (existingIngredient == null) {
            return ResponseEntity.notFound().build();
        }
        Ingredient ingredient = new Ingredient(ingredientDTO);
        ingredientService.modifyToMatch(id, ingredient);
        return ResponseEntity.ok(new IngredientDTO(ingredientService.getIngredientById(id)));
    }

    @DeleteMapping("/ingredient/{id}")
    public ResponseEntity<IngredientDTO> deleteIngredient(@PathVariable Long id){
        if (ingredientService.getIngredientById(id) == null) {
            return ResponseEntity.notFound().build();
        }
        Ingredient deletedIngredient = ingredientService.delete(id);
        if (deletedIngredient==null) return ResponseEntity.status(403).build();

        return ResponseEntity.ok(new IngredientDTO(deletedIngredient));
    }
}
