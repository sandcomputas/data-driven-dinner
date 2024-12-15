package no.sondre.domain

import com.fasterxml.jackson.annotation.JsonIgnore
import no.sondre.InternalError
import java.util.*

abstract class Domain {
    // TODO: this should not be handled with annotation
    //   should be a way to customize Jackson/OpenAPI
    //   but it seems like the issue is that Kotlin generates
    //   getters and setters automatically, which OpenAPI then reads
//    @JsonIgnore
    private lateinit var _id: UUID

    val id: UUID
        get() {
            assertId()
            return _id
        }

    fun initNew() {
        assertNoId()
        _id = UUID.randomUUID()
    }

//    @get:JsonIgnore
    private val hasId: Boolean
        get() = this::_id.isInitialized

    private fun assertId() {
        if (!hasId) {
            throw InternalError("No id set on object")
        }
    }

    private fun assertNoId() {
        if (hasId) {
            throw InternalError("Id already set on object")
        }
    }

    fun withId(id: UUID) {
        if (!hasId) this._id = id
    }
}
