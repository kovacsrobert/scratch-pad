import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { EvilLairComponent } from './evil-lair.component';

describe('EvilLairComponent', () => {
  let component: EvilLairComponent;
  let fixture: ComponentFixture<EvilLairComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ EvilLairComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(EvilLairComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
