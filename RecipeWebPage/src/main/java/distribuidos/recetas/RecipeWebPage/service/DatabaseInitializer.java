package distribuidos.recetas.RecipeWebPage.service;

import distribuidos.recetas.RecipeWebPage.entities.Chef;
import distribuidos.recetas.RecipeWebPage.entities.Ingredient;
import distribuidos.recetas.RecipeWebPage.entities.Recipe;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class DatabaseInitializer {

    @Autowired
    private RecipeService recipeService;
    @Autowired
    private ChefService chefService;
    @Autowired
    private IngredientService ingredientService;

    @PostConstruct
    public void init() {
        if (! (chefService.count() == 0 && recipeService.count() == 0 && ingredientService.count() == 0)) return;
        //Recipe
        Recipe recipe = new Recipe("Spaghetti Bolognese", "Classic Italian pasta dish", null, List.of("Boil spaghetti", "Prepare Bolognese sauce", "Combine and serve"), "https://rollercoaster.ie/wp-content/uploads/2020/04/super-easy-spaghetti-bolognese.jpg", null);
        Recipe recipe2 = new Recipe("Chicken Alfredo", "Creamy Alfredo sauce with grilled chicken", null, List.of("Cook chicken", "Prepare Alfredo sauce", "Combine and serve"), "https://evoke.ie/wp-content/uploads/2019/11/Sprezzatura-Gluten-Free-Pasta-copy.jpg", null);
        Recipe recipe3 = new Recipe("Vegetarian Stir Fry", "Colorful mix of vegetables in soy sauce", null, List.of("Chop vegetables", "Stir fry in soy sauce", "Serve hot"), "/img/vegStirFry.jpg", null);
        Recipe recipe4 = new Recipe("Chocolate Lava Cake", "Decadent dessert with a gooey chocolate center", null, List.of("Preheat oven", "Prepare chocolate batter", "Bake until gooey"), "/img/chocLavaCake.jpg", null);
        Recipe recipe5 = new Recipe("Greek Salad", "Refreshing salad with feta and olives", null, List.of("Chop tomatoes, cucumbers, and olives", "Add feta cheese", "Drizzle with olive oil"), "https://www.olivetomato.com/wp-content/uploads/2021/07/SAM_7634.jpeg", null);
        Recipe recipe6 = new Recipe("Beef Tacos", "Tasty tacos with seasoned beef", null, List.of("Season and cook beef", "Assemble tacos with toppings", "Enjoy!"), "https://www.lamichoacanameatmarket.com/wp-content/uploads/2019/08/recetas-900x650.jpg", null);
        Recipe recipe7 = new Recipe("Caprese Salad", "Simple salad with tomatoes, mozzarella, and basil", null, List.of("Slice tomatoes and mozzarella", "Arrange in layers with basil", "Drizzle with balsamic glaze"), "https://rollercoaster.ie/wp-content/uploads/2019/04/shutterstock_290701757.jpg", null);
        Recipe recipe8 = new Recipe("Shrimp Scampi", "Garlicky shrimp in a lemon butter sauce", null, List.of("Sauté shrimp in garlic butter", "Add lemon juice", "Serve over pasta"), "https://santa-priscila.com/Quito/wp-content/uploads/2020/09/Bloque-04_Izquierdo_4nuevo.jpg", null);
        Recipe recipe9 = new Recipe("Mango Salsa Chicken", "Grilled chicken topped with fresh mango salsa", null, List.of("Grill chicken", "Prepare mango salsa", "Top chicken with salsa"), "https://img.buzzfeed.com/buzzfeed-static/static/2018-01/11/15/asset/buzzfeed-prod-fastlane-01/sub-buzz-12466-1515702387-2.jpg?downsize=900:*&output-format=auto&output-quality=auto", null);
        Recipe recipe10 = new Recipe("Vegetable Lasagna", "Layers of pasta, vegetables, and cheesy goodness", null, List.of("Layer pasta and vegetables", "Top with cheese", "Bake until bubbly"), "https://phillyjaycooking.com/wp-content/uploads/2022/03/How-To-Make-Lasagna.jpg", null);
        Recipe recipe11 = new Recipe("Spinach and Feta Stuffed Chicken", "Juicy chicken breasts stuffed with spinach and feta cheese", null, List.of("Stuff chicken with spinach and feta", "Bake until golden", "Serve hot"), "https://everydayshortcuts.com/wp-content/uploads/2021/10/how-to-make-stuffed-turkey-breast.jpg", null);
        Recipe recipe12 = new Recipe("Blueberry Pancakes", "Fluffy pancakes filled with sweet blueberries", null, List.of("Prepare pancake batter", "Fold in blueberries", "Cook until golden brown"), "https://evoke.ie/wp-content/uploads/2020/02/GettyImages-1206651563.jpg", null);

        //Ingredients
        Ingredient ingredient = new Ingredient("Pimiento", "Verde, fresco y ligeramente picante, perfecto para ensaladas.", "https://www.klarskovgartneri.dk/media/u3knpzlw/gul-peber.jpg?anchor=center&mode=crop&width=900&height=650&rnd=132577017043930000");
        Ingredient ingredient2 = new Ingredient("Manzana", "Es nutritiva, rica en fibra y vitaminas", "https://ecomercioagrario.com/wp-content/uploads/2020/07/produccion-peras-se-recupera-manzanas-bajan-cataluna-ecomercioagrario.jpg");
        Ingredient ingredient3 = new Ingredient("Tomate", "Rojo, jugoso, ideal para salsas y ensaladas.", "https://golsmedia.com/wp-content/uploads/2018/09/tomate-900x650.jpg");
        Ingredient ingredient4 = new Ingredient("Cebolla", "Blanca, esencial para el sofrito y base de muchas recetas.", "https://e7.pngegg.com/pngimages/201/69/png-clipart-parappa-the-rapper-2-playstation-2-chop-chop-master-onion-s-rap-playstation.png");
        Ingredient ingredient5 = new Ingredient("Ajo", "Intenso y aromático, indispensable en la cocina mediterránea.", "https://www.euskalak.com/comun/galeria/giriondojm/diapositiva7.jpg");
        Ingredient ingredient6 = new Ingredient("Zanahoria", "Naranja, crujiente, dulce, perfecta para guisos y ensaladas.", "https://e7.pngegg.com/pngimages/810/234/png-clipart-carrot-vegetable-drawing-soup-carrot-soup-child.png");
        Ingredient ingredient7 = new Ingredient("Pepino", "Verde, fresco, ideal para ensaladas y aperitivos.", "https://e7.pngegg.com/pngimages/555/854/png-clipart-vegetable-melon-cartoon-vegetable-food-photography.png");;

        //Chefs
        chefService.getDefaultChef();
        chefService.newChef(new Chef("Blackie", "Hace las mejores galletas del vecindaio", "https://imgs.search.brave.com/o_sKJgf-zu8Xs3rtChHmgZrKHt4uHCVjWlJ3lbO9GTM/rs:fit:500:0:0/g:ce/aHR0cHM6Ly9jb2xv/cmluZ29ubHkuY29t/L2VzL2ltYWdlcy9p/bWdjb2xvci9DaGVm/LUN1bGluYXJpby1k/ZS1EaWJ1am9zLUFu/aW1hZG9zLmpwZw"));
        chefService.newChef(new Chef("Garfield", "Solo sabe hacer lasaña, pero se le da de miedo","https://imgs.search.brave.com/oytmY1I40RkuCMmDs1IZH8AHZVlWyZ8M-JSzZMlOWHc/rs:fit:500:0:0/g:ce/aHR0cHM6Ly93d3cu/aW1hZ3VpLmNvbS9p/L2RpYnVqby1jb2Np/bmVyYXMtaW1hZ3Vp/LTE0NjU5NS53ZWJw"));
        chefService.newChef(new Chef("Pequeñin", "Cocinar puede que no, pero es demasiado mono", "https://www.shutterstock.com/shutterstock/photos/2092886932/display_1500/stock-vector-cute-kitten-wearing-a-chef-hat-2092886932.jpg"));
        chefService.newChef(new Chef("SuperChef", "Su nombre lo dice todo", "https://us.123rf.com/450wm/aprillrain/aprillrain2301/aprillrain230102062/197136034-caricatura-de-un-gato-cocinero-con-sombrero-de-chef-que-cocina-algo-en-la-cocina-caricatura.jpg?ver=6"));
        chefService.newChef(new Chef("Manchas", "5 estrellas michelin", "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTQCWLXiGp40ewzewy7i8p2Kk3LaLj3FQCG76yhdr5Pp9nqxUz4E35gIm_J6AENWVb-21c&usqp=CAU"));


        ingredientService.newIngredient(ingredient);
        ingredientService.newIngredient(ingredient2);
        ingredientService.newIngredient(ingredient3);
        ingredientService.newIngredient(ingredient4);
        ingredientService.newIngredient(ingredient5);
        ingredientService.newIngredient(ingredient6);
        ingredientService.newIngredient(ingredient7);

        recipe.getIngredients().add(ingredient);

        recipe.setIngredients(ingredientService.getAll());
        recipe2.setIngredients(Collections.singletonList(ingredientService.getIngredientById(1L)));
        recipe3.setIngredients(Collections.singletonList(ingredientService.getIngredientById(2L)));

        recipe.setChef(chefService.getChefById(5L).get());
        recipe2.setChef(chefService.getChefById(6L).get());
        recipe3.setChef(chefService.getChefById(2L).get());
        recipe4.setChef(chefService.getChefById(3L).get());
        recipe5.setChef(chefService.getChefById(4L).get());
        recipe6.setChef(chefService.getChefById(5L).get());
        recipe7.setChef(chefService.getChefById(2L).get());
        recipe8.setChef(chefService.getChefById(3L).get());
        recipe9.setChef(chefService.getChefById(3L).get());
        recipe10.setChef(chefService.getChefById(4L).get());
        recipe11.setChef(chefService.getChefById(5L).get());
        recipe12.setChef(chefService.getChefById(6L).get());

        recipeService.newRecipe(recipe);
        recipeService.newRecipe(recipe2);
        recipeService.newRecipe(recipe3);
        recipeService.newRecipe(recipe4);
        recipeService.newRecipe(recipe5);
        recipeService.newRecipe(recipe6);
        recipeService.newRecipe(recipe7);
        recipeService.newRecipe(recipe8);
        recipeService.newRecipe(recipe9);
        recipeService.newRecipe(recipe10);
        recipeService.newRecipe(recipe11);
        recipeService.newRecipe(recipe12);
    }
}
