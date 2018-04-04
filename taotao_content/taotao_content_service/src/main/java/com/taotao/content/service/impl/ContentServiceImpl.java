package com.taotao.content.service.impl;


import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.sun.org.apache.regexp.internal.RE;
import com.taotao.content.redis.JedisClient;
import com.taotao.content.service.ContentService;
import com.taotao.mapper.TbContentCategoryMapper;
import com.taotao.mapper.TbContentMapper;
import com.taotao.pojo.*;

import com.taotao.utils.JsonUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class ContentServiceImpl implements ContentService{
    @Autowired
    private TbContentMapper mapper;
    @Autowired
    private JedisClient jedisClient;
    @Override
    public EasyUIDataGridResult getContentList(Long categoryId,Integer page,Integer rows) {
        //分页查询
        TbContentExample example = new TbContentExample();
        example.createCriteria().andCategoryIdEqualTo(categoryId);
        PageHelper.startPage(page, rows);
        List<TbContent> contents = mapper.selectByExample(example);
        PageInfo<TbContent> info = new PageInfo<TbContent>(contents);
        EasyUIDataGridResult result = new EasyUIDataGridResult();
        result.setRows(info.getList());
        result.setTotal(info.getTotal());
        return result;
    }

    @Override
    public TaotaoResult saveContent(TbContent tbContent) {
        mapper.insertSelective(tbContent);
        try {
            jedisClient.hdel("TBCONTENTCATEGORYREDIS",tbContent.getCategoryId()+"");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return TaotaoResult.ok();
    }

    @Override
    public TaotaoResult deleteById(String ids) {
        String[] split = ids.split(",");
        TbContent tbContent = mapper.selectByPrimaryKey(Long.parseLong(split[0]));
        if (split.length>0){

            try {
                jedisClient.hdel("TBCONTENTCATEGORYREDIS",tbContent.getCategoryId()+"");
            } catch (Exception e) {
                e.printStackTrace();
            }
            for (String id : split) {
                mapper.deleteByPrimaryKey(Long.parseLong(id));
            }
        }
        return TaotaoResult.ok();
    }

    @Override
    public List<TbContent> getContentListByCategoryId(Long categoryId) {
        try {
            String tbcontentcategoryredis = jedisClient.hget("TBCONTENTCATEGORYREDIS", categoryId + "");
            if (StringUtils.isNotBlank(tbcontentcategoryredis)){

                List<TbContent> contents = JsonUtils.jsonToList(tbcontentcategoryredis, TbContent.class);
                System.out.println("redis中有缓存");
                return  contents;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        TbContentExample example = new TbContentExample();
        example.createCriteria().andCategoryIdEqualTo(categoryId);
        List<TbContent> contents = mapper.selectByExample(example);
        try {
            jedisClient.hset("TBCONTENTCATEGORYREDIS",categoryId+"", JsonUtils.objectToJson(contents));
            System.out.println("redis中没有缓存");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return contents;
    }

    @Override
    public TaotaoResult updateContent(TbContent tbContent) {
        mapper.updateByPrimaryKeySelective(tbContent);
        try {
            jedisClient.hdel("TBCONTENTCATEGORYREDIS",tbContent.getCategoryId()+"");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return TaotaoResult.ok();
    }

    @Override
    public TbContent findContent(Long id) {
        TbContent content = mapper.selectByPrimaryKey(id);
        return content;
    }
}
