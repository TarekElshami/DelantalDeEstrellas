package distribuidos.recetas.RecipeWebPage.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import distribuidos.recetas.RecipeWebPage.DTO.IngredientDTO;
import distribuidos.recetas.RecipeWebPage.service.IngredientService;
import distribuidos.recetas.RecipeWebPage.service.RecipeService;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Collection;

//@Entity
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

    private Collection<Recipe> bestRecipes = new ArrayList<>();

    public Ingredient(String name, String description, String imageUrl) {
        this.name = name;
        this.description = description;
        this.image = imageUrl;
    }

    public Ingredient(IngredientDTO dto) {
        this.name = dto.getName();
        this.description = dto.getDescription();
        this.image = dto.getImage();
    }


}
