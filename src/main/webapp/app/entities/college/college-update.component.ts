import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { ICollege, College } from 'app/shared/model/college.model';
import { CollegeService } from './college.service';

@Component({
  selector: 'jhi-college-update',
  templateUrl: './college-update.component.html'
})
export class CollegeUpdateComponent implements OnInit {
  isSaving: boolean;

  editForm = this.fb.group({
    id: [],
    collegeName: [],
    collegeInfo: []
  });

  constructor(protected collegeService: CollegeService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ college }) => {
      this.updateForm(college);
    });
  }

  updateForm(college: ICollege) {
    this.editForm.patchValue({
      id: college.id,
      collegeName: college.collegeName,
      collegeInfo: college.collegeInfo
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const college = this.createFromForm();
    if (college.id !== undefined) {
      this.subscribeToSaveResponse(this.collegeService.update(college));
    } else {
      this.subscribeToSaveResponse(this.collegeService.create(college));
    }
  }

  private createFromForm(): ICollege {
    return {
      ...new College(),
      id: this.editForm.get(['id']).value,
      collegeName: this.editForm.get(['collegeName']).value,
      collegeInfo: this.editForm.get(['collegeInfo']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ICollege>>) {
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
