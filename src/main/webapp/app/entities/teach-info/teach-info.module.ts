import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { Day4MukeSharedModule } from 'app/shared';
import {
  TeachInfoComponent,
  TeachInfoDetailComponent,
  TeachInfoUpdateComponent,
  TeachInfoDeletePopupComponent,
  TeachInfoDeleteDialogComponent,
  teachInfoRoute,
  teachInfoPopupRoute
} from './';

const ENTITY_STATES = [...teachInfoRoute, ...teachInfoPopupRoute];

@NgModule({
  imports: [Day4MukeSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    TeachInfoComponent,
    TeachInfoDetailComponent,
    TeachInfoUpdateComponent,
    TeachInfoDeleteDialogComponent,
    TeachInfoDeletePopupComponent
  ],
  entryComponents: [TeachInfoComponent, TeachInfoUpdateComponent, TeachInfoDeleteDialogComponent, TeachInfoDeletePopupComponent],
  providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class Day4MukeTeachInfoModule {
  constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
    this.languageHelper.language.subscribe((languageKey: string) => {
      if (languageKey !== undefined) {
        this.languageService.changeLanguage(languageKey);
      }
    });
  }
}
