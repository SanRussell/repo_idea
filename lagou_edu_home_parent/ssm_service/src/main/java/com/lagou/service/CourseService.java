package com.lagou.service;

import com.lagou.domain.Course;
import com.lagou.domain.CourseVo;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

public interface CourseService {
    /*
        多条件课程列表查询
     */
    public List<Course> findCourseByCondition(CourseVo courseVo);

    /*
        添加课程和讲师信息
     */
    public void saveCourseOrTeacher(CourseVo courseVo) throws InvocationTargetException, IllegalAccessException;

    /*
        根据ID查询对应的课程信息及关联的讲师信息
     */
    public CourseVo findCourseById(Integer id);

    /*
        修改课程信息
     */
    public void updateCourseOrTeacher(CourseVo courseVo) throws InvocationTargetException, IllegalAccessException;

    /*
        修改课程状态
     */
    public void updateCourseStatus(int courseid, int status);
}
