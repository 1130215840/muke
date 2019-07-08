import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { Day4MukeSharedCommonModule, JhiLoginModalComponent, HasAnyAuthorityDirective } from './';

@NgModule({
  imports: [Day4MukeSharedCommonModule],
  declarations: [JhiLoginModalComponent, HasAnyAuthorityDirective],
  entryComponents: [JhiLoginModalComponent],
  exports: [Day4MukeSharedCommonModule, JhiLoginModalComponent, HasAnyAuthorityDirective],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class Day4MukeSharedModule {
  static forRoot() {
    return {
      ngModule: Day4MukeSharedModule
    };
  }
}
