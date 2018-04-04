package com.taotao.controller;

import com.taotao.controller.com.taotao.web.utils.FastDFSClient;
import com.taotao.pojo.EasyUITreeNode;
import com.taotao.service.mapper.ItemCatService;
import com.taotao.utils.JsonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class ItemCatController {

    @Autowired
    private ItemCatService itemCatService;
    @RequestMapping("/item/cat/list")
    @ResponseBody
    public List<EasyUITreeNode> getItemCatList(@RequestParam(value = "id",defaultValue = "0") Long parentId){
        System.out.println("进来啦");
        List<EasyUITreeNode> nodes = itemCatService.getItemCatList(parentId);

        return nodes;
    }
    //使用属性文件的方式获取值，在类中添加如下的成员变量：
    @Value("${TAOTAO_IMAGE_SERVER_URL}")
    private String TAOTAO_IMAGE_SERVER_URL;
    @RequestMapping(value = "/pic/upload",produces = MediaType.TEXT_PLAIN_VALUE+";charset=utf-8")
    @ResponseBody//MultipartFile  uploadFile
    public String uploadFile(@PathVariable("uploadFile") MultipartFile  uploadFile){
        //通过map把数据传到前端
        System.out.println("近来啦");
        try {
            //通过工具类FastDFSClient获取客户端

            FastDFSClient dfsClient = new FastDFSClient("classpath:resources/client.properties");
            String originalFilename = uploadFile.getOriginalFilename();
            String extname = originalFilename.substring(originalFilename.lastIndexOf(".") + 1);
            String path = dfsClient.uploadFile(uploadFile.getBytes(), extname);
            String url = TAOTAO_IMAGE_SERVER_URL+path;
            System.out.println(url+"++++++++++++++++++++++++++++>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
            Map<String,Object> map = new HashMap<>();
            map.put("error",0);
            map.put("url",url);
            String json=JsonUtils.objectToJson(map);
                    return json;
        } catch (Exception e) {
            e.printStackTrace();
            Map<String,Object> map = new HashMap<>();
            map.put("error",1);
            map.put("message","图片上传失败");
            String json=JsonUtils.objectToJson(map);
            return json;
        }
    }

}
