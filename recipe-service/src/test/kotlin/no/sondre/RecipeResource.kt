package no.sondre

import io.quarkus.test.junit.QuarkusTest
import io.restassured.RestAssured.given
import io.restassured.http.ContentType
import jakarta.inject.Inject
import org.apache.http.HttpStatus
import org.hamcrest.Matchers.*
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import java.util.*

//import io.restassured.RestAssured.*

@QuarkusTest
class RecipeResourceTest {
    val baseUrl = "/recipe"
    private val ingredients = listOf(
        Ingredient("ingredient one"),
        Ingredient("ingredient two")
    )

    final val recipes = listOf(
        Recipe("recipe one", mutableListOf()),
        Recipe("recipe two", mutableListOf())
    )

//    init {
//        recipes.forEach { r ->
//            r.ingredients.add(RecipeIngredient(100, "dl", r.id, ingredient = ingredients[0].id!!))
//            r.ingredients.add(RecipeIngredient(100, "dl", r.id, ingredient = ingredients[1].id!!))
//        }
//    }

    @Inject
    lateinit var repo: RecipeRepository

    // Don't think I want to mock out the persistence layer
//    @InjectMock
//    lateinit var repo: RecipeRepository
//    @BeforeEach
//    fun mockRecipieRepository() {
//        Mockito.`when`(repo.listAll()).thenReturn(recipes)
//    }

    @Test
    fun `can save recipe recipes`() {
        val response = given()
            .contentType(ContentType.JSON)
            .body(recipes[0])
            .`when`()
            .post(baseUrl)
            .then()
            .statusCode(HttpStatus.SC_OK)
            .body(
                "id", notNullValue(),
                "name", `is`(
                    recipes[0].name,
                )
            )
        // Deserialize example, not strictly needed here
        val respRecipe = response.extract().`as`(Recipe::class.java)
        assertEquals(respRecipe.name, recipes[0].name)
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