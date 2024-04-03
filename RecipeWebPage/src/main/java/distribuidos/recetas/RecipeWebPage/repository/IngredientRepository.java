package distribuidos.recetas.RecipeWebPage.repository;

import distribuidos.recetas.RecipeWebPage.entities.Ingredient;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IngredientRepository extends JpaRepository<Ingredient, Long> {

}
