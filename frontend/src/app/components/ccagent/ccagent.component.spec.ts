import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { CcagentComponent } from './ccagent.component';

describe('CcagentComponent', () => {
  let component: CcagentComponent;
  let fixture: ComponentFixture<CcagentComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ CcagentComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(CcagentComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
