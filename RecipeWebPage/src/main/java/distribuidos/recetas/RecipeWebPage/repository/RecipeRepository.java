package distribuidos.recetas.RecipeWebPage.repository;

import distribuidos.recetas.RecipeWebPage.entities.Recipe;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RecipeRepository extends JpaRepository<Recipe, Long> {
}
