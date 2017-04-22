import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';

import { SbgManagerGameSbgModule } from './game/game-sbg.module';
import { SbgManagerPieceSbgModule } from './piece/piece-sbg.module';
/* jhipster-needle-add-entity-module-import - JHipster will add entity modules imports here */

@NgModule({
    imports: [
        SbgManagerGameSbgModule,
        SbgManagerPieceSbgModule,
        /* jhipster-needle-add-entity-module - JHipster will add entity modules here */
    ],
    declarations: [],
    entryComponents: [],
    providers: [],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class SbgManagerEntityModule {}
