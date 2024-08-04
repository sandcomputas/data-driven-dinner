package no.sondre

import jakarta.enterprise.context.ApplicationScoped
import jakarta.inject.Inject
import jakarta.transaction.Transactional
import java.util.*

@ApplicationScoped
@Transactional
class IngredientService {

    @Inject
    lateinit var repo: IngredientRepository

    fun list(): List<Ingredient> {
        return repo.listAll()
    }

    fun load(id: UUID): Ingredient {
        return repo.findByIdOrThrow(id)
    }

    fun save(ingredient: Ingredient): Ingredient {
        repo.persist(ingredient)
        return ingredient
    }

    fun update(ingredient: Ingredient): Ingredient {
        val old = load(ingredient.id)
        old.name = ingredient.name
        return old
    }
}