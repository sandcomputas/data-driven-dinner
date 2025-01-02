package no.sondre.services

import jakarta.enterprise.context.ApplicationScoped
import jakarta.inject.Inject
import jakarta.ws.rs.BadRequestException
import no.sondre.repository.RecipeRepository
import no.sondre.domain.Recipe
import java.util.*

@ApplicationScoped
class RecipeService {

    @Inject
    private lateinit var repo: RecipeRepository

    fun list(): List<Recipe> {
        return repo.all()
    }

    fun load(id: UUID): Recipe {
        return repo.findByIdOrThrow(id)
    }

    fun new(recipe: Recipe): Recipe {
        if (repo.findById(recipe.id) != null) {
            throw BadRequestException("Recipe with id: ${recipe.id} already exists")
        }
        recipe.complete()
        repo.save(recipe)
        return recipe
    }

    fun update(new: Recipe): Recipe {
        val old = load(new.id)
        old.update(new)
        return old
    }
}