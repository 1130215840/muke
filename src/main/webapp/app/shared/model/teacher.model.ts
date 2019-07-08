import { ICollege } from 'app/shared/model/college.model';

export interface ITeacher {
  id?: number;
  teacherName?: string;
  collegeCode?: ICollege;
}

export class Teacher implements ITeacher {
  constructor(public id?: number, public teacherName?: string, public collegeCode?: ICollege) {}
}
