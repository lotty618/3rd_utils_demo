package com.ltnet.elasticsearchdemo.service;

import com.ltnet.elasticsearchdemo.entity.ESEntity;
import com.ltnet.elasticsearchdemo.entity.ESUser;
import com.ltnet.elasticsearchdemo.entity.User;

import java.util.List;

public interface ESUserService {
    boolean putUser(ESUser user);
    List<ESUser> findESUserByName(String name);
}
