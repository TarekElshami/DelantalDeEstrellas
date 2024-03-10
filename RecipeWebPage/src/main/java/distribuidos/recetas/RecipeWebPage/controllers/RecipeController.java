package distribuidos.recetas.RecipeWebPage.controllers;

import distribuidos.recetas.RecipeWebPage.entities.Recipe;
import distribuidos.recetas.RecipeWebPage.service.DatabaseInitializer;
import distribuidos.recetas.RecipeWebPage.service.RecipeService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;


@Controller
public class RecipeController {

    private RecipeService recipeService;

    public RecipeController() {
        this.recipeService = RecipeService.getInstance();
    }

    @GetMapping("/recipes")
    public String showAllRecipes(Model model){
        Collection<Recipe> recipes = recipeService.getPage(0);
        model.addAttribute("recipeList", recipes);
        model.addAttribute("title", "Nuestras Recetas");
        model.addAttribute("subtitle", "La mejor selección de recetas de toda la web");
        model.addAttribute("headerImg", "recipesHeader.png");
        model.addAttribute("showButton", recipes.size()==RecipeService.PAGESIZE);
        return "RecipeList";
    }
    @GetMapping("/recipe/{id}")
    public String showRecipe(Model model, @PathVariable Long id){
        Recipe recipe = recipeService.getRecipeById(id);
        model.addAttribute("recipe", recipe);
        model.addAttribute("chef", recipe.getChef());
        model.addAttribute("showHighlightedRecipes", true);
        model.addAttribute("highlightedRecipes", recipeService.getHighlighs(3));
        //TODO: pass the recipe info to the model and return it
        return "RecipeView";
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

    @GetMapping("/NextRecipePage")
    @ResponseBody
    public ResponseEntity<Collection<Recipe>> nextPage(@RequestParam("page") int pageNum){
        Collection<Recipe> page = recipeService.getPage(pageNum);
        HttpHeaders headers = new HttpHeaders();
        headers.add("X-Is-Last-Page", String.valueOf(recipeService.isLastPage(pageNum)));

        if (page==null){
            return new ResponseEntity<>(null, headers, 204); //204 No Content
        }
        return new ResponseEntity<>(page, headers, HttpStatus.OK);
    }
}
