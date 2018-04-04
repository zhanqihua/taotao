package com.taotao.search.service;

import com.taotao.pojo.SearchResult;
import com.taotao.pojo.TaotaoResult;

public interface SearchItemService {
    /**
     * 导入所有的数据库的数据到索引库中
     * @return
     */
    public TaotaoResult importAllIndex() throws Exception;


    /**
     * 根据条件分页查询数据
     * @param queryString
     * @param page
     * @param rows
     * @return
     * @throws Exception
     */
    public SearchResult search(String queryString, Integer page, Integer rows) throws Exception;
//
}
