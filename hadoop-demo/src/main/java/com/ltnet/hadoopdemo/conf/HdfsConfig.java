package com.ltnet.hadoopdemo.conf;

import com.ltnet.hadoopdemo.utils.HdfsUtil;
import org.apache.hadoop.hdfs.server.common.HdfsServerConstants;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class HdfsConfig {
    @Value("${hdfs.url}")
    private String defaultHdfsUrl;
    @Value("${hdfs.username}")
    private String username;

    @Bean
    public HdfsUtil getHBaseService() {
        org.apache.hadoop.conf.Configuration conf = new org.apache.hadoop.conf.Configuration();
        conf.set("fs.defaultFS", defaultHdfsUrl);

        // 读超时时间：dfs.client.socket-timeout。默认值1分钟。
        // 写超时时间：dfs.datanode.socket.write.timeout。默认8分钟。

        // 设置客户端连接超时时间，不设置或过短则会报超时：ConnectException: Connection timed out: no further information
        conf.set("dfs.client.socket-timeout", "60000");

        return new HdfsUtil(conf, username, defaultHdfsUrl);
    }
}
