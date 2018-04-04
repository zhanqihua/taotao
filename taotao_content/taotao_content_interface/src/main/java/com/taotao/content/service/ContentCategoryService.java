package com.taotao.content.service;

import com.taotao.pojo.EasyUITreeNode;
import com.taotao.pojo.TaotaoResult;
import com.taotao.pojo.TbContentCategory;

import java.util.List;

public interface ContentCategoryService {
    /**
     * 查询对应parentId下面所有的节点
     * @param parentId
     * @return
     */
    List<EasyUITreeNode> getContentCategoryList(Long parentId);

    /**
     * 保存contentCategory(页面传过来的parentId 和 text)
     * @param tbContentCategory
     * @return
     */
    TaotaoResult saveContent(TbContentCategory tbContentCategory);

    /**
     * 根据id修改对应的内容分类的名字
     * @param
     * @return
     */
    TaotaoResult updateContentCategoryById(TbContentCategory tbContentCategory);

    /**
     * 根据ID删除当前的内容分类
     * @param id
     * @return
     */
    TaotaoResult deleteById(Long id);
}