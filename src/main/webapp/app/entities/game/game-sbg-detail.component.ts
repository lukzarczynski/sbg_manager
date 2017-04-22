import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { EventManager   } from 'ng-jhipster';

import { GameSbg } from './game-sbg.model';
import { GameSbgService } from './game-sbg.service';

@Component({
    selector: 'jhi-game-sbg-detail',
    templateUrl: './game-sbg-detail.component.html'
})
export class GameSbgDetailComponent implements OnInit, OnDestroy {

    game: GameSbg;
    private subscription: any;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: EventManager,
        private gameService: GameSbgService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInGames();
    }

    load(id) {
        this.gameService.find(id).subscribe((game) => {
            this.game = game;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInGames() {
        this.eventSubscriber = this.eventManager.subscribe('gameListModification', (response) => this.load(this.game.id));
    }
}
