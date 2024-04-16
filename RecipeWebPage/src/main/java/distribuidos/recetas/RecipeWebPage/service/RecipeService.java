package distribuidos.recetas.RecipeWebPage.service;

import distribuidos.recetas.RecipeWebPage.entities.Ingredient;
import distribuidos.recetas.RecipeWebPage.entities.Recipe;
import distribuidos.recetas.RecipeWebPage.repository.RecipeRepository;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class RecipeService {

    public static final int PAGESIZE = 6;
    @Autowired
    private RecipeRepository recipeRepository;
    private AtomicLong currentId = new AtomicLong(0);

    public Collection<Recipe> getAll(){
        return recipeRepository.findAll();
    }
    public Optional<Recipe> getRecipeById(Long id) {
        return recipeRepository.findById(id);
    }

    public Collection<Recipe> getRecipeById(Collection<Long> ids) {
        return recipeRepository.findAllById(ids);
    }

    public Recipe newRecipe(Recipe recipe) {
        recipeRepository.save(recipe);
        return recipe;
    }

    public Recipe substitute(Long id, Recipe recipe) {
        if (!recipeRepository.existsById(id)) {
            return null;
        }
        recipe.setId(id);

        recipeRepository.save(recipe);//(id, recipe);
        return recipe;

    }

    public Recipe modifyToMatch(Long id, Recipe recipe) {
        Optional<Recipe> storedRecipeOpt = recipeRepository.findById(id);
        if (storedRecipeOpt.isEmpty())
            return null;
        Recipe storedRecipe = storedRecipeOpt.get();
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
        Optional<Recipe> recipeOpt = recipeRepository.findById(id);
        if (recipeOpt.isEmpty()) {
            return null;
        }
        Recipe recipe = recipeOpt.get();

        recipe.getChef().deleteRecipeById(recipe.getId());
        //possibly save the chef, since recipe is not the owning side
        for (Ingredient ing : recipe.getIngredients()) {
            ing.removeRecipe(recipe);
            //in theory shouldn't need to save the ing, since Recipe should be the owning side
        }
        recipeRepository.deleteById(id);
        return recipe;
    }

    public Collection<Recipe> getPage(int page) {
        return getPage(page, PAGESIZE);
    }

    public Collection<Recipe> getPage(int page, int pageSize) {
        ArrayList<Recipe> recipes = new ArrayList<>(recipeRepository.findAll());
        if (recipes.size()<page*pageSize){
            return null;
        }
        return recipes.subList(page*pageSize, Math.min(recipes.size(), (page + 1) * pageSize));
    }

    public boolean isLastPage(int pageNum) {
        return pageNum == (Math.ceil((double) recipeRepository.count() / (double) PAGESIZE)-1);
    }

    public Collection<Recipe> getHighlights(int num) {
        Collection<Recipe> collection = new ArrayList<>();
        List<Recipe> values = new ArrayList<>(recipeRepository.findAll());
        Random rand = new Random();
        while (collection.size()!=num && collection.size()!=values.size()) {
            Recipe randomRecipe = values.get(rand.nextInt((int)recipeRepository.count()));
            if (!collection.contains(randomRecipe)){
                collection.add(randomRecipe);
            }
        }
        return collection;
    }

    public boolean isValidRecipe(Recipe recipe) {
        String name = recipe.getName();
        String description = recipe.getDescription();
        List<String> steps = recipe.getSteps();
        String image = recipe.getImage();
        if (name == null || name.isEmpty()) return false;
        if (description == null || description.isEmpty()) return false;
        if (steps==null || steps.isEmpty()) return false;
        if (image==null || image.isEmpty()) return false;
        return true;
    }

    public void save(Recipe recipe) {
        recipeRepository.save(recipe);
    }
}
