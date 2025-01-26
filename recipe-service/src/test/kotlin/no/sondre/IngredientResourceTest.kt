package no.sondre

import com.fasterxml.jackson.databind.ObjectMapper
import io.quarkus.test.junit.QuarkusTest
import io.restassured.RestAssured.given
import io.restassured.http.ContentType
import jakarta.inject.Inject
import no.sondre.domain.Ingredient
import no.sondre.domain.toJsonString
import org.apache.http.HttpStatus
import org.hamcrest.Matchers.*
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import java.util.*

//https://www.baeldung.com/java-quarkus-testing

@QuarkusTest
class IngredientResourceTest {
    val baseUrl = "/ingredient"

    @Inject
    lateinit var mapper: ObjectMapper
    //    @BeforeEach
//    fun mockIngredientRepository() {
//        Mockito.`when`(repo.listAll()).thenReturn(ingredients)
//    }

    @Test
    fun `can save new ingredient`() {
//        val ingredientMap = mutableMapOf<String, String>()
//        ingredientMap["name"] = "Test Ingredient: ${Random.nextInt(0, 100_000)}"
        val ingredient = Ingredient(name = "Test ingredient")
//        val json = mapper.writer().withDefaultPrettyPrinter().writeValueAsString(ingredient)
        val json = ingredient.toJsonString(mapper)
        val response = given()
            .contentType(ContentType.JSON)
//            .body(ingredientMap)
            .body(json)
            .`when`()
            .post(baseUrl)
            .then()
            .statusCode(HttpStatus.SC_OK)
            .body(
                "id", notNullValue(),
            )
        val respIngredient = response.extract().`as`(Ingredient::class.java)
//        assertEquals(respIngredient.name, ingredientMap["name"])
        assertEquals(respIngredient.name, ingredient.name)
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
        ingredient.withId(UUID.randomUUID())
        given().contentType(ContentType.JSON)
            .body(ingredient.toJsonString(mapper))
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
        val ingredient = saveIngredient(Ingredient(name = "original name"))
        ingredient.name = "new name"
        given().contentType(ContentType.JSON)
            .body(ingredient.toJsonString(mapper))
            .`when`()
            .put(baseUrl + "/" + ingredient.id)
            .then().statusCode(HttpStatus.SC_OK)

        val updatedIngredient = given()
            .contentType(ContentType.JSON)
            .`when`()
            .get(baseUrl + "/" + ingredient.id)
            .then().statusCode(HttpStatus.SC_OK)
            .extract()
            .`as`(Ingredient::class.java)
        assertEquals(ingredient.name, updatedIngredient.name)
    }

    private fun saveIngredient(i: Ingredient): Ingredient {
        return given()
            .contentType(ContentType.JSON)
            .body(i.toJsonString(mapper))
            .`when`()
            .post("/ingredient")
            .then()
            .statusCode(HttpStatus.SC_OK)
            .extract()
            .`as`(Ingredient::class.java)
    }
}
