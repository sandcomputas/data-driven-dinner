package no.sondre.services

import jakarta.enterprise.context.ApplicationScoped
import jakarta.inject.Inject
import jakarta.transaction.Transactional
import no.sondre.repository.IngredientRepository
import no.sondre.domain.Ingredient
import java.util.*

@ApplicationScoped
@Transactional
class IngredientService {

    @Inject
    private lateinit var repo: IngredientRepository

    fun list(): List<Ingredient> {
        return repo.all()
    }

    fun load(id: UUID): Ingredient {
        return repo.findByIdOrThrow(id)
    }

    fun save(ingredient: Ingredient): Ingredient {
        ingredient.initNew()
        return repo.save(ingredient)
    }

    fun update(ingredient: Ingredient): Ingredient {
        val old = load(ingredient.id)
        old.name = ingredient.name
        return old
    }
}