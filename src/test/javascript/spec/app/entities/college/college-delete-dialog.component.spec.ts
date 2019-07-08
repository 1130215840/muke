/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { Day4MukeTestModule } from '../../../test.module';
import { CollegeDeleteDialogComponent } from 'app/entities/college/college-delete-dialog.component';
import { CollegeService } from 'app/entities/college/college.service';

describe('Component Tests', () => {
  describe('College Management Delete Component', () => {
    let comp: CollegeDeleteDialogComponent;
    let fixture: ComponentFixture<CollegeDeleteDialogComponent>;
    let service: CollegeService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [Day4MukeTestModule],
        declarations: [CollegeDeleteDialogComponent]
      })
        .overrideTemplate(CollegeDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(CollegeDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(CollegeService);
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
