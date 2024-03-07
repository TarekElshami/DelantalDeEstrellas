package distribuidos.recetas.RecipeWebPage.service;

import distribuidos.recetas.RecipeWebPage.entities.Recipe;
import jakarta.annotation.PostConstruct;
import org.springframework.core.io.ClassPathResource;
import org.springframework.util.StreamUtils;

import javax.sql.rowset.serial.SerialBlob;
import java.io.IOException;
import java.sql.Blob;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

public class DatabaseInitializer {

    private final RecipeService recipeService = RecipeService.getInstance();
    private final ChefService chefService = ChefService.getInstance();
    private final IngredientService ingredientService = IngredientService.getInstance();

    @PostConstruct
    public void init() throws IOException, SQLException {
        recipeService.newRecipe(new Recipe(1L, "Spaghetti Bolognese", "Classic Italian pasta dish", "Chef Mario", "recipe.png"));
        recipeService.newRecipe(new Recipe(2L, "Chicken Alfredo", "Creamy Alfredo sauce with grilled chicken", "Chef Julia", "recipe.png"));
        recipeService.newRecipe(new Recipe(3L, "Vegetarian Stir Fry", "Colorful mix of vegetables in soy sauce", "Chef Alex", "recipe.png"));
        recipeService.newRecipe(new Recipe(4L, "Chocolate Lava Cake", "Decadent dessert with a gooey chocolate center", "Chef Carla", "recipe.png"));
        recipeService.newRecipe(new Recipe(5L, "Greek Salad", "Refreshing salad with feta and olives", "Chef Nikos", "recipe.png"));
        recipeService.newRecipe(new Recipe(6L, "Beef Tacos", "Tasty tacos with seasoned beef", "Chef Maria", "recipe.png"));
        recipeService.newRecipe(new Recipe(7L, "Caprese Salad", "Simple salad with tomatoes, mozzarella, and basil", "Chef Luca", "recipe.png"));
        recipeService.newRecipe(new Recipe(8L, "Shrimp Scampi", "Garlicky shrimp in a lemon butter sauce", "Chef Isabella", "recipe.png"));
        recipeService.newRecipe(new Recipe(9L, "Mango Salsa Chicken", "Grilled chicken topped with fresh mango salsa", "Chef Miguel", "recipe.png"));
        recipeService.newRecipe(new Recipe(10L, "Vegetable Lasagna", "Layers of pasta, vegetables, and cheesy goodness", "Chef Sofia", "recipe.png"));

        ingredientService.newIngredient("Pimiento", "Verde, fresco y ligeramente picante, perfecto para ensaladas.");
        ingredientService.newIngredient("Pimiento", "Verde, fresco y ligeramente picante, perfecto para ensaladas.");

    }
}
