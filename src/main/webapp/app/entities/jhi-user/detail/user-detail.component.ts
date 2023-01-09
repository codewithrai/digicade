import { Component, OnInit } from '@angular/core';
import { IPlayer } from '../../player/player.model';
import { ActivatedRoute } from '@angular/router';
import { IUser } from '../user.model';

@Component({
  selector: 'jhi-user-detail',
  templateUrl: './user-detail.component.html',
  styleUrls: ['./user-detail.component.css'],
})
export class UserDetailComponent implements OnInit {
  user: IUser | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ user }) => {
      this.user = user;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
