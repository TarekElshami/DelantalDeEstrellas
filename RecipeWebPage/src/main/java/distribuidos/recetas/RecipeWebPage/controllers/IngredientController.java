package distribuidos.recetas.RecipeWebPage.controllers;

import distribuidos.recetas.RecipeWebPage.entities.Ingredient;
import distribuidos.recetas.RecipeWebPage.service.IngredientService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;


@Controller
public class IngredientController {

    private IngredientService ingredientService;

    public IngredientController() {
        this.ingredientService = IngredientService.getInstance();
    }

    @GetMapping("/ingredients")
    public String showIngredients(Model model){
        Collection<Ingredient> ingredients = ingredientService.getAll();
        model.addAttribute("ingredientsList", ingredients);
        return "IngredientList";
    }

    @GetMapping("/newIngredient")
    public String showCreateForm(Model model) {
        model.addAttribute("ingredient", new Ingredient());
        return "IngredientForm";
    }

    @PostMapping("/newIngredient")
    public String saveIngredient(Ingredient ingredient) {
        ingredientService.newIngredient(ingredient);
        return "redirect:/ingredients";
    }

    @GetMapping("/ingredient/{id}")
    public String showIngredient(@PathVariable Long id){
        Ingredient ingredient = ingredientService.getIngredientById(id);
        //TODO: pass the ingredient info to the model and return it
        return "";
    }

    @PutMapping("/ingredient/{id}")
    public String substituteIngredient(@PathVariable Long id, @RequestParam Ingredient ingredient){
        ingredientService.substitute(id, ingredient);
        //TODO: return the correct view
        return "";
    }

    @PatchMapping("/ingredient/{id}")
    public String modifyIngredient(@PathVariable Long id, @RequestParam Ingredient ingredient){
        ingredientService.modifyToMatch(id, ingredient);
        //TODO: return the correct view
        return "";
    }

    @DeleteMapping("/ingredient/{id}")
    public String newIngredient(@PathVariable Long id){
        ingredientService.delete(id);
        //TODO: return the correct view
        return "";
    }
}
