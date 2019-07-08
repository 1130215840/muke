export interface ICollege {
  id?: number;
  collegeName?: string;
  collegeInfo?: string;
}

export class College implements ICollege {
  constructor(public id?: number, public collegeName?: string, public collegeInfo?: string) {}
}
