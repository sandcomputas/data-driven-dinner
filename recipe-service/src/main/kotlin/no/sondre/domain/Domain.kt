package no.sondre.domain

import jakarta.ws.rs.InternalServerErrorException
import java.util.*

abstract class Domain {
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

    private val hasId: Boolean
        get() = this::_id.isInitialized

    private fun assertId() {
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
        if (!hasId) this._id = id
    }
}
