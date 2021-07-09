package com.ltnet.hadoopdemo.controller;

import com.ltnet.hadoopdemo.service.mapreduce.wordcount.WordCountService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/mr")
public class MapReduceController {

    @Autowired
    WordCountService wordCountService;

    @RequestMapping("/wordcount")
    public String wordCount(@RequestParam("path") String path, @RequestParam("job") String job) {
        if (StringUtils.isEmpty(job)) {
            job = "wc";
        }
        return wordCountService.doWordCount(job, path, null);
    }
}
