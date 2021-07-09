package com.ltnet.hadoopdemo.controller;

import com.ltnet.hadoopdemo.service.hdfs.HdfsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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

    @RequestMapping("/put")
    public String put(@RequestParam("src") String src, @RequestParam("dst") String dst) {
        return hdfsService.put(false, true, src, dst) ? "Success" : "Failed";
    }

    @RequestMapping("/read")
    public String read(@RequestParam String path) {
        return hdfsService.read(path);
    }

    @RequestMapping("/download")
    public String download(@RequestParam("src") String src, @RequestParam("dst") String dst) {
        return hdfsService.get(src, dst) ? "Success" : "Failed";
    }
}
