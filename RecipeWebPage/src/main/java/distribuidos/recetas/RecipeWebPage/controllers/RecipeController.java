package distribuidos.recetas.RecipeWebPage.controllers;

import distribuidos.recetas.RecipeWebPage.entities.Recipe;
import distribuidos.recetas.RecipeWebPage.service.DatabaseInitializer;
import distribuidos.recetas.RecipeWebPage.service.RecipeService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Collection;


@Controller
public class RecipeController {

    private RecipeService recipeService;

    public RecipeController() {
        this.recipeService = RecipeService.getInstance();
        try {
            new DatabaseInitializer().init();
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @GetMapping("/recipes")
    public String showAllRecipes(Model model){
        Collection<Recipe> recipes = recipeService.getAll();
        model.addAttribute("recipeList", recipes);
        model.addAttribute("title", "Nuestras Recetas");
        model.addAttribute("subtitle", "La mejor selección de recetas de toda la web");
        model.addAttribute("headerImg", "recipesHeader.png");
        return "RecipeList";
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
