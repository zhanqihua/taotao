package com.taotao.service.mapper;

import com.taotao.pojo.EasyUIDataGridResult;
import com.taotao.pojo.TaotaoResult;
import com.taotao.pojo.TbItem;

public interface ItemService {
    /**
     *
     * @param page 当前的页码
     * @param rows 每页显示的行数
     * @return
     */
    public EasyUIDataGridResult getItemList(Integer page, Integer rows);
    /**
     * 添加商品
     * @param item
     * @param desc
     * @return
     */
    TaotaoResult saveItem(TbItem item, String desc);
}
