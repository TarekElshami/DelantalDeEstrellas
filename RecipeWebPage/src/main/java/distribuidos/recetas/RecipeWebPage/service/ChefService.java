package distribuidos.recetas.RecipeWebPage.service;

import distribuidos.recetas.RecipeWebPage.DTO.ChefDTO;
import distribuidos.recetas.RecipeWebPage.DTO.IngredientDTO;
import distribuidos.recetas.RecipeWebPage.entities.Chef;
import distribuidos.recetas.RecipeWebPage.entities.Recipe;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.atomic.AtomicLong;
@Service
public class ChefService {
    private final Map<Long, Chef> chefMap;

    AtomicLong nextId = new AtomicLong();

    private ChefService(){
        chefMap = new HashMap<>();
    }

    public Collection<Chef> getAll(){
        return chefMap.values();
    }
    public Chef getChefById(Long id) {
        return chefMap.get(id);
    }

    public Collection<Chef> getChefById(Collection<Long> ids) {
        List<Chef> result = new ArrayList<>();
        if (ids==null || ids.isEmpty()) return result;
        for (Long id : ids) {
            Chef chef = chefMap.get(id);
            if (chef != null) {
                result.add(chef);
            }
        }
        return result;
    }

    public Chef newChef(Chef chef) {
        long id = nextId.incrementAndGet();
        chef.setId(id);
        chefMap.put(id, chef);
        return chef;
    }

    public Chef substitute(Long id, Chef chef) {

        if(!chefMap.containsKey(id)){
            return null;
        }
        chef.setId(id);
        chefMap.put(id, chef);
        return chef;

    }

    public void modifyToMatch(Long id, Chef chef) {
        Chef storedChef = chefMap.get(id);
        if (chef.getName() != null){
            storedChef.setName(chef.getName());
        } if (chef.getDescription() != null) {
            storedChef.setDescription(chef.getDescription());
        } if (chef.getImage() != null) {
            storedChef.setImage(chef.getImage());
            //} if (chef.getFavIng() != null){
            //    storedChef.setFavIng(chef.getFavIng());
            //} if (chef.getBestRecipes() != null){
            //    storedChef.setBestRecipes(chef.getBestRecipes());
        }
        chefMap.put(id,storedChef);
    }

    public Chef delete(Long id) {
        return chefMap.remove(id);
    }

    public List<Chef> getFirst3(){
        int i, aux;
        List<Chef> answer = new ArrayList<>();
        if (chefMap.size() < 4)
            aux = chefMap.size();
        else aux = 4;
        for (i=1;i<aux ;i++){
            answer.add(chefMap.get((long) i));
        }
        return answer;
    }

    public Collection<Chef> getHighlights(int num) {
        Collection<Chef> collection = new ArrayList<>();
        List<Chef> values = new ArrayList<>(chefMap.values());
        Random rand = new Random();
        while (collection.size()!=num && collection.size()!=values.size()) {
            Chef randomChef = values.get(rand.nextInt(chefMap.size()));
            if (!collection.contains(randomChef)){
                collection.add(randomChef);
            }
        }
        return collection;
    }

    public boolean isValidChef(ChefDTO chef) {
        String name = chef.getName();
        String description = chef.getDescription();
        if (name == null || name.isEmpty() ||
                description == null || description.isEmpty()) return false;
        return true;
    }
}
