package distribuidos.recetas.RecipeWebPage.service;

import distribuidos.recetas.RecipeWebPage.entities.Chef;
import distribuidos.recetas.RecipeWebPage.entities.Ingredient;
import distribuidos.recetas.RecipeWebPage.entities.Recipe;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class DatabaseInitializer {

    private final RecipeService recipeService = RecipeService.getInstance();
    private final ChefService chefService = ChefService.getInstance();
    private final IngredientService ingredientService = IngredientService.getInstance();

    @PostConstruct
    public void init() {
        recipeService.newRecipe(new Recipe(/*1L,*/ "Spaghetti Bolognese", "Classic Italian pasta dish", null, List.of("Boil spaghetti", "Prepare Bolognese sauce", "Combine and serve"), "recipe.png", null));
        recipeService.newRecipe(new Recipe(/*2L,*/ "Chicken Alfredo", "Creamy Alfredo sauce with grilled chicken", null, List.of("Cook chicken", "Prepare Alfredo sauce", "Combine and serve"), "recipe.png", null));
        recipeService.newRecipe(new Recipe(/*3L,*/ "Vegetarian Stir Fry", "Colorful mix of vegetables in soy sauce", null, List.of("Chop vegetables", "Stir fry in soy sauce", "Serve hot"), "recipe.png", null));
        recipeService.newRecipe(new Recipe(/*4L,*/ "Chocolate Lava Cake", "Decadent dessert with a gooey chocolate center", null, List.of("Preheat oven", "Prepare chocolate batter", "Bake until gooey"), "recipe.png", null));
        recipeService.newRecipe(new Recipe(/*5L,*/ "Greek Salad", "Refreshing salad with feta and olives", null, List.of("Chop tomatoes, cucumbers, and olives", "Add feta cheese", "Drizzle with olive oil"), "recipe.png", null));
        recipeService.newRecipe(new Recipe(/*6L,*/ "Beef Tacos", "Tasty tacos with seasoned beef", null, List.of("Season and cook beef", "Assemble tacos with toppings", "Enjoy!"), "recipe.png", null));
        recipeService.newRecipe(new Recipe(/*7L,*/ "Caprese Salad", "Simple salad with tomatoes, mozzarella, and basil", null, List.of("Slice tomatoes and mozzarella", "Arrange in layers with basil", "Drizzle with balsamic glaze"), "recipe.png", null));
        recipeService.newRecipe(new Recipe(/*8L,*/ "Shrimp Scampi", "Garlicky shrimp in a lemon butter sauce", null, List.of("Sauté shrimp in garlic butter", "Add lemon juice", "Serve over pasta"), "recipe.png", null));
        recipeService.newRecipe(new Recipe(/*9L,*/ "Mango Salsa Chicken", "Grilled chicken topped with fresh mango salsa", null, List.of("Grill chicken", "Prepare mango salsa", "Top chicken with salsa"), "recipe.png", null));
        recipeService.newRecipe(new Recipe(/*10L,*/ "Vegetable Lasagna", "Layers of pasta, vegetables, and cheesy goodness", null, List.of("Layer pasta and vegetables", "Top with cheese", "Bake until bubbly"), "recipe.png", null));
        recipeService.newRecipe(new Recipe(/*11L,*/ "Spinach and Feta Stuffed Chicken", "Juicy chicken breasts stuffed with spinach and feta cheese", null, List.of("Stuff chicken with spinach and feta", "Bake until golden", "Serve hot"), "recipe.png", null));
        recipeService.newRecipe(new Recipe(/*12L,*/ "Blueberry Pancakes", "Fluffy pancakes filled with sweet blueberries", null, List.of("Prepare pancake batter", "Fold in blueberries", "Cook until golden brown"), "recipe.png", null));


        Ingredient ingredient = new Ingredient("Pimiento", "Verde, fresco y ligeramente picante, perfecto para ensaladas.", "https://www.klarskovgartneri.dk/media/u3knpzlw/gul-peber.jpg?anchor=center&mode=crop&width=900&height=650&rnd=132577017043930000");
        Ingredient ingredient2 = new Ingredient("Manzana", "Verde, fresco y ligeramente picante, perfecto para ensaladas.", "https://ecomercioagrario.com/wp-content/uploads/2020/07/produccion-peras-se-recupera-manzanas-bajan-cataluna-ecomercioagrario.jpg");
        ingredientService.newIngredient(ingredient);
        ingredientService.newIngredient(ingredient2);

        chefService.newChef(new Chef("Blackie", "Hace las mejores galletas del vecindaio", "data:image/png;base64,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"));
        chefService.newChef(new Chef("Garfield", "Solo sabe hacer lasaña, pero se le da de miedo", "data:image/png;base64,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"));
        chefService.newChef(new Chef("Pequeñin", "Cocinar puede que no, pero es demasiado mono", "https://www.shutterstock.com/shutterstock/photos/2092886932/display_1500/stock-vector-cute-kitten-wearing-a-chef-hat-2092886932.jpg"));
        chefService.newChef(new Chef("SuperChef", "Su nombre lo dice todo", "https://us.123rf.com/450wm/aprillrain/aprillrain2301/aprillrain230102062/197136034-caricatura-de-un-gato-cocinero-con-sombrero-de-chef-que-cocina-algo-en-la-cocina-caricatura.jpg?ver=6"));
        chefService.newChef(new Chef("Manchas", "5 estrellas michelin", "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTQCWLXiGp40ewzewy7i8p2Kk3LaLj3FQCG76yhdr5Pp9nqxUz4E35gIm_J6AENWVb-21c&usqp=CAU"));

        recipeService.getRecipeById(1L).setIngredients(ingredientService.getAll());
        recipeService.getRecipeById(2L).setIngredients(Collections.singletonList(ingredientService.getIngredientById(1L)));
        recipeService.getRecipeById(3L).setIngredients(Collections.singletonList(ingredientService.getIngredientById(2L)));

        recipeService.getRecipeById(1L).setChef(chefService.getChefById(5L));
        recipeService.getRecipeById(2L).setChef(chefService.getChefById(1L));
        recipeService.getRecipeById(3L).setChef(chefService.getChefById(2L));
        recipeService.getRecipeById(4L).setChef(chefService.getChefById(3L));
        recipeService.getRecipeById(5L).setChef(chefService.getChefById(4L));
        recipeService.getRecipeById(6L).setChef(chefService.getChefById(5L));
        recipeService.getRecipeById(7L).setChef(chefService.getChefById(2L));
        recipeService.getRecipeById(8L).setChef(chefService.getChefById(3L));
        recipeService.getRecipeById(9L).setChef(chefService.getChefById(3L));
        recipeService.getRecipeById(10L).setChef(chefService.getChefById(4L));
        recipeService.getRecipeById(11L).setChef(chefService.getChefById(5L));
        recipeService.getRecipeById(12L).setChef(chefService.getChefById(1L));
    }
}
