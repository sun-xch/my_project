package com.springmvc.controller;

import com.springmvc.controller.validation.ValidGroup1;
import com.springmvc.dto.ItemsVo;
import com.springmvc.entity.Items;
import com.springmvc.exception.ItemsException;
import com.springmvc.service.ItemsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Controller
@RequestMapping("/items")
public class ItemsController{

    @Autowired
    private ItemsService itemsService;

    /**
     * 商品分类
     * @return
     */
    //itemTypes表示最终方法返回值放在request中的key
    @ModelAttribute("itemTypes")
    public Map<String, String> getItemTypes(){
        Map<String, String> itemTypes = new HashMap<String, String>();
        itemTypes.put("101","数码");
        itemTypes.put("102","母婴");
        return itemTypes;
    }

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
    public String queryItemsById(Model model, String id) throws Exception {
        Items items = new Items();
        items.setId(id);
        Items item = itemsService.queryItemsById(items);
        if(item == null){
            throw new ItemsException("修改的商品不存在！");
        }
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
    // @ModelAttribute("item") 可以指定pojo回显到页面在request域中的key(回显还可以用下面注释掉的mode回显)
    //简单类型的数据回显只能使用model.addAttribute("id",id);(回显用)
    @RequestMapping(value = "/updateItemById")
    public String updateItemById(HttpServletRequest request, Model model, @ModelAttribute("item") @Validated(value = {ValidGroup1.class}) Items items, BindingResult bindingResult, MultipartFile items_pic) throws Exception {

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
            //model.addAttribute("item",items);(回显用)
            return "items/editItem";
        }
        //上传图片
        //图片原始名称
        String originalFilename = items_pic.getOriginalFilename();
        if(items_pic != null && originalFilename != null && originalFilename.length()>0){
            //存储图片的屋里路径
            String pic_path = "D:/sxc/file/picture/";
            //新的图片名称
            String newFileName = UUID.randomUUID() + originalFilename.substring(originalFilename.lastIndexOf("."));
            //新的图片
            File newFile= new File(pic_path + newFileName);
            //将内存中的数据写入磁盘
            items_pic.transferTo(newFile);
            //若上传成功 将图片名称写到Items中
            items.setPic(newFileName);
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

    /**
     *restful风格请求
     * @param id
     * @return
     */
    @RequestMapping("/itemView/{id}")
    public @ResponseBody Items itemView(@PathVariable("id") String id){
        Items items = new Items();
        items.setId(id);
        Items item = itemsService.queryItemsById(items);
        return item;
    }

}
