package com.ltnet.hadoopdemo.service.hdfs;

public interface HdfsService {
    boolean checkExists(String path);
    boolean mkdir(String path);
    String getFileList(String path);
    String read(String path);
    boolean get(String src, String dst);
    boolean put(boolean delSrc, boolean overwrite, String file, String dstPath);
}
