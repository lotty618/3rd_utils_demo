package com.ltnet.hadoopdemo.service.hdfs.impl;

import com.ltnet.hadoopdemo.service.hdfs.HdfsService;
import com.ltnet.hadoopdemo.utils.HdfsUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class HdfsServiceImpl implements HdfsService {
    @Autowired
    HdfsUtil hdfsUtil;

    @Override
    public boolean checkExists(String path) {
        return hdfsUtil.checkExists(path);
    }

    @Override
    public boolean mkdir(String path) {
        return hdfsUtil.mkdir(path);
    }

    @Override
    public String getFileList(String path) {
        return hdfsUtil.listFiles(path, null).toString();
    }

    @Override
    public String read(String path) {
        try {
            return hdfsUtil.read(path);
        } catch (Exception e) {
            return "Read Error!";
        }
    }

    @Override
    public boolean get(String src, String dst) {
        return hdfsUtil.get(src, dst);
    }

    @Override
    public boolean put(boolean delSrc, boolean overwrite, String file, String dstPath) {
        return hdfsUtil.put(delSrc, overwrite, file, dstPath);
    }
}
