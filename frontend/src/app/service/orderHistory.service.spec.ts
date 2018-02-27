import {TestBed, inject} from '@angular/core/testing';

import {OrderHistoryService} from './orderHistory.service';

describe('OrderHistoryService', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [OrderHistoryService]
    });
  });

  it('should be created', inject([OrderHistoryService], (service: OrderHistoryService) => {
    expect(service).toBeTruthy();
  }));
});
