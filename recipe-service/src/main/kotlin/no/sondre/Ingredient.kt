package no.sondre

import jakarta.persistence.*
import jakarta.validation.constraints.NotNull
import java.util.UUID

@Entity
@Table(name = "ingredients")
class Ingredient(

    @NotNull
    val name: String,

) {
    @Id
    var id: UUID = UUID.randomUUID()
}
