/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { Day4MukeTestModule } from '../../../test.module';
import { TeachInfoDetailComponent } from 'app/entities/teach-info/teach-info-detail.component';
import { TeachInfo } from 'app/shared/model/teach-info.model';

describe('Component Tests', () => {
  describe('TeachInfo Management Detail Component', () => {
    let comp: TeachInfoDetailComponent;
    let fixture: ComponentFixture<TeachInfoDetailComponent>;
    const route = ({ data: of({ teachInfo: new TeachInfo(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [Day4MukeTestModule],
        declarations: [TeachInfoDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(TeachInfoDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(TeachInfoDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.teachInfo).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
