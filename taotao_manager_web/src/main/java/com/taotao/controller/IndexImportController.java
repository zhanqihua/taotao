package com.taotao.controller;

import com.taotao.pojo.TaotaoResult;
import com.taotao.search.service.SearchItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class IndexImportController {

    @Autowired
    private SearchItemService searchsevice;

    //url
    //参数
    //返回值

    @RequestMapping("/index/importAll")
    @ResponseBody
    public TaotaoResult importAllIndex() throws Exception{
        //引入search服务
        //注入服务
        //调用
        TaotaoResult taotaoResult = searchsevice.importAllIndex();
        //返回
        return taotaoResult;
    }
}

