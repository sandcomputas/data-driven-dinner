package no.sondre

import jakarta.inject.Inject
import jakarta.ws.rs.GET
import jakarta.ws.rs.POST
import jakarta.ws.rs.Path
import jakarta.ws.rs.PathParam
import java.util.UUID

@Path("recipe")
class RecipeResource {

    @Inject
    lateinit var repo: RecipeRepository

    @GET
    fun list(): List<Recipe> {
        return repo.listAll()
    }

    @GET
    @Path("{id}")
    fun load(@PathParam("id") id: UUID): Recipe {
        return repo.findByIdOrThrow(id)
    }

    @POST
    fun save(recipe: Recipe): Recipe {
        repo.persist(recipe)
        return recipe
    }
}
