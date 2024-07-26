package no.sondre

import jakarta.persistence.*
import jakarta.validation.constraints.NotNull
import java.util.UUID

@Entity
@Table(name = "recipes")
class Recipe(

    @NotNull
    val name: String,

    // TODO: not sure about the cascade option...
    @OneToMany(mappedBy = "recipe", cascade = [CascadeType.ALL], orphanRemoval = true)
    val ingredients: MutableList<RecipeIngredient> = mutableListOf(),

) {
    @Id
    val id: UUID = UUID.randomUUID()

    // Properties must be changed using a function so that Hibernate proxy will work
    // https://stackoverflow.com/questions/64503946/update-entity-data-using-quarkus-and-panacherepository-is-not-working
    // this is only fair code-wise, however if the entity extends PanacheEntity it will provide getters/setters behind the scene
    // TOOD: consider making all fields private to avoid such mistakes/bad pattern
    fun addIngredient(i: RecipeIngredient) {
        ingredients.add(i)
    }
}
