package Project.APIs.Applications.UseCase;

import Project.APIs.Applications.DTO.RecipeResponse;
import Project.APIs.Infra.Repository.RecipesRepository;
import org.springframework.stereotype.Service;
import org.springframework.http.ResponseEntity;
import Project.APIs.Domain.Model.Enuns.RecipeTipe;

@Service
public class RecipeUseCase {
    private final RecipesRepository recipesRepository;

    public RecipeUseCase(RecipesRepository recipesRepository) {
        this.recipesRepository = recipesRepository;
    }

    public ResponseEntity<RecipeResponse> recipeExecute(Integer id) {
       return recipesRepository.findById(id)
                .map(recipe -> new RecipeResponse(recipe.getId(), recipe.getName(), recipe.getContent(), getRecipeTypeString(recipe.getTipe())))
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }


    private String getRecipeTypeString(RecipeTipe recipeTipe) {
        if (recipeTipe == null) {
            return null;
        }
        return recipeTipe.getValue();
    }
}



//ola ola!!!