package com.lagou.dao;

import com.lagou.domain.Course;
import com.lagou.domain.CourseLesson;
import com.lagou.domain.CourseSection;

import java.util.List;

public interface CourseContentMapper {

    /*
        根据课程ID查询关联章节信息及章节信息关联的课时信息
     */
    public List<CourseSection> findSectionAndLessonById(Integer courseId);

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
    public void updateSectionStatus(CourseSection courseSection);

    /*
        新建课时信息
     */
    public void saveLesson(CourseLesson courseLesson);
}
