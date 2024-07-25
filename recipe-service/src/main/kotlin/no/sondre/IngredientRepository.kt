package no.sondre

import io.quarkus.hibernate.orm.panache.kotlin.PanacheRepository
import jakarta.enterprise.context.ApplicationScoped
import jakarta.transaction.Transactional
import jakarta.ws.rs.NotFoundException
import java.util.UUID

@ApplicationScoped
@Transactional
class IngredientRepository : PanacheRepository<Ingredient> {
    fun findByIdOrThrow(id: UUID): Ingredient {
        return find("id", id).firstResult() ?: throw NotFoundException("Recipe with ID $id not found")
    }
}
