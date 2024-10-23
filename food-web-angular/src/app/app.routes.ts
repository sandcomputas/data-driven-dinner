import { Routes } from '@angular/router';
import {OverviewComponent} from './overview/overview.component';
import {TestComponent} from './test/test.component';

export const routes: Routes = [
  {
    path: '',
    title: 'Recipes',
    component: OverviewComponent
  },
  {
    path: 'test',
    title: 'Test',
    component: TestComponent
  }
];
