import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'college',
        loadChildren: './college/college.module#Day4MukeCollegeModule'
      },
      {
        path: 'course',
        loadChildren: './course/course.module#Day4MukeCourseModule'
      },
      {
        path: 'comment',
        loadChildren: './comment/comment.module#Day4MukeCommentModule'
      },
      {
        path: 'course-type',
        loadChildren: './course-type/course-type.module#Day4MukeCourseTypeModule'
      },
      {
        path: 'teacher',
        loadChildren: './teacher/teacher.module#Day4MukeTeacherModule'
      },
      {
        path: 'teach-info',
        loadChildren: './teach-info/teach-info.module#Day4MukeTeachInfoModule'
      },
      {
        path: 'student',
        loadChildren: './student/student.module#Day4MukeStudentModule'
      }
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ])
  ],
  declarations: [],
  entryComponents: [],
  providers: [],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class Day4MukeEntityModule {}
