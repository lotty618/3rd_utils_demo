package com.ltnet.hadoopdemo.controller;

import com.ltnet.hadoopdemo.service.hdfs.HdfsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/hdfs")
public class HdfsController {
    @Autowired
    HdfsService hdfsService;

    @RequestMapping("/check/{path}")
    public String checkExists(@PathVariable String path) {
        boolean bExists = hdfsService.checkExists(path);
        return path + (bExists ? " 目录存在！" : "目录不存在！");
    }

    @RequestMapping("/mkdir/{path}")
    public String mkdir(@PathVariable String path) {
        boolean bmkdir = hdfsService.mkdir(path);
        return path + (bmkdir ? " 目录创建成功！" : "目录创建失败！");
    }

    @RequestMapping("/list/{path}")
    public String getFileList(@PathVariable String path) {
        return hdfsService.getFileList(path);
    }
}
