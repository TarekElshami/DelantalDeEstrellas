package distribuidos.recetas.RecipeWebPage.repository;

import distribuidos.recetas.RecipeWebPage.entities.Ingredient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

public interface IngredientRepository extends JpaRepository<Ingredient, Long> {

    @Transactional
    @Modifying
    @Query(value = "DELETE FROM recipe_ingredients WHERE ingredients_id=?1", nativeQuery = true)
    void delete(Long id);

}
