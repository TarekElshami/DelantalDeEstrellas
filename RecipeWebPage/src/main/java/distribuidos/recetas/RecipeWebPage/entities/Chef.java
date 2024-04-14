package distribuidos.recetas.RecipeWebPage.entities;


import distribuidos.recetas.RecipeWebPage.DTO.ChefDTO;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Blob;
import java.util.Collection;
import java.util.Objects;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Chef {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String description;
    private String image;

    @OneToMany(mappedBy = "chef")
    private Collection<Recipe> bestRecipes;

    public Chef(String name) {
        this.name = name;
    }

    public Chef(String name, String description, String image){
        this.name = name;
        this.description = description;
        this.image = image;
    }

    public Chef(ChefDTO dto) {
        this.name = dto.getName();
        this.description = dto.getDescription();
        this.image = dto.getImage();
    }

    public void deleteRecipeById(Long id) {
        for (Recipe recipe : bestRecipes){
            if (recipe.getId().equals(id)){
                bestRecipes.remove(recipe);
                return;
            }
        }
    }

    public void addRecipe(Recipe recipe) {
        bestRecipes.add(recipe);
    }
}
