import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { ITeachInfo, TeachInfo } from 'app/shared/model/teach-info.model';
import { TeachInfoService } from './teach-info.service';
import { ITeacher } from 'app/shared/model/teacher.model';
import { TeacherService } from 'app/entities/teacher';
import { ICourse } from 'app/shared/model/course.model';
import { CourseService } from 'app/entities/course';

@Component({
  selector: 'jhi-teach-info-update',
  templateUrl: './teach-info-update.component.html'
})
export class TeachInfoUpdateComponent implements OnInit {
  isSaving: boolean;

  teachers: ITeacher[];

  courses: ICourse[];

  editForm = this.fb.group({
    id: [],
    teacherCode: [],
    courseCode: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected teachInfoService: TeachInfoService,
    protected teacherService: TeacherService,
    protected courseService: CourseService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ teachInfo }) => {
      this.updateForm(teachInfo);
    });
    this.teacherService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<ITeacher[]>) => mayBeOk.ok),
        map((response: HttpResponse<ITeacher[]>) => response.body)
      )
      .subscribe((res: ITeacher[]) => (this.teachers = res), (res: HttpErrorResponse) => this.onError(res.message));
    this.courseService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<ICourse[]>) => mayBeOk.ok),
        map((response: HttpResponse<ICourse[]>) => response.body)
      )
      .subscribe((res: ICourse[]) => (this.courses = res), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(teachInfo: ITeachInfo) {
    this.editForm.patchValue({
      id: teachInfo.id,
      teacherCode: teachInfo.teacherCode,
      courseCode: teachInfo.courseCode
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const teachInfo = this.createFromForm();
    if (teachInfo.id !== undefined) {
      this.subscribeToSaveResponse(this.teachInfoService.update(teachInfo));
    } else {
      this.subscribeToSaveResponse(this.teachInfoService.create(teachInfo));
    }
  }

  private createFromForm(): ITeachInfo {
    return {
      ...new TeachInfo(),
      id: this.editForm.get(['id']).value,
      teacherCode: this.editForm.get(['teacherCode']).value,
      courseCode: this.editForm.get(['courseCode']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ITeachInfo>>) {
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

  trackCourseById(index: number, item: ICourse) {
    return item.id;
  }
}
