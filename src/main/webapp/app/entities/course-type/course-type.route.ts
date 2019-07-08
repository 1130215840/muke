import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { CourseType } from 'app/shared/model/course-type.model';
import { CourseTypeService } from './course-type.service';
import { CourseTypeComponent } from './course-type.component';
import { CourseTypeDetailComponent } from './course-type-detail.component';
import { CourseTypeUpdateComponent } from './course-type-update.component';
import { CourseTypeDeletePopupComponent } from './course-type-delete-dialog.component';
import { ICourseType } from 'app/shared/model/course-type.model';

@Injectable({ providedIn: 'root' })
export class CourseTypeResolve implements Resolve<ICourseType> {
  constructor(private service: CourseTypeService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<ICourseType> {
    const id = route.params['id'] ? route.params['id'] : null;
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<CourseType>) => response.ok),
        map((courseType: HttpResponse<CourseType>) => courseType.body)
      );
    }
    return of(new CourseType());
  }
}

export const courseTypeRoute: Routes = [
  {
    path: '',
    component: CourseTypeComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: ['ROLE_USER'],
      defaultSort: 'id,asc',
      pageTitle: 'day4MukeApp.courseType.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: CourseTypeDetailComponent,
    resolve: {
      courseType: CourseTypeResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'day4MukeApp.courseType.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: CourseTypeUpdateComponent,
    resolve: {
      courseType: CourseTypeResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'day4MukeApp.courseType.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: CourseTypeUpdateComponent,
    resolve: {
      courseType: CourseTypeResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'day4MukeApp.courseType.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const courseTypePopupRoute: Routes = [
  {
    path: ':id/delete',
    component: CourseTypeDeletePopupComponent,
    resolve: {
      courseType: CourseTypeResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'day4MukeApp.courseType.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
