package com.taotao.content.service;

import com.taotao.pojo.EasyUIDataGridResult;
import com.taotao.pojo.TaotaoResult;
import com.taotao.pojo.TbContent;
import com.taotao.pojo.TbContentCategory;
import sun.util.resources.ga.LocaleNames_ga;

import java.util.List;

public interface ContentService {
    /**
     * 根据内容分类id查询对应的内容
     * @param categoryId
     * @param page
     * @param rows
     * @return
     */
    EasyUIDataGridResult getContentList(Long categoryId,Integer page,Integer rows);

    /**
     * 添加新的内容
     * @param tbContent
     * @return
     */
    TaotaoResult saveContent(TbContent tbContent);

    /**
     * 根据内容id删除内容
     * @param ids
     * @return
     */
    TaotaoResult deleteById(String ids);

    /**
     * 根据分类ID查询对应所有的内容
     * @param i
     * @return
     */
    List<TbContent> getContentListByCategoryId(Long categoryId);
    /**
     * 根据contentId更新content
     * @param tbContent
     * @return
     */
    TaotaoResult updateContent(TbContent tbContent);

    /**
     * 根据ID查询content
     * @param id
     * @return
     */
    TbContent findContent(Long id);
}
