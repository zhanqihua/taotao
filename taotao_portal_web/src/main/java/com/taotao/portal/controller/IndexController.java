package com.taotao.portal.controller;

import com.taotao.content.service.ContentService;
import com.taotao.pojo.TbContent;
import com.taotao.portal.pojo.Ad1Node;
import com.taotao.utils.JsonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
public class IndexController {
        @Autowired
        private ContentService contentService;
        @RequestMapping("/index")
        public String showIndex(Model model){

            List<TbContent>  list = contentService.getContentListByCategoryId(102l);
            List<Ad1Node> nodes = new ArrayList<>();
                for (TbContent tbContent : list) {
                        Ad1Node node = new Ad1Node();
                        node.setAlt(tbContent.getSubTitle());
                        node.setHeight("240");
                        node.setHeightB("240");
                        node.setWidth("670");
                        node.setWidthB("550");
                        node.setHref(tbContent.getUrl());
                        node.setSrc(tbContent.getPic());
                        node.setSrcB(tbContent.getPic2());
                        nodes.add(node);
                }
            System.out.println(JsonUtils.objectToJson(nodes));
          model.addAttribute("ad1", JsonUtils.objectToJson(nodes));
            return "index";
        }
}
