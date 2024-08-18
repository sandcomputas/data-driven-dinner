package no.sondre

import jakarta.persistence.*
import jakarta.validation.constraints.NotNull
import java.util.*

@Entity
@Table(name = "recipes")
class Recipe (

    @NotNull
    var name: String,

    // TODO: not sure about the cascade option...
    //   I believe None is the correct option but need to test...
    @OneToMany(mappedBy = "recipe", cascade = [CascadeType.ALL], orphanRemoval = true, fetch = FetchType.EAGER)
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

    fun loadIngredients(): List<RecipeIngredient> {
        return ingredients.toList()
    }

    fun complete() {
        ingredients.forEach {
            it.complete(id)
        }
    }

    fun update(new: Recipe) {
        name = new.name
        ingredients.clear()
        ingredients.addAll(new.ingredients)
    }
}
