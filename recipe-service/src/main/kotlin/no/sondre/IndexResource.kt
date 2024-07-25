package no.sondre

import jakarta.ws.rs.GET
import jakarta.ws.rs.Path

@Path("/")
class IndexResource {

    @GET
    fun index(): String {
        return "This is Recipe Service speaking"
    }
}
