package com.taotao.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.taotao.service.mapper.ItemService;
import com.taotao.pojo.*;
import com.taotao.mapper.TbItemMapper;
import com.taotao.utils.IDUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.Date;
import java.util.List;

@Service
public class ItemServiceImpl implements ItemService {

    @Autowired
    private TbItemMapper mapper;

    @Override
    public EasyUIDataGridResult getItemList(Integer page, Integer rows) {
        //设置分页的条件
        PageHelper.startPage(page, rows);
        //只会执行第一次分页查询
        //创建example
        TbItemExample example = new TbItemExample();
        //执行查询 获取查询的结果
        List<TbItem> tbItemList = mapper.selectByExample(example);

        //封装分页信息,pageInfo是分页插件自带的对象
        PageInfo<TbItem> pageInfo = new PageInfo(tbItemList);
        //设置分页信息到easyuidataGridResult中
        EasyUIDataGridResult result = new EasyUIDataGridResult();
        result.setTotal(pageInfo.getTotal());
        result.setRows(pageInfo.getList());
        return result;
    }

    @Override
    public TaotaoResult saveItem(TbItem item, String desc) {
        //补全商品的Id
        long itemId = IDUtils.genItemId();
        item.setId(itemId);
        //补全商品添加时间
        item.setCreated(new Date());
        //补全更新时间
        item.setUpdated(item.getCreated());
        //补全商品的状态
        item.setStatus((byte) 1);
        mapper.insertSelective(item);
        TbItemDesc tbItemDesc = new TbItemDesc();
        //补全商品描述的添加时间,更新时间以及商品的ID
        tbItemDesc.setItemId(itemId);
        tbItemDesc.setItemDesc(desc);
        tbItemDesc.setCreated(item.getCreated());
        tbItemDesc.setUpdated(item.getCreated());
        return TaotaoResult.ok();
    }
}
