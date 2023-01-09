import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { PlayerComponent } from '../../player/list/player.component';
import { UserRouteAccessService } from '../../../core/auth/user-route-access.service';
import { PlayerDetailComponent } from '../../player/detail/player-detail.component';
import { PlayerUpdateComponent } from '../../player/update/player-update.component';
import { UserComponent } from '../list/user.component';
import { UserDetailComponent } from '../detail/user-detail.component';
import { UserUpdateComponent } from '../update/user-update.component';
import { UserRoutingResolveService } from './user-routing-resolve';

const userRoute: Routes = [
  {
    path: '',
    component: UserComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: UserDetailComponent,
    resolve: {
      user: UserRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: UserUpdateComponent,
    resolve: {
      user: UserRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: UserUpdateComponent,
    resolve: {
      user: UserRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

import { ASC } from 'app/config/navigation.constants';

@NgModule({
  imports: [RouterModule.forChild(userRoute)],
  exports: [RouterModule],
})
export class UserRoutingModule {}
