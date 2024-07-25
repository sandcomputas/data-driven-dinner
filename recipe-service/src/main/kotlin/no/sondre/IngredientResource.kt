package no.sondre

import jakarta.inject.Inject
import jakarta.ws.rs.GET
import jakarta.ws.rs.POST
import jakarta.ws.rs.Path
import jakarta.ws.rs.PathParam
import java.util.*

@Path("ingredient")
class IngredientResource {

    @Inject
    lateinit var repo: IngredientRepository

    @GET
    fun list(): List<Ingredient> {
        return repo.listAll()
    }

    @GET
    @Path("{id}")
    fun load(@PathParam("id") id: UUID): Ingredient {
        return repo.findByIdOrThrow(id)
    }

    @POST
    fun save(ingredient: Ingredient): Ingredient {
        repo.persist(ingredient)
        return ingredient
    }
}
