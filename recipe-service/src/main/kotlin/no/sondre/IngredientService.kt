package no.sondre

import jakarta.enterprise.context.ApplicationScoped
import jakarta.inject.Inject
import jakarta.transaction.Transactional
import java.util.UUID

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

}