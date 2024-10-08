package distribuidos.recetas.RecipeWebPage.controllers;

import distribuidos.recetas.RecipeWebPage.entities.Ingredient;
import distribuidos.recetas.RecipeWebPage.service.IngredientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Collection;


@Controller
public class IngredientController {

    @Autowired
    private IngredientService ingredientService;


    @GetMapping("/ingredients")
    public String showIngredients(Model model){
        Collection<Ingredient> ingredients = ingredientService.getAll();
        model.addAttribute("ingredientsList", ingredients);
        return "IngredientList";
    }

    @GetMapping("/ingredient/new")
    public String showCreateForm(Model model) {
        model.addAttribute("ingredient", new Ingredient());
        return "IngredientForm";
    }

    @PostMapping("/ingredient/new")
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
        String mensajeDeError = "No se puede ver el ingrediente de ID " + id + " porque no existe.";
        model.addAttribute("errorMessage", mensajeDeError);
        return "Error";
    }

    @GetMapping("/ingredient/{id}/update")
    public String showEditForm(Model model, @PathVariable Long id){
        Ingredient ingredient = ingredientService.getIngredientById(id);
        if(ingredient == null) {
            String mensajeDeError = "No se puede editar el ingrediente de ID " + id + " porque no existe.";
            model.addAttribute("errorMessage", mensajeDeError);
            return "Error";
        }
        model.addAttribute("ingredient", ingredient);
        return "IngredientForm";
    }

    @PostMapping("/ingredient/{id}/update")
    public String updateIngredient(@PathVariable Long id, Ingredient ingredient){
        if(ingredientService.substitute(id, ingredient) == null) return "Error";
        return "redirect:/ingredient/" + ingredient.getId();
    }

    @PostMapping("/ingredient/{id}/delete")
    public String deleteIngredient(@PathVariable Long id, RedirectAttributes redirectAttributes){
        if (ingredientService.delete(id) == null){
            String msg = "No se puede borrar este ingrediente ya que se está usando en al menos una receta.";
            redirectAttributes.addFlashAttribute("errorMessage",msg);
            return "redirect:/Error"; //Can't delete that ingredient, because it has associated recipes
        }
        return "redirect:/ingredients";
    }

    @GetMapping("/Error")
    public String error(){
        return "Error";
    }

    @ResponseBody
    @GetMapping("/IsIngredientBeingUsed")
    public ResponseEntity<Boolean> isIngredientBeingUsed(@RequestParam("id") Long id){
        Ingredient ing = ingredientService.getIngredientById(id);
        if (ing.getBestRecipes()==null || ing.getBestRecipes().isEmpty()){
            return ResponseEntity.ok(false);
        }
        return ResponseEntity.ok(true);
    }
}
