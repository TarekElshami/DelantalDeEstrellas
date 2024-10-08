package distribuidos.recetas.RecipeWebPage.repository;

import distribuidos.recetas.RecipeWebPage.entities.Chef;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Collection;

public interface ChefRepository extends JpaRepository<Chef, Long> {

    @Query(value="SELECT * FROM delantaldeestrellas.chef c where c.id <> 1", nativeQuery = true)
    Page<Chef> findChefs(Pageable pageable);

    Collection<Chef> findChefsByName(String name);
}
