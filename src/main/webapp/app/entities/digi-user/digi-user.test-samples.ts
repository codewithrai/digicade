import dayjs from 'dayjs/esm';

import { IDigiUser, NewDigiUser } from './digi-user.model';

export const sampleWithRequiredData: IDigiUser = {
  id: 79194,
};

export const sampleWithPartialData: IDigiUser = {
  id: 42634,
  userName: 'Health transmit group',
  email: 'Grover.Schowalter34@hotmail.com',
  age: 42113,
  promoCode: 'Investor',
};

export const sampleWithFullData: IDigiUser = {
  id: 1603,
  firstName: 'Turner',
  lastName: 'Bauch',
  userName: 'Account',
  email: 'Elenora.Raynor@gmail.com',
  dob: dayjs('2022-12-20'),
  age: 94113,
  promoCode: 'Crossing',
};

export const sampleWithNewData: NewDigiUser = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
