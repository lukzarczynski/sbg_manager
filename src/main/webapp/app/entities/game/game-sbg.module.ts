import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SbgManagerSharedModule } from '../../shared';
import {
    GameSbgService,
    GameSbgPopupService,
    GameSbgComponent,
    GameSbgDetailComponent,
    GameSbgDialogComponent,
    GameSbgPopupComponent,
    GameSbgDeletePopupComponent,
    GameSbgDeleteDialogComponent,
    gameRoute,
    gamePopupRoute,
    GameSbgResolvePagingParams,
} from './';

const ENTITY_STATES = [
    ...gameRoute,
    ...gamePopupRoute,
];

@NgModule({
    imports: [
        SbgManagerSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        GameSbgComponent,
        GameSbgDetailComponent,
        GameSbgDialogComponent,
        GameSbgDeleteDialogComponent,
        GameSbgPopupComponent,
        GameSbgDeletePopupComponent,
    ],
    entryComponents: [
        GameSbgComponent,
        GameSbgDialogComponent,
        GameSbgPopupComponent,
        GameSbgDeleteDialogComponent,
        GameSbgDeletePopupComponent,
    ],
    providers: [
        GameSbgService,
        GameSbgPopupService,
        GameSbgResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class SbgManagerGameSbgModule {}
