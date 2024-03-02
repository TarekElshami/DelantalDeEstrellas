package distribuidos.recetas.RecipeWebPage.service;

import distribuidos.recetas.RecipeWebPage.entities.Chef;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class ChefService {

    private static ChefService chefService = null;
    private final Map<Long, Chef> chefMap;

    public static ChefService getInstance(){
        if (chefService==null){
            chefService = new ChefService();
        }
        return chefService;
    }
    private ChefService(){
        chefMap = new HashMap<>();
    }

    public Collection<Chef> getAll(){
        return chefMap.values();
    }
    public Chef getChefById(Long id) {
        return chefMap.get(id);
    }

    public void newChef(Chef chef) {
        chefMap.put(chef.getId(), chef);
    }

    public void substitute(Long id, Chef chef) {
        chefMap.put(id, chef);
    }

    public void modifyToMatch(Long id, Chef chef) {
        Chef storedChef = chefMap.get(id);
        if (chef.getName() != null){
            storedChef.setName(chef.getName());
        } if (chef.getDescription() != null){
            storedChef.setDescription(chef.getName());
        } if (chef.getFavIng() != null){
            storedChef.setFavIng(chef.getFavIng());
        } if (chef.getBestRecipes() != null){
            storedChef.setBestRecipes(chef.getBestRecipes());
        }
    }

    public Chef delete(Long id) {
        return chefMap.remove(id);
    }
}
