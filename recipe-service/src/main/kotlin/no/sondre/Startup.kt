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
import no.sondre.domain.SQLRecipe
import no.sondre.repository.IngredientRepository
import no.sondre.repository.RecipeRepository
import no.sondre.repository.SQLIngredient
import java.util.*
import java.util.logging.Logger

@IfBuildProfile("dev")
@ApplicationScoped
class Startup {

    @Inject
    private lateinit var ingredientRepository: IngredientRepository

    @Inject
    private lateinit var recipeRepository: RecipeRepository

    private val logger = Logger.getLogger("Startup logger")

    @Transactional
    fun injectTestData(@Observes event: StartupEvent) {
        logger.info("Injecting some test data")
        val ingredients = listOf(
            SQLIngredient(UUID.randomUUID(), "ingredient one"),
            SQLIngredient(UUID.randomUUID(), "ingredient two")
        )
        val recipes = listOf(
            SQLRecipe(UUID.randomUUID(), "recipe one", youtube = "https://www.youtube.com/watch?v=JYg1UfVCfiw", ingredients = mutableListOf()),
            SQLRecipe(UUID.randomUUID(),"recipe two", ingredients = mutableListOf()),
            SQLRecipe(UUID.randomUUID(), "recipe three", ingredients = mutableListOf()),
            SQLRecipe(UUID.randomUUID(), "recipe four", ingredients = mutableListOf()),
            SQLRecipe(UUID.randomUUID(), "recipe five", ingredients = mutableListOf())
        )
        // Save initial recipe without ingredient (what we will have to do in the service)
        recipeRepository.persist(recipes)
        ingredientRepository.persist(ingredients)
//        recipes.forEach { r ->
//            r.addIngredient(RecipeIngredient(100, "dl", r.id, ingredient = ingredients[0].id))
//            r.addIngredient(RecipeIngredient(100, "dl", r.id, ingredient = ingredients[1].id))
//        }
    }
}
