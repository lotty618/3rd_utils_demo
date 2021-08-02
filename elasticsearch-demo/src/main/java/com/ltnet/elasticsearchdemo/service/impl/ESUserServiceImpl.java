package com.ltnet.elasticsearchdemo.service.impl;

import com.ltnet.elasticsearchdemo.entity.ESEntity;
import com.ltnet.elasticsearchdemo.entity.ESUser;
import com.ltnet.elasticsearchdemo.entity.User;
import com.ltnet.elasticsearchdemo.repository.ESUserRepository;
import com.ltnet.elasticsearchdemo.service.ESUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ESUserServiceImpl implements ESUserService {
    @Autowired
    ESUserRepository userRepository;

    @Override
    public boolean putUser(ESUser user) {
        ESUser ret = userRepository.save(user);
        System.out.println("=== putUser : " + ret);
        return null != ret.getId();
    }

    @Override
    public List<ESUser> findESUserByName(String name) {
//        return userRepository.findESUserByName(name);
        return userRepository.findESUsersByNameContains(name);
    }
}
