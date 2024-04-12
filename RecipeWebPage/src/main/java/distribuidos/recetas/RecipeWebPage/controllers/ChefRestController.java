package distribuidos.recetas.RecipeWebPage.controllers;

import distribuidos.recetas.RecipeWebPage.DTO.ChefDTO;
import distribuidos.recetas.RecipeWebPage.entities.Chef;
import distribuidos.recetas.RecipeWebPage.entities.Ingredient;
import distribuidos.recetas.RecipeWebPage.service.ChefService;
import distribuidos.recetas.RecipeWebPage.service.IngredientService;
import distribuidos.recetas.RecipeWebPage.service.RecipeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.Optional;


@RestController
@RequestMapping("/api")
public class ChefRestController {

    @Autowired
    private ChefService chefService;
    @Autowired
    private IngredientService ingredientService;
    @Autowired
    private RecipeService recipeService;

    @GetMapping("/chefs")
    public Collection<ChefDTO> showChefs() {
        return ChefDTO.toDTO(chefService.getAll());
    }

    @GetMapping("/chef/{id}")
    public ResponseEntity<ChefDTO> showChef(@PathVariable Long id) {
        Optional<Chef> chef = chefService.getChefById(id);
        if (chef.isPresent()) {
            return ResponseEntity.status(200).body(new ChefDTO(chef.get()));
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping("/chef")
    public ResponseEntity<ChefDTO> newChef(@RequestBody ChefDTO chefDTO) {
        if (!chefService.isValidChef(chefDTO)) return ResponseEntity.badRequest().build();
        Chef chef = new Chef(chefDTO);
        chef.setBestRecipes(recipeService.getRecipeById(chefDTO.getBestRecipes()));

        return ResponseEntity.status(201).body(new ChefDTO(chefService.newChef(chef)));
    }

    @PutMapping("/chef/{id}")
    public ResponseEntity<ChefDTO> substituteChef(@PathVariable Long id, @RequestBody ChefDTO chefDTO) {
        Chef chef = new Chef(chefDTO);
        chef.setBestRecipes(recipeService.getRecipeById(chefDTO.getBestRecipes()));

        Chef updateChef = chefService.substitute(id, chef);
        if (updateChef == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(new ChefDTO((updateChef)));
    }

    @PatchMapping("/chef/{id}")
    public ResponseEntity<ChefDTO> modifyChef(@PathVariable Long id, @RequestBody ChefDTO chefDTO) {
        Optional<Chef> oldChef = chefService.getChefById(id);
        if (!oldChef.isPresent()) {
            return ResponseEntity.notFound().build();
        }
        Chef chef = new Chef(chefDTO);
        chef.setBestRecipes(recipeService.getRecipeById(chefDTO.getBestRecipes()));

        chefService.modifyToMatch(id, chef);
        return ResponseEntity.ok(new ChefDTO(chefService.getChefById(id).get()));
    }


    @DeleteMapping("/chef/{id}")
    public ResponseEntity<ChefDTO> deleteChef(@PathVariable Long id) {
        Chef deletedChef = chefService.delete(id);
        if (deletedChef == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(new ChefDTO(deletedChef));
    }
}
