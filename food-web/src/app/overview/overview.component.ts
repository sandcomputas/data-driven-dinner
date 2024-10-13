import { Component } from '@angular/core';
import {Recipe} from "../recipe.interface";
import {OverviewService} from "./overview.service";

@Component({
  selector: 'app-overview',
  standalone: true,
  imports: [],
  templateUrl: './overview.component.html',
  styleUrl: './overview.component.css'
})
export class OverviewComponent {
  recipes: Recipe[] = []

  constructor(private overviewService: OverviewService) {}

  ngOnInit(): void {
    console.log("init")
    this.overviewService.getRecipes().subscribe((recipes) => {
      this.recipes = recipes
    })
  }
}
