package no.sondre

import io.quarkus.test.junit.QuarkusTest
import io.restassured.RestAssured.given
import io.restassured.http.ContentType
import jakarta.inject.Inject
import org.apache.http.HttpStatus
import org.hamcrest.Matchers.*
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import kotlin.random.Random

//https://www.baeldung.com/java-quarkus-testing

@QuarkusTest
class IngredientResourceTest {
    val baseUrl = "/ingredient"
    val ingredients: MutableList<Ingredient> = mutableListOf()

    @Inject
    lateinit var repo: IngredientRepository

    //    @BeforeEach
//    fun mockIngredientRepository() {
//        Mockito.`when`(repo.listAll()).thenReturn(ingredients)
//    }

    // TODO: up next -> write test that saves Ingredient to database
    // TODO: call this test at the start of the next test to verify that data has been saved to the DB and that list works

    @Test
    fun `can save new ingredient`() {
        val ingredient = Ingredient("Test Ingredient ${Random.nextInt(0,1000)}")
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
            .statusCode(200)
            .extract()
            .`as`(Array<Ingredient>::class.java).toList()
        assert(responseIngredients.size >= saves)
    }
}
