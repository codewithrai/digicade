import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { UserDetailComponent } from './detail/user-detail.component';
import { SharedModule } from '../../shared/shared.module';
import { UserRoutingModule } from './route/user-routing-module';
import { UserUpdateComponent } from './update/user-update.component';

@NgModule({
  declarations: [],
  imports: [SharedModule, UserRoutingModule],
  exports: [],
})
export class JhiUserModule {}
