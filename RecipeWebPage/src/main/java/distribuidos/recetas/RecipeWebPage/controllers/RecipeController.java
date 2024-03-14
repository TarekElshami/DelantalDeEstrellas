package distribuidos.recetas.RecipeWebPage.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
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

    @GetMapping("/")
    public String home(){
        return "redirect:/home";
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
        if (recipe==null){
            model.addAttribute("errorMessage", "No existe una receta con la id indicada");
            return "Error";
        }
        model.addAttribute("recipe", recipe);
        model.addAttribute("chef", recipe.getChef()!=null ? recipe.getChef() : new Chef("Sin Chef", "Este Chef no existe", "noImg"));
        model.addAttribute("showHighlightedRecipes", true);
        model.addAttribute("highlightedRecipes", recipeService.getHighlighs(3));
        return "Recipe";
    }

    @GetMapping("/recipe/newRecipe")
    public String newRecipe(Model model) {
        model.addAttribute("chefs", chefService.getAll());
        return "RecipeForm";
    }

    @PostMapping("/recipe/newRecipe")
    public String newRecipeSend(@RequestBody String body){
        try {
            ObjectMapper objectMapper = new ObjectMapper();

            @SuppressWarnings("unchecked")
            Map<String, Object> anonRecipe = objectMapper.readValue(body, Map.class);

            // Access attributes
            String name = (String) anonRecipe.get("name");
            String description = (String) anonRecipe.get("description");
            String image = (String) anonRecipe.get("image");
            List<String> stepsList = (List<String>) anonRecipe.get("steps");

            List<String> ingredientsIdList = (List<String>) anonRecipe.get("ingredients");
            String chefString = (String) anonRecipe.get("chef");
            Chef chef;
            if ("noChef".equals(chefString)){
                chef = null;
            } else {
                Long chefId = Long.parseLong( (String) anonRecipe.get("chef"));
                chef = chefService.getChefById(chefId);
            }

            List<Ingredient> ingredients = new ArrayList<>();
            for (String ingId : ingredientsIdList){
                ingredients.add(ingredientService.getIngredientById(Long.parseLong(ingId)));
            }

            Recipe recipe = new Recipe(name, description, chef, stepsList, image, ingredients);

            recipeService.newRecipe(recipe);
        } catch (Exception e) {
            System.out.println("A string that was expected to be JSON was not JSON");
            e.printStackTrace();
        }
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
    public ResponseEntity<Recipe> updateRecipeSend(@PathVariable Long id, @RequestBody String body){
        if (recipeService.getRecipeById(id)==null){
            return ResponseEntity.notFound().build();
        }
        try { //should be replaced with a DTO
            ObjectMapper objectMapper = new ObjectMapper();

            @SuppressWarnings("unchecked")
            Map<String, Object> anonRecipe = objectMapper.readValue(body, Map.class);

            // Access attributes
            String name = (String) anonRecipe.get("name");
            String description = (String) anonRecipe.get("description");
            String image = (String) anonRecipe.get("image");
            List<String> stepsList = (List<String>) anonRecipe.get("steps");

            List<String> ingredientsIdList = (List<String>) anonRecipe.get("ingredients");
            String chefString = (String) anonRecipe.get("chef");
            Chef chef;
            if (!"noChef".equals(chefString)){
                chef = null;
            } else {
                Long chefId = Long.parseLong( (String) anonRecipe.get("chef"));
                chef = chefService.getChefById(chefId);
            }


            List<Ingredient> ingredients = new ArrayList<>();
            for (String ingId : ingredientsIdList){
                ingredients.add(ingredientService.getIngredientById(Long.parseLong(ingId)));
            }

            Recipe recipe = new Recipe(name, description, chef, stepsList, image, ingredients);

            Recipe substitute = recipeService.substitute(id, recipe);
            if (substitute!= null) {
                return ResponseEntity.ok(recipe);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            System.out.println("A string that was expected to be JSON was not JSON");
            e.printStackTrace();
            return ResponseEntity.internalServerError().build();
        }
    }

    //@PatchMapping("/recipe/{id}")
    //public String modifyRecipe(@PathVariable Long id, @RequestParam Recipe recipe){
    //    recipeService.modifyToMatch(id, recipe);
    //    //TODO: return the correct view
    //    return "";
    //}

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
