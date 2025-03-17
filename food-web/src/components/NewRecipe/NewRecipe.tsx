import {useNavigate} from "@tanstack/react-router";
import {useMutation} from "@tanstack/react-query";
import {RecipeForm} from "@/components/RecipeForm/RecipeForm.tsx";

type Props = {
    isOpen: boolean
    setIsOpen: (open: boolean) => void
}

function NewRecipe({isOpen, setIsOpen}: Props) {

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
        <RecipeForm recipe={null} mutation={mutation} isOpen={isOpen} setIsOpen={setIsOpen}/>
    )
}

export default NewRecipe;