package com.taotao.controller;


import com.taotao.content.service.ContentService;
import com.taotao.pojo.EasyUIDataGridResult;
import com.taotao.pojo.TaotaoResult;
import com.taotao.pojo.TbContent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller
public class ContentController {
    @Autowired
    private ContentService contentService;

    /**
     * 根据内容分类id查询对应的内容
     * @param categoryId
     * @param page
     * @param rows
     * @return
     */
   @ RequestMapping("/content/query/list")
    @ResponseBody
    public EasyUIDataGridResult getContentList(Long categoryId,Integer page,Integer rows){
       EasyUIDataGridResult result = contentService.getContentList(categoryId, page, rows);
       return result;

    }

    /**
     * 根据内容id删除对应的内容
     * @param ids
     * @return
     */
    @RequestMapping("/content/delete")
    @ResponseBody
    public TaotaoResult deleteById(String ids){

       TaotaoResult result = contentService.deleteById(ids);
        return  result;
    }

    /**
     * 添加新的内容
     * @param tbContent
     * @return
     */
    @RequestMapping("/content/save")
    @ResponseBody
    public TaotaoResult saveContent(TbContent tbContent){
        TaotaoResult result = contentService.saveContent(tbContent);

        return  result;
    }

    /**
     * 根据contentId更新content
     * @param tbContent
     * @return
     */
    @RequestMapping("/rest/content/edit")
    @ResponseBody
    public TaotaoResult updateContent(TbContent tbContent){
        TaotaoResult result = contentService.updateContent(tbContent);

        return  result;
    }

    /**
     * 根据ID查询对应的Content中的content
     * @param id
     * @return
     */
    @RequestMapping("/content-edit-content")
    @ResponseBody
    public TaotaoResult findContent(Long id){
       TbContent tbContent = contentService.findContent(id);
        String content = tbContent.getContent();

        return  TaotaoResult.ok(content);
    }

}
