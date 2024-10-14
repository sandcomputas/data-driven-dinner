import {Component, Input} from '@angular/core';
import {Recipe} from '../../recipe.interface';
import {MatCard, MatCardHeader} from '@angular/material/card';

@Component({
  selector: 'app-recipe-box',
  standalone: true,
  imports: [
    MatCard,
    MatCardHeader
  ],
  templateUrl: './recipe-box.component.html',
  styleUrl: './recipe-box.component.css'
})
export class RecipeBoxComponent {
  @Input() recipe!: Recipe;
}
