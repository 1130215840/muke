export interface ICourseType {
  id?: number;
  typeName?: string;
}

export class CourseType implements ICourseType {
  constructor(public id?: number, public typeName?: string) {}
}
