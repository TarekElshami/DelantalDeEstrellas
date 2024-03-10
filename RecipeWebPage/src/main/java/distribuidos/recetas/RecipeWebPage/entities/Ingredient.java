package distribuidos.recetas.RecipeWebPage.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import distribuidos.recetas.RecipeWebPage.service.IngredientService;
import distribuidos.recetas.RecipeWebPage.service.RecipeService;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Collection;

//@Entity
@Getter
@Setter
//@NoArgsConstructor
public class Ingredient {

    private static Long currentId = 0L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String description;
    private String image;

    private Collection<Long> matchesWith = new ArrayList<>();
    private Collection<Long> bestRecipes = new ArrayList<>();

    public Ingredient() {
    }

    public Ingredient(String name, String description, String imageUrl) {
        this.name = name;
        this.description = description;
        this.image = imageUrl;
        this.id = ++currentId;
    }

    @JsonIgnore
    public Collection<Ingredient> getMatchesWith() {
        Collection<Ingredient> ingMatchesWith = new ArrayList<>();
        IngredientService ingredientService = IngredientService.getInstance();
        for (Long id: matchesWith){
            ingMatchesWith.add(ingredientService.getIngredientById(id));
        }
        return ingMatchesWith;
    }

    public void setMatchesWith(Collection<Ingredient> matchesWith) {
        this.matchesWith.clear();
        for (Ingredient ing : matchesWith){
            this.matchesWith.add(ing.id);
        }
    }

    @JsonIgnore
    public Collection<Recipe> getBestRecipes() {
        Collection<Recipe> recipeBestRecipes = new ArrayList<>();
        RecipeService recipeService = RecipeService.getInstance();
        for (Long id: matchesWith){
            recipeBestRecipes.add(recipeService.getRecipeById(id));
        }
        return recipeBestRecipes;
    }

    public void setBestRecipes(Collection<Recipe> bestRecipes) {
        this.bestRecipes.clear();
        for (Recipe recipe : bestRecipes){
            this.bestRecipes.add(recipe.getId());
        }
    }

    public Collection<Long> getTrueMatchesWith() {
        return matchesWith;
    }

    public Collection<Long> getTrueBestRecipes() {
        return bestRecipes;
    }


}
