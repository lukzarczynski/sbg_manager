import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { PaginationUtil } from 'ng-jhipster';

import { GameSbgComponent } from './game-sbg.component';
import { GameSbgDetailComponent } from './game-sbg-detail.component';
import { GameSbgPopupComponent } from './game-sbg-dialog.component';
import { GameSbgDeletePopupComponent } from './game-sbg-delete-dialog.component';

import { Principal } from '../../shared';

@Injectable()
export class GameSbgResolvePagingParams implements Resolve<any> {

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

export const gameRoute: Routes = [
  {
    path: 'game-sbg',
    component: GameSbgComponent,
    resolve: {
      'pagingParams': GameSbgResolvePagingParams
    },
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'Games'
    },
    canActivate: [UserRouteAccessService]
  }, {
    path: 'game-sbg/:id',
    component: GameSbgDetailComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'Games'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const gamePopupRoute: Routes = [
  {
    path: 'game-sbg-new',
    component: GameSbgPopupComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'Games'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  },
  {
    path: 'game-sbg/:id/edit',
    component: GameSbgPopupComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'Games'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  },
  {
    path: 'game-sbg/:id/delete',
    component: GameSbgDeletePopupComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'Games'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
