import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ICourseType } from 'app/shared/model/course-type.model';
import { CourseTypeService } from './course-type.service';

@Component({
  selector: 'jhi-course-type-delete-dialog',
  templateUrl: './course-type-delete-dialog.component.html'
})
export class CourseTypeDeleteDialogComponent {
  courseType: ICourseType;

  constructor(
    protected courseTypeService: CourseTypeService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.courseTypeService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'courseTypeListModification',
        content: 'Deleted an courseType'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-course-type-delete-popup',
  template: ''
})
export class CourseTypeDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ courseType }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(CourseTypeDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.courseType = courseType;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/course-type', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/course-type', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          }
        );
      }, 0);
    });
  }

  ngOnDestroy() {
    this.ngbModalRef = null;
  }
}
