import {FC} from "react";
import styles from "./RecipeCard.module.css";


interface Props {
    recipe: Recipe
}

const RecipeCard: FC<Props> = ({recipe}) => {
    return (
        <div
            className={styles['recipe-card']}
        >
            <div>{recipe.id} | {recipe.name}</div>
        </div>

    )
}

export default RecipeCard;