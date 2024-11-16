import {FC} from "react";
import {useQuery} from "@tanstack/react-query";
import RecipeCard from "@/routes/recipes/-components/RecipeCard/RecipeCard.tsx";
import styles from './RecipeList.module.css'

const RecipeList: FC = () => {
    const {isPending, error, data, isFetching} = useQuery<Recipe[]>({
        queryKey: ['data'],
        queryFn: async (): Promise<Recipe[]> => {
            return fetch('http://localhost:8080/recipe').then((res) => res.json())
        },
    })

    if (isPending) return 'Loading'
    if (error) return 'An error has occured: ' + error.message
    return (
        <div className={styles['recipe-list']}>
            {data.map((r) => (
                <div key={r.id}>
                    <RecipeCard recipe={r}/>
                </div>
            ))}
        </div>
    )
}

export default RecipeList;