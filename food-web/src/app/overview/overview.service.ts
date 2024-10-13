import {Observable} from 'rxjs';
import {HttpClient} from '@angular/common/http';
import {Recipe} from '../recipe.interface';
import {Injectable} from '@angular/core';

@Injectable({providedIn: 'root'})
export class OverviewService {

  private baseUrl = 'http://localhost:8080/recipe'

  constructor(private http: HttpClient) {}

  getRecipes(): Observable<Recipe[]> {
    return this.http.get<Recipe[]>(this.baseUrl)
  }
}
