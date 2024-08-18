package no.sondre

import jakarta.enterprise.context.ApplicationScoped
import jakarta.inject.Inject
import jakarta.ws.rs.BadRequestException
import java.util.*

@ApplicationScoped
class RecipeService {

    @Inject
    lateinit var repo: RecipeRepository

    fun list(): List<Recipe> {
        return repo.listAll()
    }

    fun load(id: UUID): Recipe {
        return repo.findByIdOrThrow(id)
    }

    fun save(recipe: Recipe): Recipe {
        if (repo.findById(recipe.id) != null) {
            throw BadRequestException("Recipe with id: ${recipe.id} already exists")
        }
        recipe.complete()
        repo.persist(recipe)
        return recipe
    }

    fun update(new: Recipe): Recipe {
        val old = load(new.id)
        old.update(new)
        return old
    }
}