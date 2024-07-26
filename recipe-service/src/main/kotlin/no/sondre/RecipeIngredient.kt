package no.sondre

import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.IdClass
import java.util.*


@Entity
@IdClass(RecipeIngredientId::class)
class RecipeIngredient(

    val amount: Int,
    val unit: String, // TODO: make enum

    @Id
    val recipe: UUID,
    @Id
    val ingredient: UUID
)
