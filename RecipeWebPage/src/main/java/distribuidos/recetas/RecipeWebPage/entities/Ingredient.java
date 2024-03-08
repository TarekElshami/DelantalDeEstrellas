package distribuidos.recetas.RecipeWebPage.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

import java.util.Collection;

//@Entity
@Getter
@Setter
//@NoArgsConstructor
public class Ingredient {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String description;
    private String image;

    private Collection<Ingredient> matchesWith;
    private Collection<Recipe> bestRecipes;

    public Ingredient() {
    }

    public Ingredient(String name, String description, String imageUrl) {
        this.name = name;
        this.description = description;
        this.image = imageUrl;
    }
}
