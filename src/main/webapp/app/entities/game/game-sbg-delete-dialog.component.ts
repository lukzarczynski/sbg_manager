import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager } from 'ng-jhipster';

import { GameSbg } from './game-sbg.model';
import { GameSbgPopupService } from './game-sbg-popup.service';
import { GameSbgService } from './game-sbg.service';

@Component({
    selector: 'jhi-game-sbg-delete-dialog',
    templateUrl: './game-sbg-delete-dialog.component.html'
})
export class GameSbgDeleteDialogComponent {

    game: GameSbg;

    constructor(
        private gameService: GameSbgService,
        public activeModal: NgbActiveModal,
        private eventManager: EventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.gameService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'gameListModification',
                content: 'Deleted an game'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-game-sbg-delete-popup',
    template: ''
})
export class GameSbgDeletePopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private gamePopupService: GameSbgPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.modalRef = this.gamePopupService
                .open(GameSbgDeleteDialogComponent, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
