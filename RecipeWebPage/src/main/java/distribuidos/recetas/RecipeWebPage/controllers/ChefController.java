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

    @GetMapping("/chef")
    public String showChefs(Model model){
        model.addAttribute("chef", chefService.getAll());
        return "ChefList";
    }

    @GetMapping("/chef/new")
    public String newChefForm(Model model){
        model.addAttribute("url", "new");
        model.addAttribute("FormBtn", "Añadir");
        return "NewChef";
    }
    @PostMapping("/chef/new")
    public String newChef(Chef chef){
        chefService.newChef(chef);
        return "redirect:/chef";
    }
    @GetMapping("/chef/{id}")
    public String showChef(@PathVariable Long id, Model model){
        Chef chef = chefService.getChefById(id);
        model.addAttribute("chef", chef);
        return "Chef";
    }

    @PostMapping("/chef/{id}")
    public String newChef(@PathVariable Long id, @RequestParam Chef chef){
        chefService.newChef(chef);
        //TODO: return the correct view
        return "";
    }

    @GetMapping("/chef/{id}/update")
    public String showChefEdit(@PathVariable Long id, Model model){
        Chef chef = chefService.getChefById(id);
        model.addAttribute("FormBtn", "Guardar cambios");
        model.addAttribute("url", id+"/update");
        model.addAttribute("chef", chef);
        return "NewChef";
    }

    @PostMapping("/chef/{id}/update")
    public String editChef(@PathVariable Long id, Chef chef){
        chefService.substitute(id, chef);

        return "redirect:/chef/"+id;
    }

    @GetMapping("/chef/{id}/deleted")
    public String deleteChef(@PathVariable Long id){
        chefService.delete(id);
        return "redirect:/chef";
    }


}
