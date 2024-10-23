import {RecipeIngredient} from './recipeIngredient.interface';

export interface Recipe {
  name: string,
  id: string,
  ingredients: RecipeIngredient[]
}
