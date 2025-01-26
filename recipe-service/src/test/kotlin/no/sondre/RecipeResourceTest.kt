package no.sondre

import io.quarkus.test.junit.QuarkusTest
import io.restassured.RestAssured.*
import io.restassured.http.ContentType
import no.sondre.domain.Ingredient
import no.sondre.domain.Recipe
import no.sondre.domain.RecipeIngredient
import org.apache.http.HttpStatus
import org.hamcrest.Matchers.*
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import kotlin.random.Random

@QuarkusTest
class RecipeResourceTest {
    val baseUrl = "/recipe"
    private val ingredients = listOf(
        Ingredient("ingredient one"),
        Ingredient("ingredient two")
    )

    private final val recipe = Recipe("recipe one", ingredients = mutableListOf())

    fun assureIngredientsExists() {
        val savedIngredients = given().contentType(ContentType.JSON)
            .`when`()
            .get("ingredient")
            .then()
            .statusCode(200)
            .extract()
            .`as`(Array<Ingredient>::class.java).toList()
        val missing = ingredients.filter { i ->
            i.name !in savedIngredients.map { it.name }
        }
        for (m in missing) {
            val saved = saveIngredient(m)
            m.id = saved.idSafe()
        }
    }

    fun saveIngredient(i: Ingredient): Ingredient {
        return given()
            .contentType(ContentType.JSON)
            .body(i)
            .`when`()
            .post("ingredient")
            .then()
            .statusCode(HttpStatus.SC_OK)
            .extract()
            .`as`(Ingredient::class.java)
    }

    fun listIngredients(): List<Ingredient> {
        val ingredients = given()
            .contentType(ContentType.JSON)
            .`when`()
            .get("ingredient")
            .then()
            .statusCode(HttpStatus.SC_OK)
            .extract().`as`(Array<Ingredient>::class.java).toList()
        return ingredients
    }

    fun listRecipes(): List<Recipe> {
        return given().contentType(ContentType.JSON)
            .`when`()
            .get(baseUrl)
            .then()
            .statusCode(200)
            .extract()
            .`as`(Array<Recipe>::class.java).toList()
    }

    @Test
    fun `can save recipe without ingredients`() {
        val response = given()
            .contentType(ContentType.JSON)
            .body(recipe)
            .`when`()
            .post(baseUrl)
            .then()
            .statusCode(HttpStatus.SC_OK)
            .body(
                "id", notNullValue(),
                "name", `is`(
                    recipe.name,
                )
            )
        // Deserialize example, not strictly needed here
        val respRecipe = response.extract().`as`(Recipe::class.java)
        assertEquals(respRecipe.name, recipe.name)
    }

    @Test
    fun `can save recipe with ingredients`() {
        assureIngredientsExists()
        val ingredients = listIngredients()

        // Watch out for the commonly used Recipe object in all the tests
        ingredients.forEach {
            recipe.addIngredient(
                RecipeIngredient(
                    amount = Random.nextInt(0, 1000),
                    unit = "freedom unit ${Random.nextInt(0, 1000)}",
                    ingredient = it.idSafe()
                )
            )
        }

        val response = given()
            .contentType(ContentType.JSON)
            .body(recipe)
            .`when`()
            .post(baseUrl)
            .then()
            .statusCode(HttpStatus.SC_OK)
        val respRecipe = response.extract().`as`(Recipe::class.java)
        assert(respRecipe.name == recipe.name) { "name must be same as name of input object" }

        assert(respRecipe.loadIngredients().filter {
            it.recipe == respRecipe.id
        }.size == recipe.loadIngredients().size) { "all added recipeIngredients must have been given a recipe ID that corresponds with the id of the Recipe" }
    }

    @Test
    fun `can list all recipes`() {
        `can save recipe without ingredients`()
        val responseRecipes = listRecipes()
        assert(responseRecipes.isNotEmpty()) { "There should be a least one saved recipe" }
    }

    @Test
    fun `can update recipe`() {
        `can save recipe with ingredients`()
        val recipe = listRecipes()[0]
        val newName = "new name"
        recipe.name = newName
        recipe.addIngredient(RecipeIngredient(12345, "freedomUnit2", recipe.id, ingredients[0].idSafe()))

        val response = given()
            .contentType(ContentType.JSON)
            .body(recipe)
            .`when`()
            .put(baseUrl + "/${recipe.id}")
            .then()
            .statusCode(HttpStatus.SC_OK)
        val respRecipe = response.extract().`as`(Recipe::class.java)
        assertEquals(respRecipe.name, newName)
        val newIngredient = recipe.loadIngredients().filter { it.amount == 12345 }
        assert(newIngredient.size == 1)
    }
}