import {createFileRoute, useNavigate} from "@tanstack/react-router"
import {RecipeForm} from "@/components/RecipeForm/RecipeForm.tsx";
import {useMutation} from "@tanstack/react-query";

export const Route = createFileRoute("/newRecipe/")({
    component: NewRecipe
})

function NewRecipe() {

    const navigate = useNavigate()

    const mutation = useMutation<Recipe, Error, Recipe, unknown>({
        mutationFn: async (recipe): Promise<Recipe> => {
            return fetch("http://localhost:8080/recipe", {
                method: "POST",
                headers: {"Content-Type": "application/json"},
                body: JSON.stringify(recipe)
            }).then((res) => res.json())
        },
        onSuccess: () => {
            navigate({to: '/recipes'})
        },
        onError: () => {
            console.log("Something went wrong...")
        }
    })

    return (
        <RecipeForm recipe={null} mutation={mutation} />
    )
}

