package com.ltnet.elasticsearchdemo.controller;

import com.ltnet.elasticsearchdemo.common.SysConst;
import com.ltnet.elasticsearchdemo.entity.ESUser;
import com.ltnet.elasticsearchdemo.entity.User;
import com.ltnet.elasticsearchdemo.service.ESIndexService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/es")
public class ESIndexController {
    @Autowired
    ESIndexService esIndexService;

    @RequestMapping("/check_index/{index}")
    public String check(@PathVariable String index) {
        return index + (esIndexService.checkIndex(index) ? " exist" : " not exist");
    }

    @RequestMapping("/create_index/{index}")
    public String createIndex(@PathVariable String index) {
        return index + (esIndexService.createIndex(index) ? " Create Successfully" : " Create Failed");
    }

    @RequestMapping("/create_doc/{index}")
    public String createDocument(@PathVariable String index) {
        User user = new User();
        user.setName("Hello");
        user.setAge(26);
        user.setAddress("Shenzhen");
        return esIndexService.createDocument(index, user) ? " Success" : "Failed";
    }

    @RequestMapping("/create_doc")
    public String createDocument(@RequestParam String index, @RequestParam String name,
                                 @RequestParam int age, @RequestParam String address) {
        User user = new User();
        user.setName(name);
        user.setAge(age);
        user.setAddress(address);
        return esIndexService.createDocument(index, user) ? " Success" : "Failed";
    }

    @RequestMapping("/get_doc/{index}")
    public String getDoc(@PathVariable String index) {
        String ret = "";
        List<String> list = esIndexService.matchAllQuery(index, 0, 2, "name", SysConst.SortOrder.ASC);
        for (String s : list) {
            ret = s + "\n";
        }
        return ret;
    }

}
