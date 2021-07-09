package com.ltnet.hadoopdemo.service.hdfs;

public interface HdfsService {
    boolean checkExists(String path);
    boolean mkdir(String path);
    String getFileList(String path);
}
