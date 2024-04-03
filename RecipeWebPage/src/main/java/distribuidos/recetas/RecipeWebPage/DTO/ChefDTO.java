package distribuidos.recetas.RecipeWebPage.DTO;


import distribuidos.recetas.RecipeWebPage.entities.Chef;
import distribuidos.recetas.RecipeWebPage.entities.Ingredient;
import distribuidos.recetas.RecipeWebPage.entities.Recipe;

import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;


@Getter
@Setter
@NoArgsConstructor
public class ChefDTO {
    private Long id;
    private String name;
    private String description;
    private String image;

    private Collection<Long> bestRecipes;

    public ChefDTO(Chef chef) {
        this.id = chef.getId();
        this.name = chef.getName();
        this.description = chef.getDescription();
        this.image = chef.getImage();
        this.bestRecipes = new ArrayList<>();
        if (chef.getBestRecipes()!=null) {
            for (Recipe recipe : chef.getBestRecipes()) {
                if (recipe!=null){
                    this.bestRecipes.add(recipe.getId());
                }
            }
        }
    }

    public static Collection<ChefDTO> toDTO(Collection<Chef> chefs) {
        List<ChefDTO> result = new ArrayList<>();
        for (Chef chef : chefs) {
            result.add(new ChefDTO(chef));
        }
        return result;
    }

}
