import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { PlayerComponent } from './list/player.component';
import { PlayerDetailComponent } from './detail/player-detail.component';
import { PlayerUpdateComponent } from './update/player-update.component';
import { PlayerDeleteDialogComponent } from './delete/player-delete-dialog.component';
import { PlayerRoutingModule } from './route/player-routing.module';
import { SidebarComponent } from '../../layouts/sidebar/sidebar.component';

@NgModule({
  imports: [SharedModule, PlayerRoutingModule],
  declarations: [
    // PlayerComponent,
    // PlayerDetailComponent,
    // PlayerUpdateComponent,
    // PlayerDeleteDialogComponent,
  ],
  exports: [],
})
export class PlayerModule {}
