/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { Observable, of } from 'rxjs';

import { Day4MukeTestModule } from '../../../test.module';
import { TeachInfoUpdateComponent } from 'app/entities/teach-info/teach-info-update.component';
import { TeachInfoService } from 'app/entities/teach-info/teach-info.service';
import { TeachInfo } from 'app/shared/model/teach-info.model';

describe('Component Tests', () => {
  describe('TeachInfo Management Update Component', () => {
    let comp: TeachInfoUpdateComponent;
    let fixture: ComponentFixture<TeachInfoUpdateComponent>;
    let service: TeachInfoService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [Day4MukeTestModule],
        declarations: [TeachInfoUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(TeachInfoUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(TeachInfoUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(TeachInfoService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new TeachInfo(123);
        spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.update).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));

      it('Should call create service on save for new entity', fakeAsync(() => {
        // GIVEN
        const entity = new TeachInfo();
        spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.create).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));
    });
  });
});
