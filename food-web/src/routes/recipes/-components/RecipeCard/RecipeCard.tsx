import {FC} from "react";
import styles from "./RecipeCard.module.css";
import {Link} from "@tanstack/react-router";
import image from "../../../../assets/generic_food.png"


interface Props {
    recipe: Recipe
}

const RecipeCard: FC<Props> = ({recipe}) => {

    return (
        <Link
            className={styles['recipe-card']}
        >
            <img className={styles['recipe-card-image']} src={image}/>
            <div>{recipe.name}</div>
        </Link>

    )
}

export default RecipeCard;