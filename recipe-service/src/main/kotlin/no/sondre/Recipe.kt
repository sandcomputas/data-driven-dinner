package no.sondre

import jakarta.persistence.*
import jakarta.validation.constraints.NotNull
import java.util.UUID

@Entity
@Table(name = "recipes")
class Recipe(

    @NotNull
    val name: String,

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    var id: UUID? = null
)
