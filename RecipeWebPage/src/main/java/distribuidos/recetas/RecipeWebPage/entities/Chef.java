package distribuidos.recetas.RecipeWebPage.entities;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Blob;
import java.util.Collection;

//@Entity
@Getter
@Setter
//@NoArgsConstructor
public class Chef {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String description;
    private String image;

    private static Long currentId = 0L;

    //private Collection<Ingredient> favIng;
    private Collection<Recipe> bestRecipes;


    public Chef() {
    }

    public Chef(String name) {
        this.name = name;
    }

    public Chef(String name, String description, String image){
        this.name = name;
        this.description = description;
        this.image = image;
        this.id = currentId++;
    }
}
