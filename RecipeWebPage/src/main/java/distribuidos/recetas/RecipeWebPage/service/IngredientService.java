package distribuidos.recetas.RecipeWebPage.service;

import distribuidos.recetas.RecipeWebPage.entities.Ingredient;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

public class IngredientService {

    private static IngredientService ingredientService = null;
    private final Map<Long, Ingredient> ingredientMap;
    private AtomicLong currentId = new AtomicLong(0);

    public static IngredientService getInstance(){
        if (ingredientService==null){
            ingredientService = new IngredientService();
        }
        return ingredientService;
    }

    private IngredientService(){
        ingredientMap = new HashMap<>();
    }

    public Collection<Ingredient> getAll(){
        return ingredientMap.values();
    }

    public Ingredient getIngredientById(Long id) {
        return ingredientMap.get(id);
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
}
