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
    public String showIngredient(Model model, @PathVariable Long id){
        Ingredient ingredient = ingredientService.getIngredientById(id);
        if(ingredient != null) {
            model.addAttribute("ingredient", ingredient);
            return "Ingredient";
        }
        return "Error";
    }

    @GetMapping("/ingredient/{id}/update")
    public String showEditForm(Model model, @PathVariable Long id){
        Ingredient ingredient = ingredientService.getIngredientById(id);
        if(ingredient == null) {
            return "Error";
        }
        model.addAttribute("ingredient", ingredient);
        return "IngredientForm";
    }

    @PostMapping("/ingredient/update")
    public String updateIngredient(Ingredient ingredient){
        ingredientService.substitute(ingredient.getId(), ingredient);
        return "redirect:/ingredients";
    }

}
