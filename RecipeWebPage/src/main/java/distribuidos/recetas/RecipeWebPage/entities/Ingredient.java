package distribuidos.recetas.RecipeWebPage.entities;

import distribuidos.recetas.RecipeWebPage.DTO.IngredientDTO;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Collection;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Ingredient {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String description;
    private String image;
    @ManyToMany(mappedBy = "ingredients", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Collection<Recipe> bestRecipes;

    public Ingredient(String name, String description, String imageUrl) {
        this.name = name;
        this.description = description;
        this.image = imageUrl;
        bestRecipes = new ArrayList<>();
    }

    public Ingredient(IngredientDTO dto) {
        this.name = dto.getName();
        this.description = dto.getDescription();
        this.image = dto.getImage();
        bestRecipes = new ArrayList<>();
    }


    public void removeRecipe(Recipe recipe) {
        bestRecipes.remove(recipe);
    }
}
