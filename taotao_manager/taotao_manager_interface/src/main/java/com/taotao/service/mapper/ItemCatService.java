package com.taotao.service.mapper;

import com.taotao.pojo.EasyUITreeNode;

import java.util.List;

public interface ItemCatService {
    public List<EasyUITreeNode> getItemCatList(Long parentId);
}
