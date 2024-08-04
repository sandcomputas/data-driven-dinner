package no.sondre

import jakarta.inject.Inject
import jakarta.ws.rs.BadRequestException
import jakarta.ws.rs.GET
import jakarta.ws.rs.POST
import jakarta.ws.rs.PUT
import jakarta.ws.rs.Path
import jakarta.ws.rs.PathParam
import java.util.*

@Path("ingredient")
class IngredientResource {

    @Inject
    lateinit var service: IngredientService

    @GET
    fun list(): List<Ingredient> {
        return service.list()
    }

    @GET
    @Path("{id}")
    fun load(@PathParam("id") id: UUID): Ingredient {
        return service.load(id)
    }

    @POST
    fun save(ingredient: Ingredient): Ingredient {
        return service.save(ingredient)
    }

    @PUT
    @Path("{id}")
    fun update(@PathParam("id") id: UUID, ingredient: Ingredient): Ingredient {
        if (id != ingredient.id) {
            throw BadRequestException("Object with id: ${ingredient.id} cannot update resource with id: $id")
        }
        return service.update(ingredient)
    }
}
