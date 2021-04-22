package com.lagou.service;

import com.lagou.domain.Course;
import com.lagou.domain.CourseLesson;
import com.lagou.domain.CourseSection;

import java.util.List;

public interface CourseContentService {
    /*
        根据课程ID查询对应的课程内容（章节+课时）
     */
    public List<CourseSection> findSectionAndLessonByCourseId(Integer courseId);

    /*
        根据ID查询课程信息 回显课程信息
     */
    public Course findCourseByCourseId(int courseId);

    /*
        新增章节信息
     */
    public void saveSection(CourseSection courseSection);

    /*
        更新章节信息
     */
        public void updateSection(CourseSection courseSection);

    /*
        修改章节状态
    */
    public void updateSectionStatus(int id, int status);

    /*
        新建课时信息
     */
    public void saveLesson(CourseLesson courseLesson);

}
