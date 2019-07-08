import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { Day4MukeSharedModule } from 'app/shared';
import {
  StudentComponent,
  StudentDetailComponent,
  StudentUpdateComponent,
  StudentDeletePopupComponent,
  StudentDeleteDialogComponent,
  studentRoute,
  studentPopupRoute
} from './';

const ENTITY_STATES = [...studentRoute, ...studentPopupRoute];

@NgModule({
  imports: [Day4MukeSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    StudentComponent,
    StudentDetailComponent,
    StudentUpdateComponent,
    StudentDeleteDialogComponent,
    StudentDeletePopupComponent
  ],
  entryComponents: [StudentComponent, StudentUpdateComponent, StudentDeleteDialogComponent, StudentDeletePopupComponent],
  providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class Day4MukeStudentModule {
  constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
    this.languageHelper.language.subscribe((languageKey: string) => {
      if (languageKey !== undefined) {
        this.languageService.changeLanguage(languageKey);
      }
    });
  }
}
