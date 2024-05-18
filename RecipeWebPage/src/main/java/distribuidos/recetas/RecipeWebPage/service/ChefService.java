package distribuidos.recetas.RecipeWebPage.service;

import distribuidos.recetas.RecipeWebPage.DTO.ChefDTO;
import distribuidos.recetas.RecipeWebPage.entities.Chef;

import distribuidos.recetas.RecipeWebPage.entities.Recipe;
import distribuidos.recetas.RecipeWebPage.repository.ChefRepository;
import jakarta.persistence.PreRemove;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class ChefService {

    public static final String DEFAULT_CHEF_NAME = "Anónimo";

    @Autowired
    private ChefRepository chefRepository;

    private int pageSize = 3;
    private Chef defaultChef;

    public Collection<Chef> getAll(){
        return chefRepository.findAll();
    }

    public List<Chef> getMost(int page){return chefRepository.findAll(PageRequest.of(page, pageSize)).getContent();};

    public Collection<Chef> getAll(int page){
        return chefRepository.findAll(PageRequest.of(page, pageSize)).getContent();
    }

    public int MaxPage(){
        return chefRepository.findAll(PageRequest.of(0,pageSize)).getTotalPages();
    }
    public Optional<Chef> getChefById(Long id) {
        return chefRepository.findById(id);
    }


    public Chef newChef(Chef chef) {
        if (DEFAULT_CHEF_NAME.equals(chef.getName())) return null;
        return chefRepository.save(chef);
    }

    public Chef substitute(Long id, Chef chef) {
        if (id.equals(getDefaultChef().getId())) return null;
        Optional<Chef> chef1 = chefRepository.findById(id);
        if(!chef1.isPresent()){
            return null;
        }
        chef.setId(id);
        chef.setBestRecipes(chef1.get().getBestRecipes());
        return chefRepository.save(chef);

    }

    public void modifyToMatch(Long id, Chef chef) {
        if (id.equals(getDefaultChef().getId())) return;
        Optional<Chef> storedChef1 = chefRepository.findById(id);
        Chef storedChef = new Chef();
        if (storedChef1.isPresent()){
            storedChef = storedChef1.get();
        }
        if (chef.getName() != null){
            storedChef.setName(chef.getName());
        } if (chef.getDescription() != null) {
            storedChef.setDescription(chef.getDescription());
        } if (chef.getImage() != null) {
            storedChef.setImage(chef.getImage());
        }
        storedChef.setId(id);
        chefRepository.save(storedChef);
    }

    public Chef delete(Long id) {
        if (id.equals(getDefaultChef().getId())) return null;


        Optional<Chef> chefOptional = chefRepository.findById(id);
        if (chefOptional.isPresent()) {
            Chef chef = chefOptional.get();
            Chef defaultChef1 = getDefaultChef();
            for (Recipe recipe : chef.getBestRecipes()){
                recipe.setChef(defaultChef1);
                defaultChef1.addRecipe(recipe);
                //Should not need to save it, since chef is the owning side
                //recipeService.save(recipe);
            }
            chefRepository.save(defaultChef1);
            chefRepository.delete(chef);
            return chef;
        } else {
            return null;
        }
    }



    public List<Chef> getRandomN(int n){
        List<Chef> allChefs = chefRepository.findAll();
        Collections.shuffle(allChefs);
        return allChefs.stream().limit(n).collect(Collectors.toList());
    }

    public boolean isValidChef(ChefDTO chef) {
        String name = chef.getName();
        String description = chef.getDescription();
        String image = chef.getImage();
        if (name == null || name.isEmpty() || name.equals(ChefService.DEFAULT_CHEF_NAME) ||
                description == null || description.isEmpty() ||
            image==null || image.isEmpty()) return false;
        if(chef.getBestRecipes() != null) return false;
        return true;
    }

    public Chef getDefaultChef(){
        if (defaultChef!=null) {
            return defaultChef;
        }
        Collection<Chef> chefsByName = chefRepository.findChefsByName(DEFAULT_CHEF_NAME);
        if (chefsByName.size() > 1) {throw new RuntimeException("Varios chef anonimos");}
        else if (chefsByName.size() == 1) {
            defaultChef = (Chef) chefsByName.toArray()[0];
            return defaultChef;
        }

        defaultChef = new Chef(DEFAULT_CHEF_NAME, "Este Chef se asigna cuando el creador de la receta prefiere permanecer anónimo, o bien la receta pertenecía a un Chef que ha sido borrado del sistema.", "/img/anonimousChef.jpg");
        //defaultChef.setId(-1L);
        chefRepository.save(defaultChef);

        return defaultChef;
    }

    public void save(Chef chef){
        chefRepository.save(chef);
    }

    public long count(){return chefRepository.count();}


}
