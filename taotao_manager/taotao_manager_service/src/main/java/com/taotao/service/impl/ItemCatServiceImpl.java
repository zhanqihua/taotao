package com.taotao.service.impl;

import com.taotao.service.mapper.ItemCatService;
import com.taotao.mapper.TbItemCatMapper;
import com.taotao.pojo.EasyUITreeNode;
import com.taotao.pojo.TbItemCat;
import com.taotao.pojo.TbItemCatExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ItemCatServiceImpl implements ItemCatService {
    //注入mapper
@Autowired
private TbItemCatMapper tbItemCatMapper;
    @Override
    public List<EasyUITreeNode> getItemCatList(Long parentId) {
        //根据parentId查询对应的itemCats
        TbItemCatExample example = new TbItemCatExample();
        TbItemCatExample.Criteria criteria = example.createCriteria();
        criteria.andParentIdEqualTo(parentId);
        List<TbItemCat> itemCats = tbItemCatMapper.selectByExample(example);
        //转成 List<EasyUITreeNode>
        List<EasyUITreeNode> nodes =new ArrayList<>();
        for (TbItemCat itemCat:itemCats) {
            EasyUITreeNode node = new EasyUITreeNode();
            node.setId(itemCat.getId());
            node.setText(itemCat.getName());
            node.setState(itemCat.getIsParent()?"closed":"open");
            nodes.add(node);
        }
        return nodes;
    }
}
