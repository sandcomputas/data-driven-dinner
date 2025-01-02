package no.sondre.repository

import io.quarkus.hibernate.orm.panache.kotlin.PanacheRepository
import jakarta.enterprise.context.ApplicationScoped
import jakarta.transaction.Transactional
import jakarta.ws.rs.NotFoundException
import no.sondre.domain.Recipe
import no.sondre.domain.SQLRecipe
import java.util.*

@ApplicationScoped
@Transactional
class RecipeRepository : PanacheRepository<SQLRecipe> {

    fun all(): List<Recipe> {
        return listAll().map { it.toPOJO() }
    }

    fun findById(id: UUID): Recipe? {
        val r = find("id", id).firstResult()
        return r?.toPOJO()
    }

    fun findByIdOrThrow(id: UUID): Recipe {
        return findById(id) ?: throw NotFoundException("Recipe with ID $id not found")
    }

    fun save(recipe: Recipe) {
        persist(SQLRecipe.fromPOJO(recipe))
    }

    fun save(recipes: List<Recipe>) {
        recipes.forEach { save(it) }
    }
}
