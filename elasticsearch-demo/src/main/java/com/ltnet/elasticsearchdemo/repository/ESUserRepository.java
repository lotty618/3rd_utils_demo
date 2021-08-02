package com.ltnet.elasticsearchdemo.repository;

import com.ltnet.elasticsearchdemo.entity.ESEntity;
import com.ltnet.elasticsearchdemo.entity.ESUser;
import com.ltnet.elasticsearchdemo.entity.User;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.List;


public interface ESUserRepository extends ElasticsearchRepository<ESUser, String> {
//    List<ESEntity<User>> findESUserByName(String name);
    List<ESUser> findESUserByName(String name);
    List<ESUser> findESUsersByNameContains(String name);
}
