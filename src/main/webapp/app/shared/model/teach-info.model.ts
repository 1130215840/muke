import { ITeacher } from 'app/shared/model/teacher.model';
import { ICourse } from 'app/shared/model/course.model';

export interface ITeachInfo {
  id?: number;
  teacherCode?: ITeacher;
  courseCode?: ICourse;
}

export class TeachInfo implements ITeachInfo {
  constructor(public id?: number, public teacherCode?: ITeacher, public courseCode?: ICourse) {}
}
