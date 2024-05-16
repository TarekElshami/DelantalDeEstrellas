package distribuidos.recetas.RecipeWebPage.DTO;

import distribuidos.recetas.RecipeWebPage.entities.Recipe;
import distribuidos.recetas.RecipeWebPage.entities.Ingredient;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class RecipeDTO {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;
    private String name;
    private String description;
    private List<String> steps;
    private String image;

    private Long chef;
    private Collection<Long> ingredients;

    public RecipeDTO(Recipe recipe) {
        this.id = recipe.getId().toString();
        this.name = recipe.getName();
        this.description = recipe.getDescription();
        this.steps = recipe.getSteps();
        this.image = recipe.getImage();
        this.chef = (recipe.getChef()!=null ? recipe.getChef().getId():null);
        this.ingredients = new ArrayList<>();
        for (Ingredient ingredient : recipe.getIngredients()) {
            if (ingredient!=null){
                this.ingredients.add(ingredient.getId());
            }
        }
    }

    public static Collection<RecipeDTO> toDTO(Collection<Recipe> recipes) {
        List<RecipeDTO> result = new ArrayList<>();
        for (Recipe recipe : recipes) {
            result.add(new RecipeDTO(recipe));
        }
        return result;
    }
}
