package distribuidos.recetas.RecipeWebPage.controllers;

import distribuidos.recetas.RecipeWebPage.entities.Chef;
import distribuidos.recetas.RecipeWebPage.service.ChefService;
import distribuidos.recetas.RecipeWebPage.service.IngredientService;
import distribuidos.recetas.RecipeWebPage.service.RecipeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


@Controller
public class ChefController {

    @Autowired
    private ChefService chefService;
    @Autowired
    private RecipeService recipeService;
    @Autowired
    private IngredientService ingredientService;

    @GetMapping("/")
    public String home(){
        return "redirect:/home";
    }
    @GetMapping("/home")
    public String showHome(Model model){
        model.addAttribute("chefList", chefService.getHighlights(3));
        model.addAttribute("ingredientList", ingredientService.getFirst3());
        model.addAttribute("recipeList", recipeService.getHighlights(3));

        return "Index";
    }

    @GetMapping("/chefs")
    public String showChefs(Model model){
        model.addAttribute("chef", chefService.getAll());
        return "ChefList";
    }

    @GetMapping("/chef/new")
    public String newChefForm(Model model){
        model.addAttribute("url", "new");
        model.addAttribute("FormBtn", "Añadir");
        model.addAttribute("backBtn", "/chefs");
        model.addAttribute("title", "Crear chef");
        return "ChefForm";
    }
    @PostMapping("/chef/new")
    public String newChef(Chef chef){
        chefService.newChef(chef);
        return "redirect:/chefs";
    }
    @GetMapping("/chef/{id}")
    public String showChef(@PathVariable Long id, Model model){
        Chef chef = chefService.getChefById(id);
        if (chef == null){
            model.addAttribute("errorMessage", "No existe el chef con la id " +id);
            return "Error";
        }
        model.addAttribute("chef", chef);
        return "Chef";
    }

    @GetMapping("/chef/{id}/update")
    public String showChefEdit(@PathVariable Long id, Model model){
        Chef chef = chefService.getChefById(id);
        if (chef == null){
            model.addAttribute("errorMessage", "No existe el chef con la id " +id);
            return "Error";
        }
        model.addAttribute("FormBtn", "Guardar cambios");
        model.addAttribute("url", id+"/update");
        model.addAttribute("chef", chef);
        model.addAttribute("title", "Editar chef");
        model.addAttribute("recipeList", chef.getBestRecipes());
        model.addAttribute("backBtn", "/chef/"+id);
        return "ChefForm";
    }

    @PostMapping("/chef/{id}/update")
    public String editChef(@PathVariable Long id, Chef chef){
        chefService.substitute(id, chef);

        return "redirect:/chef/"+id;
    }

    @GetMapping("/chef/{id}/delete")
    public String deleteChef(@PathVariable Long id, Model model){
        Chef chef = chefService.getChefById(id);
        if (chef == null){
            model.addAttribute("errorMessage", "No existe el chef con la id " +id);
            return "Error";
        }
        chefService.delete(id);
        return "redirect:/chefs";
    }


}
