package distribuidos.recetas.RecipeWebPage.service;

import distribuidos.recetas.RecipeWebPage.DTO.IngredientDTO;
import distribuidos.recetas.RecipeWebPage.entities.Ingredient;
import distribuidos.recetas.RecipeWebPage.repository.IngredientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class IngredientService {

    @Autowired
    private IngredientRepository ingredientRepository;

    public Collection<Ingredient> getAll(){
        return ingredientRepository.findAll();
    }

    public Ingredient getIngredientById(Long id) {
        return ingredientRepository.findById(id).orElse(null);
    }

    public Collection<Ingredient> getIngredientById(Collection<Long> ids) {
        return ingredientRepository.findAllById(ids);
    }

    public Ingredient newIngredient(Ingredient ingredient) {
        return ingredientRepository.save(ingredient);
    }

    public Ingredient substitute(Long id, Ingredient ingredient) {
        return ingredientRepository.findById(id)
                .map(existingIngredient -> {
                    ingredient.setId(id);
                    return ingredientRepository.save(ingredient);
                }).orElse(null);
    }

    public void modifyToMatch(Long id, Ingredient ingredient) {
        ingredientRepository.findById(id)
                .ifPresent(existingIngredient -> {
                    if (ingredient.getName() != null) existingIngredient.setName(ingredient.getName());
                    if (ingredient.getDescription() != null) existingIngredient.setDescription(ingredient.getDescription());
                    if (ingredient.getImage() != null) existingIngredient.setImage(ingredient.getImage());
                    ingredientRepository.save(existingIngredient);
                });
    }

    public Ingredient delete(Long id) {
        return ingredientRepository.findById(id)
                .map(ingredient -> {
                    ingredientRepository.delete(ingredient);
                    return ingredient;
                }).orElse(null);
    }

    public List<Ingredient> getFirst3(){
        List<Ingredient> allIngredients = ingredientRepository.findAll();
        return allIngredients.stream().limit(3).collect(Collectors.toList());
    }

    public boolean isValidRecipe(IngredientDTO ingredientDTO) {
        String name = ingredientDTO.getName();
        String description = ingredientDTO.getDescription();
        String image = ingredientDTO.getImage();
        if (name == null || name.isEmpty() ||
            description == null || description.isEmpty() ||
            image == null || image.isEmpty()
        ) return false;
        if(ingredientDTO.getBestRecipes() != null) return false;
        return true;
    }

}
