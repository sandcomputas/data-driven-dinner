import {createFileRoute, useNavigate} from "@tanstack/react-router"
import {useForm} from "@tanstack/react-form";
import {useMutation} from "@tanstack/react-query";

export const Route = createFileRoute("/newRecipe/")({
    component: NewRecipe
})

function NewRecipe() {
    const navigate = useNavigate()

    const mutation = useMutation<Recipe, unknown, Recipe>({
        mutationFn: async (recipe): Promise<Recipe> => {
            return fetch("http://localhost:8080/recipe", {
                method: "POST",
                headers: {"Content-Type": "application/json"},
                body: JSON.stringify(recipe)
            }).then((res) => res.json())
        },
        onSuccess: () => {
            console.log("succeessssss")
            navigate({to: '/recipes'})
        },
        onError: () => {
            console.log("Something went wrong...")
        }
    })

    const form = useForm({
        defaultValues: {
            name: "",
            youtube: ""
        },
        onSubmit: ({value}) => {
            console.log(value.name, value.youtube)
            mutation.mutate(
                {
                    id: null,
                    name: value.name,
                    youtube: value.youtube,
                    ingredients: []
                },
            )
        }
    })


    return (
        <div>
            <form.Field
                name="name"
                children={(field) => (
                    <div>
                        <label>Tittel</label>
                        <input
                            type="text"
                            value={field.state.value}
                            onChange={(e) => field.handleChange(e.target.value)}
                        />
                    </div>
                )}
            />
            <form.Field
                name="youtube"
                children={(field) => (
                    <div>
                        <label>YouTube Link</label>
                        <input
                            type="text"
                            value={field.state.value}
                            onChange={(e) => field.handleChange(e.target.value)}
                        />
                    </div>
                )}
            />

            <button onClick={form.handleSubmit}>Lagre</button>
        </div>
    )
}
