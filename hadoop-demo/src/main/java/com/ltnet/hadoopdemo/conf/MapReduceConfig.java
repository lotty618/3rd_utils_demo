package com.ltnet.hadoopdemo.conf;

import com.ltnet.hadoopdemo.utils.HdfsUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MapReduceConfig {
    @Value("${hdfs.url}")
    private String defaultHdfsUrl;
    @Value("${hdfs.username}")
    private String username;

    @Bean
    public org.apache.hadoop.conf.Configuration getMapReduceConfig() {
        org.apache.hadoop.conf.Configuration conf = new org.apache.hadoop.conf.Configuration();
        conf.set("fs.defaultFS", defaultHdfsUrl);
        conf.set("DHADOOP_USER_NAME", username);
//        conf.set("mapred.job.tracker", hdfsPath);
//        conf.set("mapreduce.framework.name", "yarn");
//        conf.set("yarn.resourcemanmager.hostname", host);
//        conf.set("yarn.resourcemanager.address", host+":8032");
//        conf.set("yarn.resourcemanager.scheduler.address", host+":8030");
//        conf.set("mapreduce.app-submission.cross-platform", "true");
        return conf;
    }
}
