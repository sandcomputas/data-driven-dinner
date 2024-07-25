package no.sondre

import io.quarkus.hibernate.orm.panache.kotlin.PanacheRepository
import jakarta.enterprise.context.ApplicationScoped
import jakarta.transaction.Transactional
import jakarta.ws.rs.NotFoundException
import java.util.*

@ApplicationScoped
@Transactional
class RecipeRepository : PanacheRepository<Recipe> {
    fun findByIdOrThrow(id: UUID): Recipe {
        return find("id", id).firstResult() ?: throw NotFoundException("Recipe with ID $id not found")
    }
}
