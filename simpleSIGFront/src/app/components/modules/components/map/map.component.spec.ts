import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { Mapomponent } from './map.component';

describe('MapComponent', () => {
  let component: Mapomponent;
  let fixture: ComponentFixture<Mapomponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ Mapomponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(Mapomponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
