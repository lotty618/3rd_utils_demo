package com.ltnet.hadoopdemo.service.mapreduce.wordcount;

public interface WordCountService {
    String doWordCount(String jobName, String inputPath, String outputPath);
}
