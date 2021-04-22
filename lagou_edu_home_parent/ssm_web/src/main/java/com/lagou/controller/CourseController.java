package com.lagou.controller;

import com.lagou.domain.Course;
import com.lagou.domain.CourseVo;
import com.lagou.domain.ResponseResult;
import com.lagou.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController // @Controller + @ResponseBody
@RequestMapping("/course")
public class CourseController {

    @Autowired
    private CourseService courseService;


    /*
        多条件课程列表查询
     */
    @RequestMapping("/findCourseByCondition")
    public ResponseResult findCourseByCondition(@RequestBody CourseVo courseVo) {

        //  调用service
        List<Course> list = courseService.findCourseByCondition(courseVo);

        ResponseResult responseResult = new ResponseResult(true, 200, "响应成功！", list);

        return responseResult;
    }

    /*
        课程图片上传
     */

    @RequestMapping("/courseUpload")
    public ResponseResult fileUpload(@RequestParam("file") MultipartFile file, HttpServletRequest request) throws IOException {

        //1.判断接收到的上传文件是否为空
        if (file.isEmpty()) {
            throw new RuntimeException();
        }

        //2.获取项目部署路径
        // /Users/srussell/Library/Tomcat/webapps/ssm_web
        String realPath = request.getServletContext().getRealPath("/");

        // /Users/srussell/Library/Tomcat/webapps/
        String substring = realPath.substring(0, realPath.indexOf("ssm_web"));

        //3.获取源文件名
        String originalFilename = file.getOriginalFilename();

        //4.生成新文件名
        String newFileName = System.currentTimeMillis() + originalFilename.substring(originalFilename.lastIndexOf("."));

        //5.文件上传
        // /Users/srussell/Library/Tomcat/webapps/upload
        String uploadPath = substring + "upload//";
        File filePath = new File(uploadPath, newFileName);

        //如果目录不存在就创建目录
        if (!filePath.getParentFile().exists()) {
            filePath.getParentFile().mkdirs();
            System.out.println("创建目录：" + filePath);
        }

        //图片已上传
        file.transferTo(filePath);

        //6.将文件名和文件路径返回并进行响应
        HashMap<String, String> map = new HashMap<>();
        map.put("fileName", newFileName);
        map.put("filePath", "http://localhost:8080/upload/" + newFileName);

        ResponseResult responseResult = new ResponseResult(true, 200, "突破上传成功！", map);

        return responseResult;


    }

    /*
        新增课程信息及讲师信息
        新增课程信息和修改课程信息要写在同一个方法中
     */
    @RequestMapping("/saveOrUpdateCourse")
    public ResponseResult saveOrUpdateCourse(@RequestBody CourseVo courseVo) throws InvocationTargetException, IllegalAccessException {

        if(courseVo.getId() == null) {
            courseService.saveCourseOrTeacher(courseVo);
            ResponseResult responseResult = new ResponseResult(true, 200, "新增成功！", null);
            return responseResult;
        } else {
            courseService.updateCourseOrTeacher(courseVo);
            ResponseResult responseResult = new ResponseResult(true, 200, "修改成功！", null);
            return responseResult;
        }
    }

    /*
        回显课程信息（根据ID查询对应的课程信息及关联的讲师信息）
     */
    @RequestMapping("/findCourseById")
    public ResponseResult findCourseById(Integer id) {

        CourseVo courseVo = courseService.findCourseById(id);

        ResponseResult responseResult = new ResponseResult(true, 200, "根据ID查询课程信息成功！", courseVo);

        return responseResult;
    }



    /*
        修改课程状态
     */

    @RequestMapping("/updateCourseStatus")
    public ResponseResult updateCourseStatus(Integer id , Integer status) {

        //  调用service
        courseService.updateCourseStatus(id,status);

        //  响应数据
        HashMap<String, Object> map = new HashMap<>();
        map.put("status",status);

        ResponseResult responseResult = new ResponseResult(true, 200, "课程状态变更成功！", map);

        return responseResult;
    }


}
