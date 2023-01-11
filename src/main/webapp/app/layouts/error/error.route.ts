import { Routes } from '@angular/router';

import { ErrorComponent } from './error.component';
import { NotFoundComponent } from './not-found.component';

export const errorRoute: Routes = [
  {
    path: 'error',
    component: ErrorComponent,
    data: {
      pageTitle: 'Error page!',
    },
  },
  {
    path: 'accessdenied',
    component: ErrorComponent,
    data: {
      pageTitle: 'Unauthorized Request!',
      errorMessage: 'You are not authorized to access this page.',
    },
  },
  {
    path: '404',
    component: NotFoundComponent,
    data: {
      pageTitle: 'Not Found page!',
      errorMessage: 'The page does not exist.',
    },
  },
  {
    path: '**',
    redirectTo: '/404',
  },
];
