package com.taotao.service.controller;


import com.taotao.pojo.SearchResult;
import com.taotao.search.service.SearchItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class SearchController {
    @Autowired
    private SearchItemService searchItemService;
    @Value("${ROWS}")
    private Integer ROWS;
    @RequestMapping("/search")
    public String search(@RequestParam("q") String queryString, @RequestParam(defaultValue = "1") Integer page , Model model) throws  Exception{
        queryString = new String( queryString.getBytes("iso-8859-1"),"utf-8");
        System.out.println("近来啦++++++++++++++++++++++_><<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<");
        SearchResult result=searchItemService.search(queryString,page,ROWS);
        model.addAttribute("query", queryString);
        //${totalPages}
        model.addAttribute("totalPages", result.getPageCount());
        //${itemList} 商品的列表
        model.addAttribute("itemList", result.getItemList());
        //${page}
        model.addAttribute("page", page);
        return "search";
    }
}
