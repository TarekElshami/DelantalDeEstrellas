package distribuidos.recetas.RecipeWebPage.service;

import distribuidos.recetas.RecipeWebPage.DTO.IngredientDTO;
import distribuidos.recetas.RecipeWebPage.entities.Chef;
import distribuidos.recetas.RecipeWebPage.entities.Ingredient;
import distribuidos.recetas.RecipeWebPage.entities.Recipe;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.atomic.AtomicLong;
@Service
public class IngredientService {

    private final Map<Long, Ingredient> ingredientMap;
    private AtomicLong currentId = new AtomicLong(0);

    private IngredientService(){
        ingredientMap = new HashMap<>();
    }

    public Collection<Ingredient> getAll(){
        return ingredientMap.values();
    }

    public Ingredient getIngredientById(Long id) {
        return ingredientMap.get(id);
    }

    public Collection<Ingredient> getIngredientById(Collection<Long> ids) {
        List<Ingredient> result = new ArrayList<>();
        if (ids==null || ids.isEmpty()) return result;
        for (Long id : ids) {
            Ingredient ing = ingredientMap.get(id);
            if (ing!=null) {
                result.add(ing);
            }
        }
        return result;
    }

    public Ingredient newIngredient(Ingredient ingredient) {
        long id = currentId.incrementAndGet();
        ingredient.setId(id);
        ingredientMap.put(ingredient.getId(), ingredient);
        return ingredient;
    }

    public Ingredient substitute(Long id, Ingredient ingredient) {
        if(!ingredientMap.containsKey(id)){
            return null;
        }
        ingredient.setId(id);
        ingredientMap.put(id, ingredient);
        return ingredient;
    }

    public void modifyToMatch(Long id, Ingredient ingredient) {
        Ingredient storedIngredient = ingredientMap.get(id);
        if (ingredient.getName() != null){
            storedIngredient.setName(ingredient.getName());
        }
        if (ingredient.getDescription() != null){
            storedIngredient.setDescription(ingredient.getDescription());
        }
        if(ingredient.getImage() != null){
            storedIngredient.setImage(ingredient.getImage());
        }
        ingredientMap.put(id, storedIngredient);
        /*if (ingredient.getMatchesWith() != null){
            storedIngredient.setMatchesWith(ingredient.getMatchesWith());
        }
        if (ingredient.getBestRecipes() != null){
            storedIngredient.setBestRecipes(ingredient.getBestRecipes());
        }*/
    }

    public Ingredient delete(Long id) {
        return ingredientMap.remove(id);
    }

    public List<Ingredient> getFirst3(){
        int i, aux;
        List<Ingredient> answer = new ArrayList<>();
        if (ingredientMap.size() < 4)
            aux = ingredientMap.size();
        else aux = 4;
        for (i=1;i<aux ;i++){
            answer.add(ingredientMap.get((long) i));
        }
        return answer;
    }

    public boolean isValidRecipe(IngredientDTO ingredient) {
        String name = ingredient.getName();
        String description = ingredient.getDescription();
        String image = ingredient.getImage();
        if (name == null || name.isEmpty() ||
            description == null || description.isEmpty() ||
            image == null || image.isEmpty()
        ) return false;
        return true;
    }

    public Collection<Ingredient> getHighlights(int num) {
        Collection<Ingredient> collection = new ArrayList<>();
        List<Ingredient> values = new ArrayList<>(ingredientMap.values());
        Random rand = new Random();
        while (collection.size()!=num && collection.size()!=values.size()) {
            Ingredient randomIngredient = values.get(rand.nextInt(ingredientMap.size()));
            if (!collection.contains(randomIngredient)){
                collection.add(randomIngredient);
            }
        }
        return collection;
    }
}
