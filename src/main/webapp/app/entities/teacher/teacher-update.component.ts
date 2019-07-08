import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { ITeacher, Teacher } from 'app/shared/model/teacher.model';
import { TeacherService } from './teacher.service';
import { ICollege } from 'app/shared/model/college.model';
import { CollegeService } from 'app/entities/college';

@Component({
  selector: 'jhi-teacher-update',
  templateUrl: './teacher-update.component.html'
})
export class TeacherUpdateComponent implements OnInit {
  isSaving: boolean;

  colleges: ICollege[];

  editForm = this.fb.group({
    id: [],
    teacherName: [],
    collegeCode: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected teacherService: TeacherService,
    protected collegeService: CollegeService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ teacher }) => {
      this.updateForm(teacher);
    });
    this.collegeService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<ICollege[]>) => mayBeOk.ok),
        map((response: HttpResponse<ICollege[]>) => response.body)
      )
      .subscribe((res: ICollege[]) => (this.colleges = res), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(teacher: ITeacher) {
    this.editForm.patchValue({
      id: teacher.id,
      teacherName: teacher.teacherName,
      collegeCode: teacher.collegeCode
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const teacher = this.createFromForm();
    if (teacher.id !== undefined) {
      this.subscribeToSaveResponse(this.teacherService.update(teacher));
    } else {
      this.subscribeToSaveResponse(this.teacherService.create(teacher));
    }
  }

  private createFromForm(): ITeacher {
    return {
      ...new Teacher(),
      id: this.editForm.get(['id']).value,
      teacherName: this.editForm.get(['teacherName']).value,
      collegeCode: this.editForm.get(['collegeCode']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ITeacher>>) {
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

  trackCollegeById(index: number, item: ICollege) {
    return item.id;
  }
}
