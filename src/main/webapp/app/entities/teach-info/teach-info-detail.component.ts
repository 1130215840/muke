import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ITeachInfo } from 'app/shared/model/teach-info.model';

@Component({
  selector: 'jhi-teach-info-detail',
  templateUrl: './teach-info-detail.component.html'
})
export class TeachInfoDetailComponent implements OnInit {
  teachInfo: ITeachInfo;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ teachInfo }) => {
      this.teachInfo = teachInfo;
    });
  }

  previousState() {
    window.history.back();
  }
}
