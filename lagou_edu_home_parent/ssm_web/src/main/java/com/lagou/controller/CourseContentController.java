package com.lagou.controller;

import com.lagou.domain.Course;
import com.lagou.domain.CourseLesson;
import com.lagou.domain.CourseSection;
import com.lagou.domain.ResponseResult;
import com.lagou.service.CourseContentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/courseContent")
public class CourseContentController {

    @Autowired
    private CourseContentService courseContentService;


    /*
        查询章节 课时信息
     */
    @RequestMapping("/findSectionAndLesson")
    public ResponseResult findSectionAndLessonByCourseId(Integer courseId){

        //  调用service
        List<CourseSection> list = courseContentService.findSectionAndLessonByCourseId(courseId);

        ResponseResult responseResult = new ResponseResult(true, 200, "章节及课时内容查询成功！", list);

        return responseResult;

    }


    /*
        回显章节对应课程信息
     */
    @RequestMapping("/findCourseByCourseId")
    public ResponseResult findCourseByCourseId(Integer courseId){

        Course course = courseContentService.findCourseByCourseId(courseId);

        ResponseResult responseResult = new ResponseResult(true, 200, "查询课程信息成功！", course);

        return responseResult;
    }

    /*
        新增&更新章节信息
     */
    @RequestMapping("/saveOrUpdateSection")
    public ResponseResult saveOrUpdateSection(@RequestBody CourseSection courseSection) {

        //  判断是否携带章节ID
        if(courseSection.getId() == null ) {
            //  新增
            courseContentService.saveSection(courseSection);

            ResponseResult responseResult = new ResponseResult(true, 200, "新增章节成功！", null);

            return responseResult;
        } else {
            //  更新
            courseContentService.updateSection(courseSection);

            ResponseResult responseResult = new ResponseResult(true, 200, "更新章节成功！", null);

            return responseResult;

        }

    }

    /*
        修改章节状态
    */
    @RequestMapping("/updateSectionStatus")
    public ResponseResult updateSectionStatus(int id,int status){

        courseContentService.updateSectionStatus(id,status);

        HashMap<String, Object> map = new HashMap<>();
        map.put("status",status);

        ResponseResult responseResult = new ResponseResult(true, 200, "修改章节状态成功！", map);

        return responseResult;

    }


    /*
        新增课时信息
     */
    @RequestMapping("/saveLesson")
    public ResponseResult saveLesson(@RequestBody CourseLesson courseLesson){

        courseContentService.saveLesson(courseLesson);

        ResponseResult responseResult = new ResponseResult(true, 200, "新增课时信息成功", null);

        return responseResult;

    }

}
