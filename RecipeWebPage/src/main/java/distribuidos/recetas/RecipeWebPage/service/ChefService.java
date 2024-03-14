package distribuidos.recetas.RecipeWebPage.service;

import distribuidos.recetas.RecipeWebPage.entities.Chef;
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

    public void newChef(Chef chef) {
        long id = nextId.incrementAndGet();
        chef.setId(id);
        chefMap.put(id, chef);
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
}
