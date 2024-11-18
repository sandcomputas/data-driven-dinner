import { createFileRoute } from "@tanstack/react-router"

export const Route = createFileRoute("/newRecipe/")({
    component: NewRecipe
})

function NewRecipe() {
    return <div>so... you have a new idea for a recipe I hear?</div>
}
