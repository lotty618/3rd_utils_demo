package com.ltnet.elasticsearchdemo.service.impl;

import com.ltnet.elasticsearchdemo.common.SysConst;
import com.ltnet.elasticsearchdemo.entity.ESUser;
import com.ltnet.elasticsearchdemo.repository.ESUserRepository;
import com.ltnet.elasticsearchdemo.service.ESIndexService;
import com.ltnet.elasticsearchdemo.utils.ESUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
public class ESIndexServiceImpl implements ESIndexService {
    @Autowired
    ESUtils esUtils;

    @Override
    public boolean createIndex(String index) {
        boolean bExist = false;

        try {
            bExist = esUtils.createIndex(index);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return bExist;
    }

    @Override
    public boolean checkIndex(String index) {
        boolean bExist = false;

        try {
            bExist = esUtils.checkIndexExist(index);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return bExist;
    }

    @Override
    public boolean createDocument(String index, Object obj) {
        boolean bRet = false;

        try {
            esUtils.createDocument(index, obj);
            bRet = true;
        } catch (IOException e) {
            e.printStackTrace();
        }

        return bRet;
    }

    @Override
    public String getDocument(String index) {
        try {
            return esUtils.getDocument(index);
        } catch (IOException e) {
            return null;
        }
    }

    @Override
    public List<String> matchAllQuery(String index, int from, int size, String sortField, SysConst.SortOrder order) {
        try {
            return esUtils.matchAllQuery(index, from, size, sortField, order);
        } catch (IOException e) {
            return null;
        }
    }

}
