import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { ICourseType, CourseType } from 'app/shared/model/course-type.model';
import { CourseTypeService } from './course-type.service';

@Component({
  selector: 'jhi-course-type-update',
  templateUrl: './course-type-update.component.html'
})
export class CourseTypeUpdateComponent implements OnInit {
  isSaving: boolean;

  editForm = this.fb.group({
    id: [],
    typeName: []
  });

  constructor(protected courseTypeService: CourseTypeService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ courseType }) => {
      this.updateForm(courseType);
    });
  }

  updateForm(courseType: ICourseType) {
    this.editForm.patchValue({
      id: courseType.id,
      typeName: courseType.typeName
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const courseType = this.createFromForm();
    if (courseType.id !== undefined) {
      this.subscribeToSaveResponse(this.courseTypeService.update(courseType));
    } else {
      this.subscribeToSaveResponse(this.courseTypeService.create(courseType));
    }
  }

  private createFromForm(): ICourseType {
    return {
      ...new CourseType(),
      id: this.editForm.get(['id']).value,
      typeName: this.editForm.get(['typeName']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ICourseType>>) {
    result.subscribe(() => this.onSaveSuccess(), () => this.onSaveError());
  }

  protected onSaveSuccess() {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError() {
    this.isSaving = false;
  }
}
