package com.taotao.service;

import com.taotao.pojo.EasyUIDataGridResult;
import com.taotao.pojo.TbItemParam;

public interface ItemService {
    /**
     *
     * @param page 当前的页码
     * @param rows 每页显示的行数
     * @return
     */
    public EasyUIDataGridResult getItemList(Integer page, Integer rows);
}
