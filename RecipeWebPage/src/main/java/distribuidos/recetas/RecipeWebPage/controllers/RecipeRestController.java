package distribuidos.recetas.RecipeWebPage.controllers;

import distribuidos.recetas.RecipeWebPage.DTO.RecipeDTO;
import distribuidos.recetas.RecipeWebPage.entities.Ingredient;
import distribuidos.recetas.RecipeWebPage.entities.Recipe;
import distribuidos.recetas.RecipeWebPage.service.ChefService;
import distribuidos.recetas.RecipeWebPage.service.IngredientService;
import distribuidos.recetas.RecipeWebPage.service.RecipeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class RecipeRestController {

    @Autowired
    private RecipeService recipeService;
    @Autowired
    private ChefService chefService;
    @Autowired
    private IngredientService ingredientService;

    @GetMapping("/recipes")
    public ResponseEntity<Collection<RecipeDTO>> getAllRecipes(){
        return ResponseEntity.ok(RecipeDTO.toDTO(recipeService.getAll()));
    }
    @GetMapping("/recipe/{id}")
    public ResponseEntity<RecipeDTO> getRecipe(@PathVariable Long id){
        Optional<Recipe> recipeOpt = recipeService.getRecipeById(id);
        if (recipeOpt.isEmpty()){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(new RecipeDTO(recipeOpt.get()));
    }

    @PostMapping("/recipe")
    public ResponseEntity<RecipeDTO> newRecipe(@RequestBody RecipeDTO recipeDTO){
        Recipe recipe = new Recipe(recipeDTO);
        if(recipeDTO.getChef() != null) {
            if (chefService.getChefById(recipeDTO.getChef()).isEmpty()) return ResponseEntity.badRequest().build();
            recipe.setChef(chefService.getChefById(recipeDTO.getChef()).get());
        } else
            recipe.setChef(chefService.getDefaultChef());
        Collection<Ingredient> ingredients = ingredientService.getIngredientById(recipeDTO.getIngredients());
        if (ingredients==null || ingredients.isEmpty()){
            return ResponseEntity.badRequest().build();
        }
        recipe.setIngredients(ingredients);

        if (!recipeService.isValidRecipe(recipe)) return ResponseEntity.badRequest().build();
        return ResponseEntity.status(201).body(new RecipeDTO(recipeService.newRecipe(recipe)));
    }

    @PutMapping("/recipe/{id}")
    public ResponseEntity<RecipeDTO> substituteRecipe(@PathVariable Long id, @RequestBody RecipeDTO recipeDTO){
        if (recipeDTO.getIngredients()==null || recipeDTO.getIngredients().isEmpty()) return ResponseEntity.badRequest().build();
        Recipe recipe = new Recipe(recipeDTO);
        if(recipeDTO.getChef() != null) {
            if (chefService.getChefById(recipeDTO.getChef()).isEmpty()) return ResponseEntity.badRequest().build();
            recipe.setChef(chefService.getChefById(recipeDTO.getChef()).get());
        } else
            recipe.setChef(chefService.getDefaultChef());

        Collection<Ingredient> ingredients = ingredientService.getIngredientById(recipeDTO.getIngredients());
        if (ingredients==null || ingredients.isEmpty()){
            return ResponseEntity.badRequest().build();
        }
        recipe.setIngredients(ingredients);

        if (!recipeService.isValidRecipe(recipe)) return ResponseEntity.badRequest().build();

        Recipe substitute = recipeService.substitute(id, recipe);
        if (substitute == null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(new RecipeDTO(substitute));
    }

    @PatchMapping("/recipe/{id}")
    public ResponseEntity<RecipeDTO> modifyRecipe(@PathVariable Long id, @RequestBody RecipeDTO recipeDTO){
        if (recipeDTO.getName()!=null && recipeDTO.getName().isEmpty()) return ResponseEntity.badRequest().build();
        if (recipeDTO.getDescription()!=null && recipeDTO.getDescription().isEmpty()) return ResponseEntity.badRequest().build();
        if (recipeDTO.getSteps()!=null && recipeDTO.getSteps().isEmpty()) return ResponseEntity.badRequest().build();
        if (recipeDTO.getImage()!= null && recipeDTO.getImage().isEmpty()) return ResponseEntity.badRequest().build();
        if (recipeDTO.getIngredients()!=null && recipeDTO.getIngredients().isEmpty()) return ResponseEntity.badRequest().build();
        if (recipeDTO.getChef()!=null && chefService.getChefById(recipeDTO.getChef()).isEmpty()) return ResponseEntity.badRequest().build();
        //if (recipeDTO.getChef()==null) recipeDTO.setChef(1L);

        Recipe recipe = new Recipe(recipeDTO);
        if (recipeDTO.getChef()!=null)
            recipe.setChef(chefService.getChefById(recipeDTO.getChef()).get());
        if (recipeDTO.getIngredients() != null) {
            Collection<Ingredient> ingredients = ingredientService.getIngredientById(recipeDTO.getIngredients());
            if (ingredients==null || ingredients.isEmpty()){
                return ResponseEntity.badRequest().build();
            }
            recipe.setIngredients(ingredients);
        }


        Recipe storedRecipe = recipeService.modifyToMatch(id, recipe);
        if (storedRecipe == null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(new RecipeDTO(storedRecipe));
    }

    @DeleteMapping("/recipe/{id}")
    @ResponseBody
    public ResponseEntity<RecipeDTO> newRecipe(@PathVariable Long id){
        Optional<Recipe> recipeToDelete = recipeService.getRecipeById(id);
        if (recipeToDelete.isEmpty()) return ResponseEntity.notFound().build();
        List<String> fakeSteps = recipeToDelete.get().getSteps();
        List<String> steps = new ArrayList<>(fakeSteps);

        Recipe deletedRecipe = recipeService.delete(id);
        if (deletedRecipe == null) {
            return ResponseEntity.notFound().build();
        }
        deletedRecipe.setSteps(steps);
        return ResponseEntity.ok(new RecipeDTO(deletedRecipe));
    }
}
