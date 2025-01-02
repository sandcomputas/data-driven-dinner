package no.sondre.resources

import jakarta.inject.Inject
import jakarta.ws.rs.*
import jakarta.ws.rs.core.Response
import no.sondre.domain.Ingredient
import no.sondre.services.IngredientService
import org.jboss.resteasy.reactive.RestResponse
import org.jboss.resteasy.reactive.server.ServerExceptionMapper
import java.util.*

@Path("ingredient")
class IngredientResource {

    @ServerExceptionMapper
    fun mapException(e: Exception): RestResponse<String> {
        // TODO: all exceptions are now mapped to not found, this should not be the case
        return RestResponse.status(Response.Status.NOT_FOUND, e.message)
    }

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
