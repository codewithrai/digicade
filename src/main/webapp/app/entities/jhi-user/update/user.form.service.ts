import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { IUser, NewUser } from '../user.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IPlayer for edit and NewPlayerFormGroupInput for create.
 */
type UserFormGroupInput = IUser | PartialWithRequiredKeyOf<NewUser>;

type UserFormDefaults = Pick<NewUser, 'id'>;

type UserFormGroupContent = {
  id: FormControl<IUser['id'] | NewUser['id']>;
  login: FormControl<NewUser['login']>;
  firstName: FormControl<IUser['firstName']>;
  lastName: FormControl<IUser['lastName']>;
  email: FormControl<IUser['email']>;
  imageUrl: FormControl<IUser['imageUrl']>;
  activated: FormControl<IUser['activated']>;
  authorities: FormControl<IUser['authorities']>;
};

export type UserFormGroup = FormGroup<UserFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class UserFormService {
  createUserFormGroup(user: UserFormGroupInput = { id: null }): UserFormGroup {
    const userRawValue = {
      ...this.getFormDefaults(),
      ...user,
    };
    return new FormGroup<UserFormGroupContent>({
      id: new FormControl(
        { value: userRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      login: new FormControl(userRawValue.login),
      firstName: new FormControl(userRawValue.firstName),
      lastName: new FormControl(userRawValue.lastName),
      email: new FormControl(userRawValue.email),
      imageUrl: new FormControl(userRawValue.imageUrl),
      activated: new FormControl(userRawValue.activated),
      authorities: new FormControl<IUser['authorities']>([]),
    });
  }

  getUser(form: UserFormGroup): IUser | NewUser {
    return form.getRawValue() as IUser | NewUser;
  }

  resetForm(form: UserFormGroup, user: UserFormGroupInput): void {
    const userRawValue = { ...this.getFormDefaults(), ...user };
    form.reset(
      {
        ...userRawValue,
        id: { value: userRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): UserFormDefaults {
    return {
      id: null,
    };
  }
}
