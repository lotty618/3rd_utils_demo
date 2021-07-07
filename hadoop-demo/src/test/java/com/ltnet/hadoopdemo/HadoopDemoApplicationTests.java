package com.ltnet.hadoopdemo;

import com.ltnet.hadoopdemo.conf.HdfsConfig;
import com.ltnet.hadoopdemo.utils.HdfsUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Map;

@SpringBootTest
class HadoopDemoApplicationTests {

    @Autowired
    // Autowired有4种模式，byName、byType、constructor、autodectect，其中@Autowired注解是使用byType方式的
    private HdfsUtil hdfsUtil;

    @Test
    void contextLoads() {
        boolean b = hdfsUtil.checkExists("/demo");
        List<Map<String, Object>> list = hdfsUtil.listFiles("/", null);
        for (Map<String, Object> map : list) {
            System.out.println(map.get("path") + " / " + ((boolean)map.get("isdir") ? "Directory" : "File") + " / " + map.get("status"));
        }

        try {
            String str = hdfsUtil.readFile("/demo/input/wc2.txt");
            System.out.println(str);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

}
