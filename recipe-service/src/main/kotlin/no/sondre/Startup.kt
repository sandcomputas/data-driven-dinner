package no.sondre

import io.quarkus.arc.profile.IfBuildProfile
import io.quarkus.runtime.StartupEvent
import jakarta.enterprise.context.ApplicationScoped
import jakarta.enterprise.event.Observes
import jakarta.inject.Inject
import jakarta.transaction.Transactional
import no.sondre.domain.Ingredient
import no.sondre.domain.Recipe
import no.sondre.domain.RecipeIngredient
import no.sondre.repository.IngredientRepository
import no.sondre.repository.RecipeRepository
import java.util.logging.Logger

@IfBuildProfile("dev")
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
            Recipe("recipe one", youtube = "https://www.youtube.com/watch?v=JYg1UfVCfiw", ingredients = mutableListOf()),
            Recipe("recipe two", ingredients = mutableListOf()),
            Recipe("recipe three", ingredients = mutableListOf()),
            Recipe("recipe four", ingredients = mutableListOf()),
            Recipe("recipe five", ingredients = mutableListOf())
        )
        // Save initial recipe without ingredient (what we will have to do in the service)
        recipeRepository.persist(recipes)
        ingredientRepository.persist(ingredients)
        recipes.forEach { r ->
            r.addIngredient(RecipeIngredient(100, "dl", r.id, ingredient = ingredients[0].id))
            r.addIngredient(RecipeIngredient(100, "dl", r.id, ingredient = ingredients[1].id))
        }
    }
}
