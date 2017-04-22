import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { PaginationUtil } from 'ng-jhipster';

import { PieceSbgComponent } from './piece-sbg.component';
import { PieceSbgDetailComponent } from './piece-sbg-detail.component';
import { PieceSbgPopupComponent } from './piece-sbg-dialog.component';
import { PieceSbgDeletePopupComponent } from './piece-sbg-delete-dialog.component';

import { Principal } from '../../shared';

@Injectable()
export class PieceSbgResolvePagingParams implements Resolve<any> {

  constructor(private paginationUtil: PaginationUtil) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
      const page = route.queryParams['page'] ? route.queryParams['page'] : '1';
      const sort = route.queryParams['sort'] ? route.queryParams['sort'] : 'id,asc';
      return {
          page: this.paginationUtil.parsePage(page),
          predicate: this.paginationUtil.parsePredicate(sort),
          ascending: this.paginationUtil.parseAscending(sort)
    };
  }
}

export const pieceRoute: Routes = [
  {
    path: 'piece-sbg',
    component: PieceSbgComponent,
    resolve: {
      'pagingParams': PieceSbgResolvePagingParams
    },
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'Pieces'
    },
    canActivate: [UserRouteAccessService]
  }, {
    path: 'piece-sbg/:id',
    component: PieceSbgDetailComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'Pieces'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const piecePopupRoute: Routes = [
  {
    path: 'piece-sbg-new',
    component: PieceSbgPopupComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'Pieces'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  },
  {
    path: 'piece-sbg/:id/edit',
    component: PieceSbgPopupComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'Pieces'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  },
  {
    path: 'piece-sbg/:id/delete',
    component: PieceSbgDeletePopupComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'Pieces'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
