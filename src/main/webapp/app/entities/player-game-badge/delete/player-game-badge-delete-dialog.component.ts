import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IPlayerGameBadge } from '../player-game-badge.model';
import { PlayerGameBadgeService } from '../service/player-game-badge.service';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';

@Component({
  templateUrl: './player-game-badge-delete-dialog.component.html',
})
export class PlayerGameBadgeDeleteDialogComponent {
  playerGameBadge?: IPlayerGameBadge;

  constructor(protected playerGameBadgeService: PlayerGameBadgeService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.playerGameBadgeService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
