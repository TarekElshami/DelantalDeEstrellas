package distribuidos.recetas.RecipeWebPage.service;

import distribuidos.recetas.RecipeWebPage.entities.Recipe;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class RecipeService {

    private static RecipeService recipeService = null;
    private final Map<Long, Recipe> recipeMap;

    public static RecipeService getInstance(){
        if (recipeService==null){
            recipeService = new RecipeService();
        }
        return recipeService;
    }
    private RecipeService(){
        recipeMap = new HashMap<>();
    }

    public Collection<Recipe> getAll(){
        return recipeMap.values();
    }
    public Recipe getRecipeById(Long id) {
        return recipeMap.get(id);
    }

    public void newRecipe(Recipe recipe) {
        recipeMap.put(recipe.getId(), recipe);
    }

    public void substitute(Long id, Recipe recipe) {
        recipeMap.put(id, recipe);
    }

    public void modifyToMatch(Long id, Recipe recipe) {
        Recipe storedRecipe = recipeMap.get(id);
        if (recipe.getName() != null){
            storedRecipe.setName(recipe.getName());
        } if (recipe.getDescription() != null){
            storedRecipe.setDescription(recipe.getName());
        } if (recipe.getIngredients() != null){
            storedRecipe.setIngredients(recipe.getIngredients());
        } if (recipe.getChef() != null){
            storedRecipe.setChef(recipe.getChef());
        } if (recipe.getSteps() != null){
            storedRecipe.setSteps(recipe.getSteps());
        }
    }

    public Recipe delete(Long id) {
        return recipeMap.remove(id);
    }
}
