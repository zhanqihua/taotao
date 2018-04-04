package com.taotao.controller;

import com.taotao.content.service.ContentCategoryService;
import com.taotao.pojo.EasyUITreeNode;
import com.taotao.pojo.TaotaoResult;
import com.taotao.pojo.TbContentCategory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class ContentCategoryController {

    @Autowired
    private ContentCategoryService contentCategoryService;

    /**
     * 查询所有的内容分类以树状返回
     * @param parentId
     * @return
     */
    @RequestMapping("/content/category/list")
    @ResponseBody
    public List<EasyUITreeNode> getContentCategoryList(@RequestParam(value = "id",defaultValue = "0") Long parentId){
       List<EasyUITreeNode> nodes = contentCategoryService.getContentCategoryList(parentId);

        return nodes;
    }

    /**
     * 新增内容分类
     * @param tbContentCategory
     * @return
     */
    @RequestMapping("/content/category/create")
    @ResponseBody
    public TaotaoResult saveContent(TbContentCategory tbContentCategory){
        TaotaoResult result = contentCategoryService.saveContent(tbContentCategory);

        return  result;
    }

    /**
     * 根据ID修改内容分类的名称
     * @param tbContentCategory
     * @return
     */
    @RequestMapping("/content/category/update")
    @ResponseBody
    public TaotaoResult updateContentCategoryById(TbContentCategory tbContentCategory){
      TaotaoResult result =   contentCategoryService.updateContentCategoryById(tbContentCategory);

        return result;
    }
    @RequestMapping("/content/category/delete/")
    @ResponseBody
    public  TaotaoResult deleteById(Long id){
        TaotaoResult result =contentCategoryService.deleteById(id);

        return result;
    }
}
