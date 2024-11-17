import {createFileRoute} from '@tanstack/react-router'
import {useQuery} from "@tanstack/react-query";

interface Params {
    recipeId: string;
}

export const Route = createFileRoute('/recipes/$recipeId/')({
    component: RouteComponent,
    // loader: async ({params}: { params: Params }) => {

})

function RouteComponent() {
    const {recipeId} = Route.useParams()
    const {isPending, error, data, isFetching} = useQuery<Recipe>({
        queryKey: ['recipe'],
        queryFn: async (): Promise<Recipe> => {
            return fetch(`http://localhost:8080/recipe/${recipeId}`).then((res) => res.json())
        }
    })
    if (isPending || isFetching) return <div>Loading...</div>
    if (error) return <div>Ups! Something has gone wrong here...</div>
    return <div>{data.name}</div>
}
