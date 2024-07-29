package no.sondre

import jakarta.inject.Inject
import jakarta.transaction.Transactional
import jakarta.ws.rs.GET
import jakarta.ws.rs.POST
import jakarta.ws.rs.Path
import jakarta.ws.rs.PathParam
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
}
