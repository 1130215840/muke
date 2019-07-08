import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { College } from 'app/shared/model/college.model';
import { CollegeService } from './college.service';
import { CollegeComponent } from './college.component';
import { CollegeDetailComponent } from './college-detail.component';
import { CollegeUpdateComponent } from './college-update.component';
import { CollegeDeletePopupComponent } from './college-delete-dialog.component';
import { ICollege } from 'app/shared/model/college.model';

@Injectable({ providedIn: 'root' })
export class CollegeResolve implements Resolve<ICollege> {
  constructor(private service: CollegeService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<ICollege> {
    const id = route.params['id'] ? route.params['id'] : null;
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<College>) => response.ok),
        map((college: HttpResponse<College>) => college.body)
      );
    }
    return of(new College());
  }
}

export const collegeRoute: Routes = [
  {
    path: '',
    component: CollegeComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: ['ROLE_USER'],
      defaultSort: 'id,asc',
      pageTitle: 'day4MukeApp.college.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: CollegeDetailComponent,
    resolve: {
      college: CollegeResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'day4MukeApp.college.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: CollegeUpdateComponent,
    resolve: {
      college: CollegeResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'day4MukeApp.college.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: CollegeUpdateComponent,
    resolve: {
      college: CollegeResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'day4MukeApp.college.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const collegePopupRoute: Routes = [
  {
    path: ':id/delete',
    component: CollegeDeletePopupComponent,
    resolve: {
      college: CollegeResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'day4MukeApp.college.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
