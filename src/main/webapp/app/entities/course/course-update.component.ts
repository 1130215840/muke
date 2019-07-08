import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { JhiAlertService } from 'ng-jhipster';
import { ICourse, Course } from 'app/shared/model/course.model';
import { CourseService } from './course.service';
import { ITeacher } from 'app/shared/model/teacher.model';
import { TeacherService } from 'app/entities/teacher';
import { ICourseType } from 'app/shared/model/course-type.model';
import { CourseTypeService } from 'app/entities/course-type';

@Component({
  selector: 'jhi-course-update',
  templateUrl: './course-update.component.html'
})
export class CourseUpdateComponent implements OnInit {
  isSaving: boolean;

  teachers: ITeacher[];

  coursetypes: ICourseType[];

  editForm = this.fb.group({
    id: [],
    courseInfo: [],
    courseStartTime: [],
    teacherCode: [],
    typeCode: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected courseService: CourseService,
    protected teacherService: TeacherService,
    protected courseTypeService: CourseTypeService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ course }) => {
      this.updateForm(course);
    });
    this.teacherService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<ITeacher[]>) => mayBeOk.ok),
        map((response: HttpResponse<ITeacher[]>) => response.body)
      )
      .subscribe((res: ITeacher[]) => (this.teachers = res), (res: HttpErrorResponse) => this.onError(res.message));
    this.courseTypeService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<ICourseType[]>) => mayBeOk.ok),
        map((response: HttpResponse<ICourseType[]>) => response.body)
      )
      .subscribe((res: ICourseType[]) => (this.coursetypes = res), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(course: ICourse) {
    this.editForm.patchValue({
      id: course.id,
      courseInfo: course.courseInfo,
      courseStartTime: course.courseStartTime != null ? course.courseStartTime.format(DATE_TIME_FORMAT) : null,
      teacherCode: course.teacherCode,
      typeCode: course.typeCode
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const course = this.createFromForm();
    if (course.id !== undefined) {
      this.subscribeToSaveResponse(this.courseService.update(course));
    } else {
      this.subscribeToSaveResponse(this.courseService.create(course));
    }
  }

  private createFromForm(): ICourse {
    return {
      ...new Course(),
      id: this.editForm.get(['id']).value,
      courseInfo: this.editForm.get(['courseInfo']).value,
      courseStartTime:
        this.editForm.get(['courseStartTime']).value != null
          ? moment(this.editForm.get(['courseStartTime']).value, DATE_TIME_FORMAT)
          : undefined,
      teacherCode: this.editForm.get(['teacherCode']).value,
      typeCode: this.editForm.get(['typeCode']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ICourse>>) {
    result.subscribe(() => this.onSaveSuccess(), () => this.onSaveError());
  }

  protected onSaveSuccess() {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError() {
    this.isSaving = false;
  }
  protected onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }

  trackTeacherById(index: number, item: ITeacher) {
    return item.id;
  }

  trackCourseTypeById(index: number, item: ICourseType) {
    return item.id;
  }
}
