import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { PlayerGameBadgeComponent } from '../list/player-game-badge.component';
import { PlayerGameBadgeDetailComponent } from '../detail/player-game-badge-detail.component';
import { PlayerGameBadgeUpdateComponent } from '../update/player-game-badge-update.component';
import { PlayerGameBadgeRoutingResolveService } from './player-game-badge-routing-resolve.service';
import { ASC } from 'app/config/navigation.constants';

const playerGameBadgeRoute: Routes = [
  {
    path: '',
    component: PlayerGameBadgeComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: PlayerGameBadgeDetailComponent,
    resolve: {
      playerGameBadge: PlayerGameBadgeRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: PlayerGameBadgeUpdateComponent,
    resolve: {
      playerGameBadge: PlayerGameBadgeRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: PlayerGameBadgeUpdateComponent,
    resolve: {
      playerGameBadge: PlayerGameBadgeRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(playerGameBadgeRoute)],
  exports: [RouterModule],
})
export class PlayerGameBadgeRoutingModule {}
