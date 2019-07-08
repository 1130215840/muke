import { Moment } from 'moment';
import { IStudent } from 'app/shared/model/student.model';
import { ICourse } from 'app/shared/model/course.model';

export interface IComment {
  id?: number;
  commentContent?: string;
  commentTime?: Moment;
  studentCode?: IStudent;
  courseCode?: ICourse;
}

export class Comment implements IComment {
  constructor(
    public id?: number,
    public commentContent?: string,
    public commentTime?: Moment,
    public studentCode?: IStudent,
    public courseCode?: ICourse
  ) {}
}
