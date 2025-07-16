import { UseMutationResult } from "@tanstack/react-query";
import { useForm } from "@tanstack/react-form";
// @ts-ignore
import styles from "./RecipeForm.module.css";

type Props = {
  recipe: Recipe | null
  mutation: UseMutationResult<Recipe, Error, Recipe, unknown>
  isOpen: boolean;
  closeNewRecipeModal: () => void
}

export function RecipeForm({recipe, mutation, isOpen, closeNewRecipeModal}: Props) {

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
      closeNewRecipeModal()
    }
  })

  if (!isOpen) return null

  return (
    <div className={styles["modal-overlay"]}>
      <div className={styles["modal-content"]}>
        <h2>{recipe ? "Edit Recipe" : "Add New Recipe"}</h2>
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
        <button onClick={() => closeNewRecipeModal()}>Lukk</button>
        <button onClick={form.handleSubmit}>Lagre</button>
      </div>
    </div>
  )
}