package no.sondre

import jakarta.inject.Inject
import jakarta.ws.rs.*
import java.util.*

@Path("recipe")
class RecipeResource {

    @Inject
    lateinit var service: RecipeService

    @GET
    fun list(): List<Recipe> {
        return service.list()
    }

    @GET
    @Path("{id}")
    fun load(@PathParam("id") id: UUID): Recipe {
        return service.load(id)
    }

    @POST
    fun save(recipe: Recipe): Recipe {
        return service.save(recipe)
    }

    @PUT
    @Path("{id}")
    fun update(@PathParam("id") id: UUID, recipe: Recipe): Recipe {
        if (id != recipe.id) {
            throw BadRequestException("Object with id: ${recipe.id} cannot update resource with id: $id")
        }
        return service.update(recipe)
    }
}
