package no.sondre

import io.quarkus.test.junit.QuarkusTest
import io.restassured.RestAssured.given
import io.restassured.http.ContentType
import no.sondre.domain.Ingredient
import org.apache.http.HttpStatus
import org.hamcrest.Matchers.*
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import java.util.*
import kotlin.random.Random

//https://www.baeldung.com/java-quarkus-testing

@QuarkusTest
class IngredientResourceTest {
    val baseUrl = "/ingredient"
    val ingredients: MutableList<Ingredient> = mutableListOf()

    //    @BeforeEach
//    fun mockIngredientRepository() {
//        Mockito.`when`(repo.listAll()).thenReturn(ingredients)
//    }

    @Test
    fun `can save new ingredient`() {
        val ingredient = Ingredient("Test Ingredient ${Random.nextInt(0, 1000)}")
        val response = given()
            .contentType(ContentType.JSON)
            .body(ingredient)
            .`when`()
            .post(baseUrl)
            .then()
            .statusCode(HttpStatus.SC_OK)
            .body(
                "id", notNullValue(),
            )
        val respIngredient = response.extract().`as`(Ingredient::class.java)
        ingredients.add(respIngredient)
        assertEquals(respIngredient.name, ingredients.last().name)
    }

    @Test
    fun `can list all ingredients`() {
        val saves = 3
        for (i in 1..saves) {
            `can save new ingredient`()
        }
        val responseIngredients = given().contentType(ContentType.JSON)
            .`when`()
            .get(baseUrl)
            .then()
            .statusCode(HttpStatus.SC_OK)
            .extract()
            .`as`(Array<Ingredient>::class.java).toList()
        assert(responseIngredients.size >= saves)
    }

    @Test
    fun `bad request when object id does not correspond to resource id on PUT`() {
        val ingredient = Ingredient(name = "strawberry")
        given().contentType(ContentType.JSON)
            .body(ingredient)
            .`when`()
            .put(baseUrl + "/" + UUID.randomUUID())
            .then()
            .statusCode(HttpStatus.SC_BAD_REQUEST)
    }

    @Test
    fun `cannot update non-existing ingredient`() {
        `can save new ingredient`()
        val ingredient = Ingredient(name = "strawberry")
        given().contentType(ContentType.JSON)
            .body(ingredient)
            .`when`()
            .put(baseUrl + "/" + ingredient.id)
            .then()
            .statusCode(HttpStatus.SC_NOT_FOUND)
    }

    @Test
    fun `can update ingredient`() {
        `can save new ingredient`()
        val ingredient = ingredients.last()
        ingredient.name = "new name"
        given().contentType(ContentType.JSON)
            .body(ingredient)
            .`when`()
            .put(baseUrl + "/" + ingredient.id)
            .then().statusCode(HttpStatus.SC_OK)

        val updatedIngredient = given().contentType(ContentType.JSON).`when`().get(baseUrl + "/" + ingredient.id)
            .then().statusCode(HttpStatus.SC_OK).extract().`as`(Ingredient::class.java)
        assertEquals(ingredient.name, updatedIngredient.name)
    }
}
