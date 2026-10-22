package Project.APIs.Infra.Repository;

import java.util.List;

import Project.APIs.Domain.Model.Recipes;

import org.springframework.data.jpa.repository.JpaRepository;

public interface RecipesRepository extends JpaRepository<Recipes, Integer> {
    List<Recipes> findByName(String name);
}