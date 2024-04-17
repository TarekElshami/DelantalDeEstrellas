package distribuidos.recetas.RecipeWebPage.service;

import distribuidos.recetas.RecipeWebPage.DTO.IngredientDTO;
import distribuidos.recetas.RecipeWebPage.entities.Ingredient;
import distribuidos.recetas.RecipeWebPage.entities.Recipe;
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
        if (ids==null) return new ArrayList<>();
        return ingredientRepository.findAllById(ids);
    }

    public Ingredient newIngredient(Ingredient ingredient) {
        return ingredientRepository.save(ingredient);
    }

    public Ingredient substitute(Long id, Ingredient ingredient) {
        Optional<Ingredient> existingIngredientOptional = ingredientRepository.findById(id);
        if (existingIngredientOptional.isPresent()) {
            ingredient.setId(id);
            return ingredientRepository.save(ingredient);
        } else {
            return null;
        }
    }

    public void modifyToMatch(Long id, Ingredient ingredient) {
        Optional<Ingredient> existingIngredientOptional = ingredientRepository.findById(id);
        if (existingIngredientOptional.isPresent()) {
            Ingredient existingIngredient = existingIngredientOptional.get();
            if (ingredient.getName() != null) existingIngredient.setName(ingredient.getName());
            if (ingredient.getDescription() != null) existingIngredient.setDescription(ingredient.getDescription());
            if (ingredient.getImage() != null) existingIngredient.setImage(ingredient.getImage());
            ingredientRepository.save(existingIngredient);
        }
    }

    public Ingredient delete(Long id) {
        Optional<Ingredient> ingredientOptional = ingredientRepository.findById(id);
        if (ingredientOptional.isPresent()) {
            Ingredient ingredient = ingredientOptional.get();

            if (!ingredient.getBestRecipes().isEmpty()) {
                return null; //only allow deletion if it has no recipes associated
            }

            ingredientRepository.delete(id);
            ingredientRepository.delete(ingredient);
            return ingredient;
        } else {
            return null;
        }
    }

    public List<Ingredient> getRandomN(int n){
        List<Ingredient> allIngredients = ingredientRepository.findAll();
        Collections.shuffle(allIngredients);
        return allIngredients.stream().limit(n).collect(Collectors.toList());
    }

    public boolean isValidIngredient(IngredientDTO ingredientDTO) {
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
