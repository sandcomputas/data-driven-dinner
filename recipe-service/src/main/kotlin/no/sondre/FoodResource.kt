package no.sondre

import jakarta.inject.Inject
import jakarta.ws.rs.GET
import jakarta.ws.rs.POST
import jakarta.ws.rs.Path

@Path("food")
class FoodResource {

    @Inject
    lateinit var foodRepository: FoodRepository

    @GET
    fun foods(): List<Food> {
        return foodRepository.listAll()
    }

    @POST
    fun new(food: Food): Food {
        foodRepository.persist(food)
        return food
    }
}