package com.lagou.controller;

import com.lagou.domain.PromotionSpace;
import com.lagou.domain.ResponseResult;
import com.lagou.service.PromotionSpaceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/PromotionSpace")
public class PromotionSpaceController {

    @Autowired
    private PromotionSpaceService promotionSpaceService;

    /*
        查询广告位
     */
    @RequestMapping("/findAllPromotionSpace")
    public ResponseResult findAllPromotionSpace() {
        List<PromotionSpace> allPromotionSpace = promotionSpaceService.findAllPromotionSpace();

        ResponseResult responseResult = new ResponseResult(true, 200, "查询所有广告位成功!", allPromotionSpace);

        return responseResult;
    }

    /*
        添加广告位
     */
    @RequestMapping("/saveOrUpdatePromotionSpace")
    public ResponseResult saveOrUpdatePromotionSpace(@RequestBody PromotionSpace promotionSpace){


        if(promotionSpace.getId() == null) {
            //  新增
            promotionSpaceService.savePromotionSpace(promotionSpace);

            ResponseResult responseResult = new ResponseResult(true, 200, "新增广告位成功!", null);

            return responseResult;
        } else {
            //  更新
            promotionSpaceService.updatePromotionSpace(promotionSpace);

            ResponseResult responseResult = new ResponseResult(true, 200, "更新广告位成功!", null);

            return responseResult;
        }

    }

    /*
        根据ID查询广告位
     */
    @RequestMapping("/findPromotionSpaceById")
    public ResponseResult findPromotionSpaceById(int id) {

        PromotionSpace promotionSpace = promotionSpaceService.findPromotionSpaceById(id);

        ResponseResult responseResult = new ResponseResult(true, 200, "查询广告位成功!", promotionSpace);

        return responseResult;
    }




}
