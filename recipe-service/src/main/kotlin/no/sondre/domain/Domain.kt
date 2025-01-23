package no.sondre.domain

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import jakarta.ws.rs.InternalServerErrorException
import java.util.*

@NoArg
abstract class Domain {
    var id: UUID? = null

    fun initNew() {
        assertNoId()
        id = UUID.randomUUID()
    }

    private val hasId: Boolean
        get() = id != null

    fun idSafe(): UUID {
        assertId()
        return id!!
    }

    fun assertId() {
        if (!hasId) {
            throw InternalServerErrorException("No id set on object")
        }
    }

    private fun assertNoId() {
        if (hasId) {
            throw InternalServerErrorException("Id already set on object")
        }
    }

    fun withId(id: UUID) {
        if (!hasId) this.id = id
    }
}
