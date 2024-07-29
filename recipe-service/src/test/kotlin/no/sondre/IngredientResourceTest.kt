package no.sondre

import io.quarkus.test.InjectMock
import io.quarkus.test.junit.QuarkusTest
import io.restassured.RestAssured.given
import io.restassured.http.ContentType
import jakarta.inject.Inject
import jakarta.validation.constraints.NotNull
import org.apache.http.HttpStatus
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.Mockito
import java.util.*
import org.junit.jupiter.api.Assertions.assertEquals

import org.hamcrest.Matchers.*
//https://www.baeldung.com/java-quarkus-testing

@QuarkusTest
class IngredientResourceTest {
    val baseUrl = "/ingredient"
    val ingredients = listOf(Ingredient("food one"), Ingredient("food two"))

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
       val response = given()
           .contentType(ContentType.JSON)
           .body(ingredients[0])
           .`when`()
           .post(baseUrl)
           .then()
           .statusCode(HttpStatus.SC_OK)
           .body(
              "id", notNullValue(),
               "name", `is`(ingredients[0].name)
           )
        val respIngredient = response.extract().`as`(Ingredient::class.java)
        assertEquals(respIngredient.name, ingredients[0].name)
    }

    @Test
    fun `can list all ingredients`() {
        val responseIngredients = given().contentType(ContentType.JSON)
            .`when`()
            .get(baseUrl)
            .then()
            .statusCode(200)
            .extract()
            .`as`(Array<Ingredient>::class.java).toList()
        assertEquals(2, responseIngredients.size)
    }


}