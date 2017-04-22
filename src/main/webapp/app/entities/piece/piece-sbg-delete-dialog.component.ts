import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager } from 'ng-jhipster';

import { PieceSbg } from './piece-sbg.model';
import { PieceSbgPopupService } from './piece-sbg-popup.service';
import { PieceSbgService } from './piece-sbg.service';

@Component({
    selector: 'jhi-piece-sbg-delete-dialog',
    templateUrl: './piece-sbg-delete-dialog.component.html'
})
export class PieceSbgDeleteDialogComponent {

    piece: PieceSbg;

    constructor(
        private pieceService: PieceSbgService,
        public activeModal: NgbActiveModal,
        private eventManager: EventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.pieceService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'pieceListModification',
                content: 'Deleted an piece'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-piece-sbg-delete-popup',
    template: ''
})
export class PieceSbgDeletePopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private piecePopupService: PieceSbgPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.modalRef = this.piecePopupService
                .open(PieceSbgDeleteDialogComponent, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
