package distribuidos.recetas.RecipeWebPage.controllers;

import distribuidos.recetas.RecipeWebPage.entities.Ingredient;
import distribuidos.recetas.RecipeWebPage.service.IngredientService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequestMapping("/api")
public class IngredientRestController {

    private IngredientService ingredientService;

    public IngredientRestController() {
        this.ingredientService = IngredientService.getInstance();
    }

    //Get All
    @GetMapping("/ingredients")
    public ResponseEntity<Collection<Ingredient>> getAllIngredients() {
        return ResponseEntity.ok(ingredientService.getAll());
    }

    //Create Ingredient
    @PostMapping("/ingredient")
    public ResponseEntity<Ingredient> createIngredient(@RequestBody Ingredient ingredient) {
        return ResponseEntity.status(201).body(ingredientService.newIngredient(ingredient));
    }

    //Get Ingredient by ID
    @GetMapping("/ingredient/{id}")
    public ResponseEntity<Ingredient> showIngredient(@PathVariable Long id){
        Ingredient ingredient = ingredientService.getIngredientById(id);
        if(ingredient == null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(ingredient);
    }

    @PutMapping("/ingredient/{id}")
    public ResponseEntity<Ingredient> substituteIngredient(@PathVariable Long id, @RequestBody Ingredient ingredient){
        Ingredient updateIngredient = ingredientService.substitute(id, ingredient);
        if(updateIngredient == null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(updateIngredient);
    }

    @PatchMapping("/ingredient/{id}")
    public String modifyIngredient(@PathVariable Long id, @RequestParam Ingredient ingredient){
        ingredientService.modifyToMatch(id, ingredient);
        //TODO: return the correct view
        return "";
    }

    @DeleteMapping("/ingredient/{id}")
    public ResponseEntity<Void> deleteIngredient(@PathVariable Long id){
        ingredientService.delete(id);
        return ResponseEntity.ok().build();
    }
}
