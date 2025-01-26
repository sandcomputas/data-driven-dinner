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

    private fun findSQLByIdOrThrow(id: UUID): SQLRecipe {
        return find("id", id).firstResult() ?: throw NotFoundException("Recipe with ID $id not found")
    }

    fun findByIdOrThrow(id: UUID): Recipe {
        return findSQLByIdOrThrow(id).toPOJO()
    }

    fun save(recipe: Recipe) {
        persist(SQLRecipe.fromPOJO(recipe))
    }

    fun save(recipes: List<Recipe>) {
        recipes.forEach { save(it) }
    }

    fun update(new: Recipe): Recipe {
        val current = findSQLByIdOrThrow(new.idSafe())
        current.update(new)
        return current.toPOJO()
    }
}
