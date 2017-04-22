import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager, AlertService } from 'ng-jhipster';

import { PieceSbg } from './piece-sbg.model';
import { PieceSbgPopupService } from './piece-sbg-popup.service';
import { PieceSbgService } from './piece-sbg.service';
import { GameSbg, GameSbgService } from '../game';

@Component({
    selector: 'jhi-piece-sbg-dialog',
    templateUrl: './piece-sbg-dialog.component.html'
})
export class PieceSbgDialogComponent implements OnInit {

    piece: PieceSbg;
    authorities: any[];
    isSaving: boolean;

    games: GameSbg[];
    constructor(
        public activeModal: NgbActiveModal,
        private alertService: AlertService,
        private pieceService: PieceSbgService,
        private gameService: GameSbgService,
        private eventManager: EventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.authorities = ['ROLE_USER', 'ROLE_ADMIN'];
        this.gameService.query().subscribe(
            (res: Response) => { this.games = res.json(); }, (res: Response) => this.onError(res.json()));
    }
    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.piece.id !== undefined) {
            this.pieceService.update(this.piece)
                .subscribe((res: PieceSbg) =>
                    this.onSaveSuccess(res), (res: Response) => this.onSaveError(res));
        } else {
            this.pieceService.create(this.piece)
                .subscribe((res: PieceSbg) =>
                    this.onSaveSuccess(res), (res: Response) => this.onSaveError(res));
        }
    }

    private onSaveSuccess(result: PieceSbg) {
        this.eventManager.broadcast({ name: 'pieceListModification', content: 'OK'});
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

    trackGameById(index: number, item: GameSbg) {
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
    selector: 'jhi-piece-sbg-popup',
    template: ''
})
export class PieceSbgPopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private piecePopupService: PieceSbgPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.modalRef = this.piecePopupService
                    .open(PieceSbgDialogComponent, params['id']);
            } else {
                this.modalRef = this.piecePopupService
                    .open(PieceSbgDialogComponent);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
