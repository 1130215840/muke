import { ICollege } from 'app/shared/model/college.model';

export interface IStudent {
  id?: number;
  name?: string;
  password?: string;
  phoneNumber?: number;
  collegeCode?: ICollege;
}

export class Student implements IStudent {
  constructor(
    public id?: number,
    public name?: string,
    public password?: string,
    public phoneNumber?: number,
    public collegeCode?: ICollege
  ) {}
}
