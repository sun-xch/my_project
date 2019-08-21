package com.springmvc.controller;

import com.springmvc.controller.validation.ValidGroup1;
import com.springmvc.dto.ItemsVo;
import com.springmvc.entity.Items;
import com.springmvc.service.ItemsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.validation.annotation.Validated;
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

    /**
     * 查询商品信息
     * @param request
     * @param items
     * @return
     */
    @RequestMapping(value = "/queryItems")
    public ModelAndView queryItems(HttpServletRequest request, Items items){
        //测试 forward 后request 是否可以共享
        System.out.println("============>" + request.getParameter("id"));

        List<Items> itemsList = itemsService.queryItems(items);
        //反回 ModelAndView
        ModelAndView modelAndView = new ModelAndView();
        //相当于request的setAttribut
        modelAndView.addObject("itemsList",itemsList);
        //指定视图
        modelAndView.setViewName("items/itemsList");
        return modelAndView;
    }

    /**
     * 根据商品ID获取商品信息
     * @param model
     * @param id
     * @return
     */
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

    /**
     * 根据商品ID 修改商品信息
     * @param request
     * @param items
     * @return
     */
    //在pojo前面加上@Validated，在需要校验的pojo的后面添加BindingResult bindingResult 接收校验出错信息
    //注意@Validated 和 BindingResult bindingResult是配对出现的并且在形参中的顺序是固定的
    //value = {ValidGroup1.class} 指定使用ValidGroup1分组的校验
    @RequestMapping(value = "/updateItemById")
    public String updateItemById(HttpServletRequest request, Model model, @Validated(value = {ValidGroup1.class}) Items items, BindingResult bindingResult){

        //获取校验错误信息
        if(bindingResult.hasErrors()){
            //输出错误信息
            List<ObjectError> allErrors = bindingResult.getAllErrors();
            for(ObjectError objectError:allErrors){
                //输出错误信息
                System.out.println("==========>" + objectError.getDefaultMessage());
            }
            //将错误信息传到页面
            model.addAttribute("allErrors", allErrors);
            return "items/editItem";
        }
        int i = itemsService.updateItemById(items);

        //重定向到商品查询列表
        //return "redirect:queryItems";
        //页面转发
        return "forward:queryItems";
    }

    /**
     * 批量删除商品信息
     * @param items_id
     * @return
     */
    @RequestMapping(value = "/deleteItems")
    public String deleteItems(Integer[] items_id){
        //调用service批量删除商品
        //...
        return "forward:queryItems";
    }

    /**
     * 查询 要批量修改的商品信息
     * @param request
     * @param items
     * @return
     */
    @RequestMapping(value = "/editItemsQuery")
    public ModelAndView editItemsQuery(HttpServletRequest request, Items items){
        List<Items> itemsList = itemsService.queryItems(items);
        //反回 ModelAndView
        ModelAndView modelAndView = new ModelAndView();
        //相当于request的setAttribut
        modelAndView.addObject("itemsList",itemsList);
        //指定视图
        modelAndView.setViewName("items/editItemsQuery");
        return modelAndView;
    }

    /**
     * 批量修改商品信息
     * @param itemsVo
     * @return
     */
    @RequestMapping(value = "/editItemsAllSubmit")
    public String editItemsAllSubmit(ItemsVo itemsVo){

        return "redirect:queryItems";
    }

}
