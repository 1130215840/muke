import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { Day4MukeSharedModule } from 'app/shared';
import {
  CourseTypeComponent,
  CourseTypeDetailComponent,
  CourseTypeUpdateComponent,
  CourseTypeDeletePopupComponent,
  CourseTypeDeleteDialogComponent,
  courseTypeRoute,
  courseTypePopupRoute
} from './';

const ENTITY_STATES = [...courseTypeRoute, ...courseTypePopupRoute];

@NgModule({
  imports: [Day4MukeSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    CourseTypeComponent,
    CourseTypeDetailComponent,
    CourseTypeUpdateComponent,
    CourseTypeDeleteDialogComponent,
    CourseTypeDeletePopupComponent
  ],
  entryComponents: [CourseTypeComponent, CourseTypeUpdateComponent, CourseTypeDeleteDialogComponent, CourseTypeDeletePopupComponent],
  providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class Day4MukeCourseTypeModule {
  constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
    this.languageHelper.language.subscribe((languageKey: string) => {
      if (languageKey !== undefined) {
        this.languageService.changeLanguage(languageKey);
      }
    });
  }
}
