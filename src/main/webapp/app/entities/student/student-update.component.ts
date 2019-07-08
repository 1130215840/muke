import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { IStudent, Student } from 'app/shared/model/student.model';
import { StudentService } from './student.service';
import { ICollege } from 'app/shared/model/college.model';
import { CollegeService } from 'app/entities/college';

@Component({
  selector: 'jhi-student-update',
  templateUrl: './student-update.component.html'
})
export class StudentUpdateComponent implements OnInit {
  isSaving: boolean;

  colleges: ICollege[];

  editForm = this.fb.group({
    id: [],
    name: [],
    password: [],
    phoneNumber: [],
    collegeCode: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected studentService: StudentService,
    protected collegeService: CollegeService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ student }) => {
      this.updateForm(student);
    });
    this.collegeService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<ICollege[]>) => mayBeOk.ok),
        map((response: HttpResponse<ICollege[]>) => response.body)
      )
      .subscribe((res: ICollege[]) => (this.colleges = res), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(student: IStudent) {
    this.editForm.patchValue({
      id: student.id,
      name: student.name,
      password: student.password,
      phoneNumber: student.phoneNumber,
      collegeCode: student.collegeCode
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const student = this.createFromForm();
    if (student.id !== undefined) {
      this.subscribeToSaveResponse(this.studentService.update(student));
    } else {
      this.subscribeToSaveResponse(this.studentService.create(student));
    }
  }

  private createFromForm(): IStudent {
    return {
      ...new Student(),
      id: this.editForm.get(['id']).value,
      name: this.editForm.get(['name']).value,
      password: this.editForm.get(['password']).value,
      phoneNumber: this.editForm.get(['phoneNumber']).value,
      collegeCode: this.editForm.get(['collegeCode']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IStudent>>) {
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
