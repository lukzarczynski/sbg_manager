import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { DateUtils, DataUtils, EventManager } from 'ng-jhipster';
import { SbgManagerTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { PieceSbgDetailComponent } from '../../../../../../main/webapp/app/entities/piece/piece-sbg-detail.component';
import { PieceSbgService } from '../../../../../../main/webapp/app/entities/piece/piece-sbg.service';
import { PieceSbg } from '../../../../../../main/webapp/app/entities/piece/piece-sbg.model';

describe('Component Tests', () => {

    describe('PieceSbg Management Detail Component', () => {
        let comp: PieceSbgDetailComponent;
        let fixture: ComponentFixture<PieceSbgDetailComponent>;
        let service: PieceSbgService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [SbgManagerTestModule],
                declarations: [PieceSbgDetailComponent],
                providers: [
                    DateUtils,
                    DataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    PieceSbgService,
                    EventManager
                ]
            }).overrideComponent(PieceSbgDetailComponent, {
                set: {
                    template: ''
                }
            }).compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(PieceSbgDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(PieceSbgService);
        });


        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new PieceSbg(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.piece).toEqual(jasmine.objectContaining({id:10}));
            });
        });
    });

});
