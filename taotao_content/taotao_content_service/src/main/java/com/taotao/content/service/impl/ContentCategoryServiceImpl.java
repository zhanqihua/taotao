package com.taotao.content.service.impl;

import com.taotao.content.service.ContentCategoryService;
import com.taotao.mapper.TbContentCategoryMapper;
import com.taotao.pojo.EasyUITreeNode;
import com.taotao.pojo.TaotaoResult;
import com.taotao.pojo.TbContentCategory;
import com.taotao.pojo.TbContentCategoryExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
@Service
public class ContentCategoryServiceImpl implements ContentCategoryService {

    @Autowired
    private TbContentCategoryMapper tbContentCategoryMapper;
    @Override
    public List<EasyUITreeNode> getContentCategoryList(Long parentId) {
        TbContentCategoryExample example = new TbContentCategoryExample();
        example.createCriteria().andParentIdEqualTo(parentId);
        List<TbContentCategory> list = tbContentCategoryMapper.selectByExample(example);
        List<EasyUITreeNode> nodes = new ArrayList<>();
        for (TbContentCategory category : list) {
            EasyUITreeNode node = new EasyUITreeNode();
            node.setId(category.getId());
            node.setText(category.getName());
            node.setState(category.getIsParent()?"closed":"open");
            nodes.add(node);
        }
        return nodes;
    }

    @Override
    public TaotaoResult saveContent(TbContentCategory tbContentCategory) {
        tbContentCategory.setCreated(new Date());
        tbContentCategory.setUpdated(tbContentCategory.getCreated());
        tbContentCategory.setIsParent(false);
        tbContentCategory.setSortOrder(1);
        tbContentCategory.setStatus(1);
        //判断所选节点是否为父节点
        TbContentCategory parentContentCategory = tbContentCategoryMapper.selectByPrimaryKey(tbContentCategory.getParentId());
        if (!parentContentCategory.getIsParent()){
            parentContentCategory.setIsParent(true);
            tbContentCategoryMapper.updateByPrimaryKey(parentContentCategory);
        }
        tbContentCategoryMapper.insertSelective(tbContentCategory);
        return TaotaoResult.ok(tbContentCategory);
    }

    @Override
    public TaotaoResult updateContentCategoryById(TbContentCategory tbContentCategory) {
        tbContentCategoryMapper.updateByPrimaryKeySelective(tbContentCategory);
        return TaotaoResult.ok();
    }

    @Override
    public TaotaoResult deleteById(Long id) {
        TbContentCategory category = tbContentCategoryMapper.selectByPrimaryKey(id);
        if (category.getIsParent()){
            return TaotaoResult.build(300,"您删除的不是最底层分类,请重新选择");
        }else {
            //执行删除
            tbContentCategoryMapper.deleteByPrimaryKey(id);
            //判断父目录中的内容分类是否只有一个,如果是一个就将父目录变成子节点,否则不做改变
            Long parentId = category.getParentId();
            TbContentCategory parentCategory = tbContentCategoryMapper.selectByPrimaryKey(parentId);
            TbContentCategoryExample example = new TbContentCategoryExample();
            example.createCriteria().andParentIdEqualTo(parentId);
            List<TbContentCategory> categories = tbContentCategoryMapper.selectByExample(example);
            if (categories.size()==0){
                parentCategory.setIsParent(false);
                tbContentCategoryMapper.updateByPrimaryKey(parentCategory);
            }
            return TaotaoResult.ok();
        }
    }

}
