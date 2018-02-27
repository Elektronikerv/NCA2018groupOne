import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { EditOrderCcagentComponent } from './edit-order-ccagent.component';

describe('EditOrderCcagentComponent', () => {
  let component: EditOrderCcagentComponent;
  let fixture: ComponentFixture<EditOrderCcagentComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ EditOrderCcagentComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(EditOrderCcagentComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
