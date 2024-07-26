package no.sondre

import jakarta.persistence.Embeddable
import java.io.Serializable
import java.util.UUID

@Embeddable
class RecipeIngredientId(
    val recipe: UUID? = null,
    val ingredient: UUID
) : Serializable {}
