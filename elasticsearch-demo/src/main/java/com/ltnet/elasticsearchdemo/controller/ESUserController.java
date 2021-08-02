package com.ltnet.elasticsearchdemo.controller;

import com.ltnet.elasticsearchdemo.entity.ESEntity;
import com.ltnet.elasticsearchdemo.entity.ESUser;
import com.ltnet.elasticsearchdemo.entity.User;
import com.ltnet.elasticsearchdemo.service.ESUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/user")
public class ESUserController {

    @Autowired
    ESUserService esUserService;

    @RequestMapping("/import")
    public String importAll() {
        return "Success";
    }

//    @RequestMapping("/put")
//    public String createDocument() {
//        ESUser user = new ESUser();
//        user.setId(1);
//        user.setName("James");
//        user.setAge(30);
//        user.setAddress("Boston");
//        return esUserService.putUser(user) ? "Success" : "Failed";
//    }

    @RequestMapping("/put")
    public String createDocument(@RequestParam String name, @RequestParam int age, @RequestParam String address) {
        ESUser user = new ESUser();
//        user.setId(1);
        user.setName(name);
        user.setAge(age);
        user.setAddress(address);
        return esUserService.putUser(user) ? "Success" : "Failed";
    }

    @RequestMapping("/get/{name}")
    public String getUserByName(@PathVariable String name) {
        String ret = "";
        List<ESUser> list = esUserService.findESUserByName(name);
        for (ESUser user : list) {
            ret = user.getName() + " / " + user.getAge() + " / " + user.getAddress() + "\n";
        }

        return ret;
    }
}
