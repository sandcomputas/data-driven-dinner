package no.sondre.domain

class Ingredient(var name: String) : Domain() {
    private val secretField: String = "hello"
}
