package distribuidos.recetas.RecipeWebPage.controllers;

import distribuidos.recetas.RecipeWebPage.DTO.ChefDTO;
import distribuidos.recetas.RecipeWebPage.DTO.RecipeDTO;
import distribuidos.recetas.RecipeWebPage.entities.Chef;
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
        Optional<Recipe> recipeOpt = recipeService.getRecipeById(id);
        if (recipeOpt.isEmpty()){
            model.addAttribute("errorMessage", "No existe una receta con la id indicada");
            return "Error";
        }
        Recipe recipe = recipeOpt.get();
        Chef chef;
        if (recipe.getChef() != null) {
            chef = recipe.getChef();
        } else {
            chef = chefService.getDefaultChef();
        }
        model.addAttribute("recipe", recipe);
        model.addAttribute("chef", chef);
        model.addAttribute("showHighlightedRecipes", true);
        model.addAttribute("highlightedRecipes", recipeService.getHighlights(3));
        return "Recipe";
    }

    @GetMapping("/recipe/new")
    public String newRecipe(Model model, @RequestParam(value = "id", required = false) Long id) {
        if (id != null){
            System.out.println("hola");
            System.out.println(id);
            Optional<Chef> chefOptional = chefService.getChefById(id);
            if (chefOptional.isPresent()){
                model.addAttribute("chefs", chefOptional.get());
                model.addAttribute("url", "/chef/"+id);
            } else {
                model.addAttribute("errorMessage", "No existe el chef con la id " +id);
                return "Error";
            }


        }else{
            model.addAttribute("chefs", chefService.getAll());
            model.addAttribute("url", "/recipes");
            model.addAttribute("showAllOptions", "");
        }
        return "RecipeForm";
    }

    @PostMapping("/recipe/new")
    public String newRecipeSend(@RequestBody RecipeDTO recipeDTO, Model model){
        Recipe recipe = new Recipe(recipeDTO);
        Optional<Chef> chefOpt = chefService.getChefById(recipeDTO.getChef());
        if (chefOpt.isEmpty()){
            model.addAttribute("errorMessage", "El formato de la receta es incorrecto.");
            return "Error";
        }
        Chef chef = chefOpt.get();
        recipe.setIngredients(ingredientService.getIngredientById(recipeDTO.getIngredients()));
        recipe.setChef(chef);
        chef.addRecipe(recipe);

        chefService.save(chef);
        recipeService.newRecipe(recipe);
        return "redirect:/recipes";
    }

    @GetMapping("/recipe/{id}/update")
    public String updateRecipeView(Model model, @PathVariable Long id){

        Optional<Recipe> recipe = recipeService.getRecipeById(id);
        model.addAttribute("recipe", recipe.get()); //what if there is an error?
        //model.addAttribute("chef", recipe.getChef());
        //model.addAttribute("showHighlightedRecipes", true);
        //model.addAttribute("highlightedRecipes", recipeService.getHighlighs(3));
        model.addAttribute("chefs", ChefDTO.toDTO(chefService.getAll()));
        //should call js updateStepIds when the file is rendered
        return "RecipeUpdateForm";
    }

    @PostMapping("/recipe/{id}/update")
    @ResponseBody
    public ResponseEntity<Recipe> updateRecipeSend(@PathVariable Long id, @RequestBody RecipeDTO recipeDTO){
        if (recipeService.getRecipeById(id).isEmpty()){
            return ResponseEntity.notFound().build();
        }
        Recipe recipe = new Recipe(recipeDTO);
        Optional<Chef> chefOpt = chefService.getChefById(recipeDTO.getChef());
        if (chefOpt.isEmpty()){
            //as it stands now, a recipe should always have a chef, so if it doesn't, that's an error
            return ResponseEntity.internalServerError().build();
        }
        Chef chef = chefOpt.get();
        recipe.setChef(chef);
        recipe.setIngredients(ingredientService.getIngredientById(recipeDTO.getIngredients()));


        Recipe substitute = recipeService.substitute(id, recipe);
        if (substitute!= null) {
            substitute.getChef().deleteRecipeById(recipe.getId());
            chef.addRecipe(recipe);
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
    public ResponseEntity<Collection<RecipeDTO>> nextPage(@RequestParam("page") int pageNum){
        Collection<RecipeDTO> page = RecipeDTO.toDTO(recipeService.getPage(pageNum));
        HttpHeaders headers = new HttpHeaders();
        headers.add("X-Is-Last-Page", String.valueOf(recipeService.isLastPage(pageNum)));

        if (page==null || page.isEmpty()){
            return new ResponseEntity<>(null, headers, 204); //204 No Content
        }
        return new ResponseEntity<>(page, headers, HttpStatus.OK);
    }
}
