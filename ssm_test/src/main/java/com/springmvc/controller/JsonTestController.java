package com.springmvc.controller;

import com.springmvc.entity.Items;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * json交互测试
 */
@Controller
public class JsonTestController {

    //请求json 输出json
    //@RequestBody 将请求的信息的json串转成Items 对象
    //@ResponseBody将items对象转成json输出
    @RequestMapping("/requestJson")
    public @ResponseBody Items requestJson(@RequestBody Items items){

        return items;
    }

    //请求key/value 输出json
    @RequestMapping("/responseJson")
    public @ResponseBody Items responseJson(Items items){

        return items;
    }
}
