package distribuidos.recetas.RecipeWebPage.controllers;

import distribuidos.recetas.RecipeWebPage.entities.Chef;
import distribuidos.recetas.RecipeWebPage.service.ChefService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


@Controller
public class ChefController {

    private ChefService chefService;

    public ChefController() {
        this.chefService = ChefService.getInstance();
    }

    @GetMapping("/chefs")
    public String showChefs(Model model){
        model.addAttribute("chef", chefService.getAll());
        return "ChefList";
    }

    @GetMapping("/chefs/new")
    public String newChefForm(Model model){
        model.addAttribute("url", "new");
        model.addAttribute("FormBtn", "Añadir");
        return "NewChef";
    }
    @PostMapping("/chefs/new")
    public String newChef(Chef chef){
        chefService.newChef(chef);
        return "redirect:/chefs";
    }
    @GetMapping("/chefs/{id}")
    public String showChef(@PathVariable Long id, Model model){
        Chef chef = chefService.getChefById(id);
        model.addAttribute("chef", chef);
        return "Chef";
    }

    @PostMapping("/chefs/{id}")
    public String newChef(@PathVariable Long id, @RequestParam Chef chef){
        chefService.newChef(chef);
        //TODO: return the correct view
        return "";
    }

    @GetMapping("/chefs/{id}/update")
    public String showChefEdit(@PathVariable Long id, Model model){
        Chef chef = chefService.getChefById(id);
        model.addAttribute("FormBtn", "Guardar cambios");
        model.addAttribute("url", id+"/update");
        model.addAttribute("chef", chef);
        return "NewChef";
    }

    @PostMapping("/chefs/{id}/update")
    public String editChef(@PathVariable Long id, Chef chef){
        chefService.substitute(id, chef);

        return "redirect:/chefs/"+id;
    }

    @GetMapping("/chefs/{id}/deleted")
    public String deleteChef(@PathVariable Long id){
        chefService.delete(id);
        return "redirect:/chefs";
    }


}
