package distribuidos.recetas.RecipeWebPage.entities;

import distribuidos.recetas.RecipeWebPage.DTO.RecipeDTO;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

//@Entity
@Getter
@Setter
//@NoArgsConstructor
public class Recipe {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String description;
    private List<String> steps;
    private boolean complete; //true if the recipe is complete. Since one can be created with only its name, this attribute is used to know if it is complete enough to show.
    //private Blob image;
    private String image;

    private Collection<Ingredient> ingredients;
    private Chef chef;

    public Recipe(String name){
        this.name = name;
    }
    public Recipe(String name, String description, Chef chef, List<String> steps, String image, Collection<Ingredient> ingredients){
        this.name = name;
        this.description = description;
        this.steps = steps;
        this.image = image;
        this.chef = chef;

        if (ingredients!=null){
            this.ingredients = ingredients;
        } else {
            this.ingredients = new ArrayList<>();
        }
    }

    public Recipe(RecipeDTO dto) {
        this.name = dto.getName();
        this.description = dto.getDescription();
        this.steps = dto.getSteps();
        //this.chef = dto.getChef();
        //this.ingredients = dto.getIngredients();
    }


}
