package no.sondre

import io.quarkus.test.InjectMock
import io.quarkus.test.junit.QuarkusTest
import io.restassured.RestAssured.given
import io.restassured.http.ContentType
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.Mockito
import java.util.*
import org.junit.jupiter.api.Assertions.assertEquals

//https://www.baeldung.com/java-quarkus-testing

@QuarkusTest
class IngredientResourceTest {
    val baseUrl = "/ingredient"
    val ingredients = listOf(Ingredient("food one", UUID.randomUUID()), Ingredient("food two", UUID.randomUUID()))

    @InjectMock
    lateinit var repo: IngredientRepository

    @BeforeEach
    fun mockIngredientRepository() {
        Mockito.`when`(repo.listAll()).thenReturn(ingredients)
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

    @Test
    fun `can save new ingredient`() {
//       given().contentType(ContentType.JSON)


    }


}