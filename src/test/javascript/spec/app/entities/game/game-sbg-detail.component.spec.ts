import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { DateUtils, DataUtils, EventManager } from 'ng-jhipster';
import { SbgManagerTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { GameSbgDetailComponent } from '../../../../../../main/webapp/app/entities/game/game-sbg-detail.component';
import { GameSbgService } from '../../../../../../main/webapp/app/entities/game/game-sbg.service';
import { GameSbg } from '../../../../../../main/webapp/app/entities/game/game-sbg.model';

describe('Component Tests', () => {

    describe('GameSbg Management Detail Component', () => {
        let comp: GameSbgDetailComponent;
        let fixture: ComponentFixture<GameSbgDetailComponent>;
        let service: GameSbgService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [SbgManagerTestModule],
                declarations: [GameSbgDetailComponent],
                providers: [
                    DateUtils,
                    DataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    GameSbgService,
                    EventManager
                ]
            }).overrideComponent(GameSbgDetailComponent, {
                set: {
                    template: ''
                }
            }).compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(GameSbgDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(GameSbgService);
        });


        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new GameSbg(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.game).toEqual(jasmine.objectContaining({id:10}));
            });
        });
    });

});
