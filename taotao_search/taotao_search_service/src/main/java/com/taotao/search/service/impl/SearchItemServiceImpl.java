package com.taotao.search.service.impl;


import com.taotao.pojo.SearchItem;
import com.taotao.pojo.SearchResult;
import com.taotao.pojo.TaotaoResult;
import com.taotao.search.dao.SearchDao;
import com.taotao.search.mapper.SearchItemMapper;
import com.taotao.search.service.SearchItemService;
import org.apache.commons.lang3.StringUtils;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.common.SolrInputDocument;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
@Service
public class SearchItemServiceImpl implements SearchItemService {
    @Autowired
    private SearchItemMapper mapper;
    @Autowired
    private SolrServer solrServer;
    @Autowired
    private SearchDao dao;
    @Override
    public TaotaoResult importAllIndex() throws Exception {

        //从数据库中查询所有的商品的数据 ，导入数据到索引库中  用solrj

        //1.创建连接对象solrserver 由spring管理

        //2.调用MApper的方法 返回的是List<sarchItem>
        List<SearchItem> itemList = mapper.getSearchItemList();
        //3.遍历
        List<SolrInputDocument> documents = new ArrayList<>();
        for (SearchItem searchItem : itemList) {
            //4 将集合中的数据  一个个 sarchItem 放入solrinputdocument
            SolrInputDocument document = new SolrInputDocument();
            document.addField("id", searchItem.getId().toString());
            document.addField("item_title", searchItem.getTitle());
            document.addField("item_sell_point", searchItem.getSell_point());
            document.addField("item_image", searchItem.getImage());
            document.addField("item_category_name", searchItem.getCategory_name());
            document.addField("item_price", searchItem.getPrice());
            document.addField("item_desc", searchItem.getItem_desc());
            documents.add(document);
        }
        //5.添加文档到索引库中
        solrServer.add(documents);

        //6.提交
        solrServer.commit();

        return TaotaoResult.ok();
    }

    @Override
    public SearchResult search(String queryString, Integer page, Integer rows) throws Exception {
        //封装solrquery对象（设置各种的查询的条件）  调用dao的方法  返回searchresult(只有总记录书  和商品的列表)  这里添加 补全其他的属性（总页数 ..）

        //1.创建查询的对象Solrquery
        SolrQuery query = new SolrQuery();
        //2.设置查询的主查询条件
        if(StringUtils.isNotBlank(queryString)){
            query.setQuery(queryString);
        }else{
            query.setQuery("*:*");
        }
        //3.设置过滤条件

        //3.1设置分页查询的条件
        if(page==null) page=1;
        if(page<1) page=1;
        if(rows==null) rows=60;

        query.setStart((page-1) * rows);//(page-1) * rows

        query.setRows(rows);
        //3.2设置默认的搜索域

        query.set("df", "item_keywords");

        //3.3 开启高亮 设置高亮显示的域 设置前缀和后缀
        query.setHighlight(true);
        query.addHighlightField("item_title");
        query.setHighlightSimplePre("<em style=\"color:red\">");
        query.setHighlightSimplePost("</em>");
        //4.注入dao  调用dao的方法
        SearchResult result = dao.search(query);

        //5.设置searchresult 对象  返回

        long pageCount = result.getTotalCount()/rows;

        if( result.getTotalCount()% rows>0){
            pageCount++;
        }
        result.setPageCount(pageCount );


        return result;
    }

}
