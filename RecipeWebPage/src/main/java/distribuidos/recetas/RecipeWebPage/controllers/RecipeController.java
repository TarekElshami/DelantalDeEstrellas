package distribuidos.recetas.RecipeWebPage.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import distribuidos.recetas.RecipeWebPage.DTO.RecipeDTO;
import distribuidos.recetas.RecipeWebPage.entities.Chef;
import distribuidos.recetas.RecipeWebPage.entities.Ingredient;
import distribuidos.recetas.RecipeWebPage.entities.Recipe;
import distribuidos.recetas.RecipeWebPage.service.ChefService;
import distribuidos.recetas.RecipeWebPage.service.IngredientService;
import distribuidos.recetas.RecipeWebPage.service.RecipeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.*;


@Controller
public class RecipeController {

    @Autowired
    private RecipeService recipeService;
    @Autowired
    private IngredientService ingredientService;
    @Autowired
    private ChefService chefService;

    public RecipeController(){}

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
        if (recipe==null){
            model.addAttribute("errorMessage", "No existe una receta con la id indicada");
            return "Error";
        }
        Chef chef;
        if (recipe.getChef() != null) {
            chef = recipe.getChef();
        } else {
            chef = chefService.getEmptyChef();
        }
        model.addAttribute("recipe", recipe);
        model.addAttribute("chef", chef);
        model.addAttribute("showHighlightedRecipes", true);
        model.addAttribute("highlightedRecipes", recipeService.getHighlights(3));
        return "Recipe";
    }

    @GetMapping("/recipe/new")
    public String newRecipe(Model model) {
        model.addAttribute("chefs", chefService.getAll());
        return "RecipeForm";
    }

    @PostMapping("/recipe/new")
    public String newRecipeSend(@RequestBody RecipeDTO recipeDTO){
        Recipe recipe = new Recipe(recipeDTO);
        Optional<Chef> chef = chefService.getChefById(recipeDTO.getChef());
        Chef chef1;
        if (!chef.isPresent()){
            chef1 = chefService.getEmptyChef();
        } else{
            chef1 = chef.get();
        }
        recipe.setChef(chef1);
        recipe.setIngredients(ingredientService.getIngredientById(recipeDTO.getIngredients()));
        recipeService.newRecipe(recipe);
        return "redirect:/recipes";
    }

    @GetMapping("/recipe/{id}/update")
    public String updateRecipeView(Model model, @PathVariable Long id){
        Recipe recipe = recipeService.getRecipeById(id);
        model.addAttribute("recipe", recipe);
        //model.addAttribute("chef", recipe.getChef());
        //model.addAttribute("showHighlightedRecipes", true);
        //model.addAttribute("highlightedRecipes", recipeService.getHighlighs(3));
        model.addAttribute("chefs", chefService.getAll());
        //should call js updateStepIds when the file is rendered
        return "RecipeUpdateForm";
    }

    @PostMapping("/recipe/{id}/update")
    @ResponseBody
    public ResponseEntity<Recipe> updateRecipeSend(@PathVariable Long id, @RequestBody RecipeDTO recipeDTO){
        if (recipeService.getRecipeById(id)==null){
            return ResponseEntity.notFound().build();
        }
        Recipe recipe = new Recipe(recipeDTO);
        Optional<Chef> chef = chefService.getChefById(recipeDTO.getChef());
        Chef chef1;
        if (!chef.isPresent()){
            chef1 = chefService.getEmptyChef();
        } else {
            chef1 = chef.get();
        }
        recipe.setChef(chef1);
        recipe.setIngredients(ingredientService.getIngredientById(recipeDTO.getIngredients()));

        Recipe substitute = recipeService.substitute(id, recipe);
        if (substitute!= null) {
            return ResponseEntity.ok(recipe);
        } else {
            return ResponseEntity.notFound().build();
        }

    }

    @PostMapping("/recipe/{id}/delete")
    public String deleteRecipe(@PathVariable Long id){
        recipeService.delete(id);
        return "redirect:/recipes";
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
