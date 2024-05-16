package distribuidos.recetas.RecipeWebPage.DTO;


import distribuidos.recetas.RecipeWebPage.entities.Ingredient;
import distribuidos.recetas.RecipeWebPage.entities.Recipe;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class IngredientDTO {


    private String id;
    private String name;
    private String description;
    private String image;

    private Collection<Long> bestRecipes;

    public IngredientDTO(Ingredient ingredient) {
        this.id = ingredient.getId().toString();
        this.name = ingredient.getName();
        this.description = ingredient.getDescription();
        this.image = ingredient.getImage();
        this.bestRecipes = new ArrayList<>();
        for (Recipe recipe : ingredient.getBestRecipes()) {
            if (recipe!=null) {
                this.bestRecipes.add(recipe.getId());
            }
        }
    }

    public static Collection<IngredientDTO> toDTO(Collection<Ingredient> ingredients) {
        List<IngredientDTO> result = new ArrayList<>();
        for (Ingredient ing : ingredients) {
            result.add(new IngredientDTO(ing));
        }
        return result;
    }
}
