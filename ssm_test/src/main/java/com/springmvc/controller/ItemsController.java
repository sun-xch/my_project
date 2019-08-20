package com.springmvc.controller;

import com.springmvc.entity.Items;
import com.springmvc.service.ItemsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
@RequestMapping("/items")
public class ItemsController{

    @Autowired
    private ItemsService itemsService;

    @RequestMapping(value = "/queryItems")
    public ModelAndView queryItems(HttpServletRequest request){
        //测试 forward 后request 是否可以共享
        System.out.println("============>" + request.getParameter("id"));

        List<Items> itemsList = itemsService.queryItems(null);
        //反回 ModelAndView
        ModelAndView modelAndView = new ModelAndView();
        //相当于request的setAttribut
        modelAndView.addObject("itemsList",itemsList);
        //指定视图
        modelAndView.setViewName("items/itemsList");
        return modelAndView;
    }

    //@RequestMapping(value = "/queryItemsById")
    //限制http请求方式,可以是POST或者是GET
    @RequestMapping(value = "/queryItemsById",method = {RequestMethod.POST,RequestMethod.GET})
    public String queryItemsById(Model model, String id){
        Items items = new Items();
        items.setId(id);
        Items item = itemsService.queryItemsById(items);
        /*ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("item",item);
        modelAndView.setViewName("items/editItem");*/
        //通过形参中的model将model数据转到页面
        //相当于modelAndView.addObject("item",item); 方法
        model.addAttribute("item",item);
        return "items/editItem";
    }

    @RequestMapping(value = "/updateItemById")
    public String updateItemById(HttpServletRequest request, Items items){

        int i = itemsService.updateItemById(items);

        //重定向到商品查询列表
        //return "redirect:queryItems";
        //页面转发
        return "forward:queryItems";
    }



}
