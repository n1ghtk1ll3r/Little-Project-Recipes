package Project.APIs.View;

import Project.APIs.Domain.Model.Recipes;
import Project.APIs.Infra.Repository.RecipesRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import Project.APIs.Applications.DTO.RecipeResponse;
import Project.APIs.Applications.UseCase.RecipeUseCase;

import java.util.List;

@RestController
@RequestMapping("/api/recipes")
public class RecipesController {
    private final RecipesRepository recipesRepository;
    private final RecipeUseCase recipeUseCase;

    public RecipesController(RecipesRepository recipesRepository, RecipeUseCase recipeUseCase) {
        this.recipesRepository = recipesRepository;
        this.recipeUseCase = recipeUseCase;
    }

    @GetMapping
    public List<Recipes> findAll() {
        return recipesRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<RecipeResponse> findById(@PathVariable Integer id) {
        return recipeUseCase.recipeExecute(id);
    }

    @PostMapping
    public ResponseEntity<Recipes> create(@RequestBody Recipes recipe) {
        recipe.setId(null);
        Recipes savedRecipe = recipesRepository.save(recipe);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedRecipe);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Recipes> update(@PathVariable Integer id, @RequestBody Recipes recipe) {
        return recipesRepository.findById(id)
                .map(existingRecipe -> {
                    existingRecipe.setName(recipe.getName());
                    existingRecipe.setContent(recipe.getContent());
                    existingRecipe.setTipe(recipe.getTipe());
                    return ResponseEntity.ok(recipesRepository.save(existingRecipe));
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        if (!recipesRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }

        recipesRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}