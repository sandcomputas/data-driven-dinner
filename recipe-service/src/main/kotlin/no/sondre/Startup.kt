package no.sondre

import io.quarkus.runtime.StartupEvent
import jakarta.enterprise.context.ApplicationScoped
import jakarta.enterprise.event.Observes
import jakarta.inject.Inject
import jakarta.transaction.Transactional
import java.util.logging.Logger

@ApplicationScoped
class Startup {

    @Inject
    lateinit var ingredientRepository: IngredientRepository

    @Inject
    lateinit var recipeRepository: RecipeRepository

    private val logger = Logger.getLogger("Startup logger")

    @Transactional
    fun injectTestData(@Observes event: StartupEvent) {
        logger.info("Injecting some test data")
        val ingredients = listOf(
            Ingredient("ingredient one"),
            Ingredient("ingredient two")
        )
        val recipes = listOf(
            Recipe("recipe one", mutableListOf()),
            Recipe("recipe two", mutableListOf())
        )
        // Save initial recipe without ingredient (what we will have to do in the service)
        recipeRepository.persist(recipes)
        ingredientRepository.persist(ingredients)
        recipes.forEach { r ->
            r.addIngredient(RecipeIngredient(100, "dl", r.id, ingredient = ingredients[0].id!!))
            r.addIngredient(RecipeIngredient(100, "dl", r.id, ingredient = ingredients[1].id!!))
        }
    }
}
