package com.ltnet.elasticsearchdemo.service;

import com.ltnet.elasticsearchdemo.common.SysConst;
import com.ltnet.elasticsearchdemo.entity.ESUser;

import java.io.IOException;
import java.util.List;

public interface ESIndexService {
    boolean createIndex(String index);
    boolean checkIndex(String index);
    boolean createDocument(String index, Object obj);
    String getDocument(String index);
    List<String> matchAllQuery(String index, int from, int size, String sortField, SysConst.SortOrder order);
}
