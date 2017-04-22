import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SbgManagerSharedModule } from '../../shared';
import {
    PieceSbgService,
    PieceSbgPopupService,
    PieceSbgComponent,
    PieceSbgDetailComponent,
    PieceSbgDialogComponent,
    PieceSbgPopupComponent,
    PieceSbgDeletePopupComponent,
    PieceSbgDeleteDialogComponent,
    pieceRoute,
    piecePopupRoute,
    PieceSbgResolvePagingParams,
} from './';

const ENTITY_STATES = [
    ...pieceRoute,
    ...piecePopupRoute,
];

@NgModule({
    imports: [
        SbgManagerSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        PieceSbgComponent,
        PieceSbgDetailComponent,
        PieceSbgDialogComponent,
        PieceSbgDeleteDialogComponent,
        PieceSbgPopupComponent,
        PieceSbgDeletePopupComponent,
    ],
    entryComponents: [
        PieceSbgComponent,
        PieceSbgDialogComponent,
        PieceSbgPopupComponent,
        PieceSbgDeleteDialogComponent,
        PieceSbgDeletePopupComponent,
    ],
    providers: [
        PieceSbgService,
        PieceSbgPopupService,
        PieceSbgResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class SbgManagerPieceSbgModule {}
