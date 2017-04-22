import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { PieceSbg } from './piece-sbg.model';
import { PieceSbgService } from './piece-sbg.service';
@Injectable()
export class PieceSbgPopupService {
    private isOpen = false;
    constructor(
        private modalService: NgbModal,
        private router: Router,
        private pieceService: PieceSbgService

    ) {}

    open(component: Component, id?: number | any): NgbModalRef {
        if (this.isOpen) {
            return;
        }
        this.isOpen = true;

        if (id) {
            this.pieceService.find(id).subscribe((piece) => {
                this.pieceModalRef(component, piece);
            });
        } else {
            return this.pieceModalRef(component, new PieceSbg());
        }
    }

    pieceModalRef(component: Component, piece: PieceSbg): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.piece = piece;
        modalRef.result.then((result) => {
            this.router.navigate([{ outlets: { popup: null }}], { replaceUrl: true });
            this.isOpen = false;
        }, (reason) => {
            this.router.navigate([{ outlets: { popup: null }}], { replaceUrl: true });
            this.isOpen = false;
        });
        return modalRef;
    }
}
