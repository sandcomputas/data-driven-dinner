package no.sondre.domain

import jakarta.persistence.*
import no.sondre.repository.SQLModel
import no.sondre.repository.SQLModelCreator
import java.util.*

class Recipe(
    var name: String,
    var youtube: String? = null,
    val ingredients: MutableList<RecipeIngredient> = mutableListOf(),
) : Domain() {
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

@Entity
@Table(name = "recipes")
class SQLRecipe(
    @Id
    val id: UUID,
    var name: String,
    var youtube: String? = null,
    // TODO: not sure about the cascade option...
    //   I believe None is the correct option but need to test...
    @OneToMany(mappedBy = "recipe", cascade = [CascadeType.ALL], orphanRemoval = true, fetch = FetchType.EAGER)
    val ingredients: MutableList<RecipeIngredient> = mutableListOf()
) : SQLModel<Recipe> {
    companion object : SQLModelCreator<Recipe, SQLRecipe> {
        override fun fromPOJO(pojo: Recipe): SQLRecipe {
            return SQLRecipe(pojo.idSafe(), pojo.name, pojo.youtube, pojo.ingredients)
        }
    }

    override fun toPOJO(): Recipe {
        val pojo = Recipe(name, youtube, ingredients)
        pojo.withId(id)
        return pojo
    }
}
