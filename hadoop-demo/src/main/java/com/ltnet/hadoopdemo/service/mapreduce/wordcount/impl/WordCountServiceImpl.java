package com.ltnet.hadoopdemo.service.mapreduce.wordcount.impl;

import com.ltnet.hadoopdemo.service.mapreduce.wordcount.WordCountMap;
import com.ltnet.hadoopdemo.service.mapreduce.wordcount.WordCountReduce;
import com.ltnet.hadoopdemo.service.mapreduce.wordcount.WordCountService;
import com.ltnet.hadoopdemo.utils.MapReduceJobUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapred.JobConf;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.CombineTextInputFormat;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;

@Service
public class WordCountServiceImpl implements WordCountService {

    @Autowired
    MapReduceJobUtil jobUtil;

    @Override
    public String doWordCount(String jobName, String inputPath, String outputPath) {
        return jobUtil.doJob(jobName, inputPath, outputPath);
    }
}
