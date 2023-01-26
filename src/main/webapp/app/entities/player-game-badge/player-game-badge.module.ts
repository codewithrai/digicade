import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { PlayerGameBadgeComponent } from './list/player-game-badge.component';
import { PlayerGameBadgeDetailComponent } from './detail/player-game-badge-detail.component';
import { PlayerGameBadgeUpdateComponent } from './update/player-game-badge-update.component';
import { PlayerGameBadgeDeleteDialogComponent } from './delete/player-game-badge-delete-dialog.component';
import { PlayerGameBadgeRoutingModule } from './route/player-game-badge-routing.module';

@NgModule({
  imports: [SharedModule, PlayerGameBadgeRoutingModule],
  declarations: [
    PlayerGameBadgeComponent,
    PlayerGameBadgeDetailComponent,
    PlayerGameBadgeUpdateComponent,
    PlayerGameBadgeDeleteDialogComponent,
  ],
})
export class PlayerGameBadgeModule {}
