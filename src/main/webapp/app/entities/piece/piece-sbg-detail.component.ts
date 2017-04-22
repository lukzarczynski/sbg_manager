import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { EventManager   } from 'ng-jhipster';

import { PieceSbg } from './piece-sbg.model';
import { PieceSbgService } from './piece-sbg.service';

@Component({
    selector: 'jhi-piece-sbg-detail',
    templateUrl: './piece-sbg-detail.component.html'
})
export class PieceSbgDetailComponent implements OnInit, OnDestroy {

    piece: PieceSbg;
    private subscription: any;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: EventManager,
        private pieceService: PieceSbgService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInPieces();
    }

    load(id) {
        this.pieceService.find(id).subscribe((piece) => {
            this.piece = piece;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInPieces() {
        this.eventSubscriber = this.eventManager.subscribe('pieceListModification', (response) => this.load(this.piece.id));
    }
}
