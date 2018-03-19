import {async, ComponentFixture, TestBed} from '@angular/core/testing';

import {NoPrivilegeComponent} from './no-privilege.component';

describe('NoPrivilegeComponent', () => {
  let component: NoPrivilegeComponent;
  let fixture: ComponentFixture<NoPrivilegeComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [NoPrivilegeComponent]
    })
      .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(NoPrivilegeComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
