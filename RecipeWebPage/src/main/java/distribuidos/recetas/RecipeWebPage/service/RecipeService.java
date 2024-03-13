package distribuidos.recetas.RecipeWebPage.service;

import distribuidos.recetas.RecipeWebPage.entities.Recipe;

import java.util.*;
import java.util.concurrent.atomic.AtomicLong;

public class RecipeService {

    public static final int PAGESIZE = 6;
    private static RecipeService recipeService = null;
    private final Map<Long, Recipe> recipeMap;
    private AtomicLong currentId = new AtomicLong(0);

    public static RecipeService getInstance(){
        if (recipeService==null){
            recipeService = new RecipeService();
        }
        return recipeService;
    }
    private RecipeService(){
        recipeMap = new LinkedHashMap<>();
    }

    public Collection<Recipe> getAll(){
        return recipeMap.values();
    }
    public Recipe getRecipeById(Long id) {
        return recipeMap.get(id);
    }

    public void newRecipe(Recipe recipe) {
        recipe.setId(currentId.incrementAndGet());
        recipeMap.put(recipe.getId(), recipe);
    }

    public Recipe substitute(Long id, Recipe recipe) {
        if (!recipeMap.containsKey(id)) {
            return null;
        }
        recipe.setId(id);
        recipeMap.put(id, recipe);
        return recipe;

    }

    public Recipe modifyToMatch(Long id, Recipe recipe) {
        Recipe storedRecipe = recipeMap.get(id);
        if (storedRecipe==null)
            return null;
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
        return storedRecipe;
    }

    public Recipe delete(Long id) {
        return recipeMap.remove(id);
    }

    public Collection<Recipe> getPage(int page) {
        ArrayList<Recipe> recipes = new ArrayList<>(recipeMap.values());
        if (recipes.size()<page*PAGESIZE){
            return null;
        }
        return recipes.subList(page*PAGESIZE, Math.min(recipes.size(), (page + 1) * PAGESIZE));
    }

    public boolean isLastPage(int pageNum) {
        return pageNum == (Math.ceil((double) recipeMap.size() / (double) PAGESIZE)-1);
    }

    public Collection<Recipe> getHighlighs(int num) {
        Collection<Recipe> collection = new ArrayList<>();
        List<Recipe> values = new ArrayList<>(recipeMap.values());
        Random rand = new Random();
        while (collection.size()!=num && collection.size()!=values.size()) {
            Recipe randomRecipe = values.get(rand.nextInt(recipeMap.size()));
            if (!collection.contains(randomRecipe)){
                collection.add(randomRecipe);
            }
        }
        return collection;

    }
}
