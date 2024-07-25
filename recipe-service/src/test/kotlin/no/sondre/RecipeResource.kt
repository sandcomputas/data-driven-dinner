package no.sondre

import io.quarkus.test.InjectMock
import io.quarkus.test.junit.QuarkusTest
import io.restassured.RestAssured.given
import io.restassured.http.ContentType
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.Mockito
import java.util.*

@QuarkusTest
class RecipeResourceTest {
    val baseUrl = "/recipe"
    val recipes = listOf(
        Recipe("recipe one", UUID.randomUUID()),
        Recipe("recipe two", UUID.randomUUID())
    )

    @InjectMock
    lateinit var repo: RecipeRepository

    @BeforeEach
    fun mockRecipieRepository() {
        Mockito.`when`(repo.listAll()).thenReturn(recipes)
    }

    @Test
    fun `can list all recipes`() {
        val responseRecipes = given().contentType(ContentType.JSON)
            .`when`()
            .get(baseUrl)
            .then()
            .statusCode(200)
            .extract()
            .`as`(Array<Ingredient>::class.java).toList()
        assertEquals(2, responseRecipes.size)
    }
}