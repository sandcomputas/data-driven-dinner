import {UseMutationResult} from "@tanstack/react-query";
import {useForm} from "@tanstack/react-form";

type Props = {
    recipe: Recipe | null
    mutation: UseMutationResult<Recipe, Error, Recipe, unknown>
}

export function RecipeForm({recipe, mutation}: Props) {

    const form = useForm({
        defaultValues: {
            id: recipe?.id ?? null,
            name: recipe?.name ?? "",
            youtube: recipe?.youtube ?? ""
        },
        onSubmit: ({value}) => {
            console.log(value.name, value.youtube)
            mutation.mutate(
                {
                    id: value.id ?? null,
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