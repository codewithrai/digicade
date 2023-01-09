import { Component, OnInit } from '@angular/core';
import { ITEM_DELETED_EVENT } from '../../../config/navigation.constants';
import { PlayerService } from '../../player/service/player.service';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { UserService } from '../service/user.service';
import { IPlayer } from '../../player/player.model';
import { IUser } from '../user.model';

@Component({
  selector: 'jhi-user-delete',
  templateUrl: './user-delete.component.html',
  styleUrls: ['./user-delete.component.scss'],
})
export class UserDeleteComponent implements OnInit {
  user?: IUser;

  constructor(protected userService: UserService, protected activeModal: NgbActiveModal) {}

  ngOnInit(): void {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.userService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
