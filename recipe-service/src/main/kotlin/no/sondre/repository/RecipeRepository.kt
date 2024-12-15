package no.sondre.repository

import io.quarkus.hibernate.orm.panache.kotlin.PanacheRepository
import jakarta.enterprise.context.ApplicationScoped
import jakarta.transaction.Transactional
import jakarta.ws.rs.NotFoundException
import no.sondre.domain.Recipe
import java.util.*

@ApplicationScoped
@Transactional
class RecipeRepository : PanacheRepository<Recipe> {

    fun findById(id: UUID): Recipe? {
        return find("id", id).firstResult()
    }

    fun findByIdOrThrow(id: UUID): Recipe {
        return findById(id) ?: throw NotFoundException("Recipe with ID $id not found")
    }
}
