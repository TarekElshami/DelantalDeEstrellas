package distribuidos.recetas.RecipeWebPage.service;

import distribuidos.recetas.RecipeWebPage.entities.Recipe;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class RecipeService {

    public static final int PAGESIZE = 6;
    private final Map<Long, Recipe> recipeMap;
    private AtomicLong currentId = new AtomicLong(0);


    private RecipeService(){
        recipeMap = new LinkedHashMap<>();
    }

    public Collection<Recipe> getAll(){
        return recipeMap.values();
    }
    public Recipe getRecipeById(Long id) {
        return recipeMap.get(id);
    }

    public Collection<Recipe> getRecipeById(Collection<Long> ids) {
        List<Recipe> result = new ArrayList<>();
        if (ids==null || ids.isEmpty()) return result;
        for (Long id : ids) {
            Recipe recipe = recipeMap.get(id);
            if (recipe != null) {
                result.add(recipe);
            }
        }
        return result;
    }

    public Recipe newRecipe(Recipe recipe) {
        recipe.setId(currentId.incrementAndGet());
        recipeMap.put(recipe.getId(), recipe);
        return recipe;
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
            storedRecipe.setDescription(recipe.getDescription());
        } if (recipe.getIngredients() != null){
            storedRecipe.setIngredients(recipe.getIngredients());
        } if (recipe.getChef() != null){
            storedRecipe.setChef(recipe.getChef());
        } if (recipe.getSteps() != null){
            storedRecipe.setSteps(recipe.getSteps());
        } if (recipe.getImage() != null) {
            storedRecipe.setImage(recipe.getImage());
        }
        return storedRecipe;
    }

    public Recipe delete(Long id) {
        return recipeMap.remove(id);
    }

    public Collection<Recipe> getPage(int page) {
        return getPage(page, PAGESIZE);
    }

    public Collection<Recipe> getPage(int page, int pageSize) {
        ArrayList<Recipe> recipes = new ArrayList<>(recipeMap.values());
        if (recipes.size()<page*pageSize){
            return null;
        }
        return recipes.subList(page*pageSize, Math.min(recipes.size(), (page + 1) * pageSize));
    }

    public boolean isLastPage(int pageNum) {
        return pageNum == (Math.ceil((double) recipeMap.size() / (double) PAGESIZE)-1);
    }

    public Collection<Recipe> getHighlights(int num) {
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

    public List<Recipe> getFirst3(){
        int i, aux;
        List<Recipe> answer = new ArrayList<>();
        if (recipeMap.size() < 4)
            aux = recipeMap.size();
        else aux = 4;
        for (i=1;i<aux ;i++){
            answer.add(recipeMap.get((long) i));
        }
        return answer;
    }

    public boolean isValidRecipe(Recipe recipe) {
        String name = recipe.getName();
        String description = recipe.getDescription();
        if (name == null || name.isEmpty()) return false;
        if (description == null || description.isEmpty()) return false;
        return true;
    }
}
