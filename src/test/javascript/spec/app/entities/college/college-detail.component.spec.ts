/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { Day4MukeTestModule } from '../../../test.module';
import { CollegeDetailComponent } from 'app/entities/college/college-detail.component';
import { College } from 'app/shared/model/college.model';

describe('Component Tests', () => {
  describe('College Management Detail Component', () => {
    let comp: CollegeDetailComponent;
    let fixture: ComponentFixture<CollegeDetailComponent>;
    const route = ({ data: of({ college: new College(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [Day4MukeTestModule],
        declarations: [CollegeDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(CollegeDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(CollegeDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.college).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
