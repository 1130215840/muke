import { Moment } from 'moment';
import { ITeacher } from 'app/shared/model/teacher.model';
import { ICourseType } from 'app/shared/model/course-type.model';

export interface ICourse {
  id?: number;
  courseInfo?: string;
  courseStartTime?: Moment;
  teacherCode?: ITeacher;
  typeCode?: ICourseType;
}

export class Course implements ICourse {
  constructor(
    public id?: number,
    public courseInfo?: string,
    public courseStartTime?: Moment,
    public teacherCode?: ITeacher,
    public typeCode?: ICourseType
  ) {}
}
