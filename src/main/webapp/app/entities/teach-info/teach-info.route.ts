import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { TeachInfo } from 'app/shared/model/teach-info.model';
import { TeachInfoService } from './teach-info.service';
import { TeachInfoComponent } from './teach-info.component';
import { TeachInfoDetailComponent } from './teach-info-detail.component';
import { TeachInfoUpdateComponent } from './teach-info-update.component';
import { TeachInfoDeletePopupComponent } from './teach-info-delete-dialog.component';
import { ITeachInfo } from 'app/shared/model/teach-info.model';

@Injectable({ providedIn: 'root' })
export class TeachInfoResolve implements Resolve<ITeachInfo> {
  constructor(private service: TeachInfoService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<ITeachInfo> {
    const id = route.params['id'] ? route.params['id'] : null;
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<TeachInfo>) => response.ok),
        map((teachInfo: HttpResponse<TeachInfo>) => teachInfo.body)
      );
    }
    return of(new TeachInfo());
  }
}

export const teachInfoRoute: Routes = [
  {
    path: '',
    component: TeachInfoComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: ['ROLE_USER'],
      defaultSort: 'id,asc',
      pageTitle: 'day4MukeApp.teachInfo.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: TeachInfoDetailComponent,
    resolve: {
      teachInfo: TeachInfoResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'day4MukeApp.teachInfo.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: TeachInfoUpdateComponent,
    resolve: {
      teachInfo: TeachInfoResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'day4MukeApp.teachInfo.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: TeachInfoUpdateComponent,
    resolve: {
      teachInfo: TeachInfoResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'day4MukeApp.teachInfo.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const teachInfoPopupRoute: Routes = [
  {
    path: ':id/delete',
    component: TeachInfoDeletePopupComponent,
    resolve: {
      teachInfo: TeachInfoResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'day4MukeApp.teachInfo.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
