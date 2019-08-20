package com.springmvc.controller;

import com.springmvc.entity.Items;
import com.springmvc.service.ItemsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequestMapping("items")
public class ItemsController{

    @Autowired
    private ItemsService itemsService;

    @RequestMapping(value = "/queryItems")
    public ModelAndView queryItems(){
        List<Items> itemsList = itemsService.queryItems(null);
        //反回 ModelAndView
        ModelAndView modelAndView = new ModelAndView();
        //相当于request的setAttribut
        modelAndView.addObject("itemsList",itemsList);
        //指定视图
        modelAndView.setViewName("items/itemsList");
        return modelAndView;
    }

    @RequestMapping(value = "/queryItemsById")
    public ModelAndView queryItemsById(){
        Items items = new Items();
        items.setId("1");
        Items item = itemsService.queryItemsById(items);
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("item",item);
        modelAndView.setViewName("items/editItem");
        return modelAndView;
    }

    @RequestMapping(value = "/updateItemById")
    public ModelAndView updateItemById(){
        int i = itemsService.updateItemById(null);
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("items/itemsList");
        return modelAndView;
    }



}
