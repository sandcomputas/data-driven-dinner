import {FC} from "react";
import styles from "./RecipeCard.module.css";
import {Link} from "@tanstack/react-router";


interface Props {
    recipe: Recipe
}

const RecipeCard: FC<Props> = ({recipe}) => {
    return (
        <Link
            className={styles['recipe-card']}
        >
            <div>{recipe.id} | {recipe.name}</div>
        </Link>

    )
}

export default RecipeCard;