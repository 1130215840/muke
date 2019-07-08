/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { Day4MukeTestModule } from '../../../test.module';
import { TeachInfoDeleteDialogComponent } from 'app/entities/teach-info/teach-info-delete-dialog.component';
import { TeachInfoService } from 'app/entities/teach-info/teach-info.service';

describe('Component Tests', () => {
  describe('TeachInfo Management Delete Component', () => {
    let comp: TeachInfoDeleteDialogComponent;
    let fixture: ComponentFixture<TeachInfoDeleteDialogComponent>;
    let service: TeachInfoService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [Day4MukeTestModule],
        declarations: [TeachInfoDeleteDialogComponent]
      })
        .overrideTemplate(TeachInfoDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(TeachInfoDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(TeachInfoService);
      mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
      mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
    });

    describe('confirmDelete', () => {
      it('Should call delete service on confirmDelete', inject(
        [],
        fakeAsync(() => {
          // GIVEN
          spyOn(service, 'delete').and.returnValue(of({}));

          // WHEN
          comp.confirmDelete(123);
          tick();

          // THEN
          expect(service.delete).toHaveBeenCalledWith(123);
          expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
          expect(mockEventManager.broadcastSpy).toHaveBeenCalled();
        })
      ));
    });
  });
});
