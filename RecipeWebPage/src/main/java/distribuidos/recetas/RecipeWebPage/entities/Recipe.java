package distribuidos.recetas.RecipeWebPage.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

import java.sql.Blob;
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

    private Collection<Ingredient> ingredients; //cómo hacemos el tema cantidades? Para que pueda ser  por ejemplo 5g de mejorana
    private Chef chef;

    public Recipe(String name){
        this.name = name;
    }

}
