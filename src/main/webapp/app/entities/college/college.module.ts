import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { Day4MukeSharedModule } from 'app/shared';
import {
  CollegeComponent,
  CollegeDetailComponent,
  CollegeUpdateComponent,
  CollegeDeletePopupComponent,
  CollegeDeleteDialogComponent,
  collegeRoute,
  collegePopupRoute
} from './';

const ENTITY_STATES = [...collegeRoute, ...collegePopupRoute];

@NgModule({
  imports: [Day4MukeSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    CollegeComponent,
    CollegeDetailComponent,
    CollegeUpdateComponent,
    CollegeDeleteDialogComponent,
    CollegeDeletePopupComponent
  ],
  entryComponents: [CollegeComponent, CollegeUpdateComponent, CollegeDeleteDialogComponent, CollegeDeletePopupComponent],
  providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class Day4MukeCollegeModule {
  constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
    this.languageHelper.language.subscribe((languageKey: string) => {
      if (languageKey !== undefined) {
        this.languageService.changeLanguage(languageKey);
      }
    });
  }
}
