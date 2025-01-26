import {createFileRoute} from '@tanstack/react-router'
import {useQuery} from "@tanstack/react-query";
import renderYoutube from "@/components/EmbeddedYoutube/EmbeddedYoutube.tsx";

export const Route = createFileRoute('/recipes/$recipeId/')({
    component: RecipeId,
})

function RecipeId() {
    const {recipeId} = Route.useParams()
    const {isPending, error, data, isFetching} = useQuery<Recipe>({
        queryKey: ['recipe', recipeId],
        queryFn: async (): Promise<Recipe> => {
            return fetch(`http://localhost:8080/recipe/${recipeId}`).then((res) => res.json())
        }
    })

    if (isPending || isFetching) return <div>Loading...</div>
    if (error) return <div>Ups! Something has gone wrong here...</div>
    return (
        <div>
            <h1>{data.name}</h1>
            {data.youtube != undefined && renderYoutube(data.youtube)}
        </div>
    )
}
