package com.taotao.controller;

import com.taotao.service.ItemService;
import com.taotao.pojo.EasyUIDataGridResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class ItemController {
@Autowired
private ItemService itemService;

    /**
     * 一个方法解决打开各个窗口,使用变量page来解决路径不一致问题
     * @param page 页面传递过来的item-list之类的,返回的也是list-item页面
     * @return
     */
    @RequestMapping("/{page}")
    public String showPage(@PathVariable String page) {
        return page;
    }


    /**
     * 分页查询Item
     * @param page
     * @param rows
     * @return
     */
    @RequestMapping("/item/list")
    @ResponseBody
    public EasyUIDataGridResult getItemList(Integer page,Integer rows){
        EasyUIDataGridResult result = itemService.getItemList(page, rows);
        return result;
    }
}
