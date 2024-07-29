package no.sondre

import io.quarkus.test.junit.QuarkusTest
import io.restassured.RestAssured.*
import io.restassured.http.ContentType
import jakarta.inject.Inject
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

    final val recipe = Recipe("recipe one", mutableListOf())

//    init {
//        recipes.forEach { r ->
//            r.ingredients.add(RecipeIngredient(100, "dl", r.id, ingredient = ingredients[0].id!!))
//            r.ingredients.add(RecipeIngredient(100, "dl", r.id, ingredient = ingredients[1].id!!))
//        }
//    }

    @Inject
    lateinit var repo: RecipeRepository

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
            saveIngredient(m)
        }
    }

    fun saveIngredient(i: Ingredient) {
        given()
            .contentType(ContentType.JSON)
            .body(i)
            .`when`()
            .post("ingredient")
            .then()
            .statusCode(HttpStatus.SC_OK)
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
            recipe.ingredients.add(RecipeIngredient(
                amount = Random.nextInt(0, 1000),
                unit = "freedom unit ${Random.nextInt(0, 1000)}",
                ingredient = it.id!! // !! should be fine sine the ingredient is loaded from DB
            ))
        }

        val response = given()
            .contentType(ContentType.JSON)
            .body(recipe)
            .`when`()
            .post(baseUrl)
            .then()
            .statusCode(HttpStatus.SC_OK)
        val respRecipe = response.extract().`as`(Recipe::class.java)
        assert(respRecipe.name == recipe.name) { "name must be same as name of input object"}

        assert(respRecipe.ingredients.filter {
            it.recipe == respRecipe.id
        }.size == recipe.ingredients.size) { "all added recipeIngredients must have been given a recipe ID that corresponds with the id of the Recipe" }
    }

    @Test
    fun `can list all recipes`() {
//        this.`can save recipe recipes`()
        val responseRecipes = given().contentType(ContentType.JSON)
            .`when`()
            .get(baseUrl)
            .then()
            .statusCode(200)
            .extract()
            .`as`(Array<Recipe>::class.java).toList()
        assertEquals(2, responseRecipes.size)
    }
}