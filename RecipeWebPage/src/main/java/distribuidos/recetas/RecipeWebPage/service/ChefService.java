package distribuidos.recetas.RecipeWebPage.service;

import distribuidos.recetas.RecipeWebPage.DTO.ChefDTO;
import distribuidos.recetas.RecipeWebPage.entities.Chef;

import distribuidos.recetas.RecipeWebPage.entities.Ingredient;
import distribuidos.recetas.RecipeWebPage.repository.ChefRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class ChefService {

    @Autowired
    private ChefRepository chefRepository;

    private int pageSize = 3;

    public Collection<Chef> getAll(){
        return chefRepository.findAll();
    }

    public Collection<Chef> getAll(int page){
        return chefRepository.findAll(PageRequest.of(page, pageSize)).getContent();
    }

    public int MaxPage(){
        return chefRepository.findAll(PageRequest.of(0,pageSize)).getTotalPages();
    }
    public Optional<Chef> getChefById(Long id) {
        return chefRepository.findById(id);
    }


    public Chef newChef(Chef chef) {
        return chefRepository.save(chef);
    }

    public Chef substitute(Long id, Chef chef) {
        Optional<Chef> chef1 = chefRepository.findById(id);
        if(!chef1.isPresent()){
            return null;
        }
        chef.setId(id);
        return chefRepository.save(chef);

    }

    public void modifyToMatch(Long id, Chef chef) {
        Optional<Chef> storedChef1 = chefRepository.findById(id);
        Chef storedChef = new Chef();
        if (storedChef1.isPresent()){
            storedChef = storedChef1.get();
        }
        if (chef.getName() != null){
            storedChef.setName(chef.getName());
        } if (chef.getDescription() != null) {
            storedChef.setDescription(chef.getDescription());
        } if (chef.getImage() != null) {
            storedChef.setImage(chef.getImage());
        }
        storedChef.setId(id);
        chefRepository.save(storedChef);
    }

    public Chef delete(Long id) {
        Optional<Chef> chefOptional = chefRepository.findById(id);
        if (chefOptional.isPresent()) {
            Chef chef = chefOptional.get();
            chefRepository.delete(chef);
            return chef;
        } else {
            return null;
        }
    }



    public List<Chef> getRandomN(int n){
        List<Chef> allChefs = chefRepository.findAll();
        Collections.shuffle(allChefs);
        return allChefs.stream().limit(n).collect(Collectors.toList());
    }

    public boolean isValidChef(ChefDTO chef) {
        String name = chef.getName();
        String description = chef.getDescription();
        if (name == null || name.isEmpty() ||
                description == null || description.isEmpty()) return false;
        return true;
    }

    public Chef getEmptyChef(){
        Chef chef = new Chef("Sin Chef", "Este Chef se le asigna a recetas que todavía no tienen un chef válido asignado.", "noImg");
        chef.setId(-1L);
        return chef;
    }

    public void save(Chef chef){
        chefRepository.save(chef);
    }

}
