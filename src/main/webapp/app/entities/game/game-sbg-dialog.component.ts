import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager, AlertService } from 'ng-jhipster';

import { GameSbg } from './game-sbg.model';
import { GameSbgPopupService } from './game-sbg-popup.service';
import { GameSbgService } from './game-sbg.service';
import { PieceSbg, PieceSbgService } from '../piece';

@Component({
    selector: 'jhi-game-sbg-dialog',
    templateUrl: './game-sbg-dialog.component.html'
})
export class GameSbgDialogComponent implements OnInit {

    game: GameSbg;
    authorities: any[];
    isSaving: boolean;

    pieces: PieceSbg[];
    constructor(
        public activeModal: NgbActiveModal,
        private alertService: AlertService,
        private gameService: GameSbgService,
        private pieceService: PieceSbgService,
        private eventManager: EventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.authorities = ['ROLE_USER', 'ROLE_ADMIN'];
        this.pieceService.query().subscribe(
            (res: Response) => { this.pieces = res.json(); }, (res: Response) => this.onError(res.json()));
    }
    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.game.id !== undefined) {
            this.gameService.update(this.game)
                .subscribe((res: GameSbg) =>
                    this.onSaveSuccess(res), (res: Response) => this.onSaveError(res));
        } else {
            this.gameService.create(this.game)
                .subscribe((res: GameSbg) =>
                    this.onSaveSuccess(res), (res: Response) => this.onSaveError(res));
        }
    }

    private onSaveSuccess(result: GameSbg) {
        this.eventManager.broadcast({ name: 'gameListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError(error) {
        try {
            error.json();
        } catch (exception) {
            error.message = error.text();
        }
        this.isSaving = false;
        this.onError(error);
    }

    private onError(error) {
        this.alertService.error(error.message, null, null);
    }

    trackPieceById(index: number, item: PieceSbg) {
        return item.id;
    }

    getSelected(selectedVals: Array<any>, option: any) {
        if (selectedVals) {
            for (let i = 0; i < selectedVals.length; i++) {
                if (option.id === selectedVals[i].id) {
                    return selectedVals[i];
                }
            }
        }
        return option;
    }
}

@Component({
    selector: 'jhi-game-sbg-popup',
    template: ''
})
export class GameSbgPopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private gamePopupService: GameSbgPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.modalRef = this.gamePopupService
                    .open(GameSbgDialogComponent, params['id']);
            } else {
                this.modalRef = this.gamePopupService
                    .open(GameSbgDialogComponent);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
