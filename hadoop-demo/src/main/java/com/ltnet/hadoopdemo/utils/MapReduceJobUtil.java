package com.ltnet.hadoopdemo.utils;

import com.ltnet.hadoopdemo.service.mapreduce.wordcount.WordCountMap;
import com.ltnet.hadoopdemo.service.mapreduce.wordcount.WordCountReduce;
import org.apache.commons.lang3.StringUtils;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.CombineTextInputFormat;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;

@Component
public class MapReduceJobUtil {
    @Autowired
    private Configuration conf;
    private static final int bufSize = 4 * 1024 * 1024;

    private SimpleDateFormat df = new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss");
    private String BATCH_NAME = df.format(new Date());

    public String doJob(String jobName, String inputPath, String outputPath) {
        if (StringUtils.isEmpty(jobName)) {
            return "Job Name is not allowed to be null";
        }
        if (StringUtils.isEmpty(inputPath)) {
            return "Input path is not allowed to be null";
        }

        // 存储路径
        if (StringUtils.isEmpty(outputPath)) {
            outputPath = inputPath + "_output" + "/" + jobName + "_" + BATCH_NAME;
        }

        try {
            System.setProperty("HADOOP_USER_NAME", conf.get("DHADOOP_USER_NAME"));
            Job job = Job.getInstance(conf, jobName);

            job.setMapperClass(WordCountMap.class);
            job.setCombinerClass(WordCountReduce.class);
            job.setReducerClass(WordCountReduce.class);

            // 指定Mapper输出数据的（key,value）类型
            job.setOutputKeyClass(Text.class);
            job.setOutputValueClass(IntWritable.class);

            // 小文件合并设置
            job.setInputFormatClass(CombineTextInputFormat.class);
            // 最大分片
            CombineTextInputFormat.setMaxInputSplitSize(job, bufSize);
            // 最小分片
            CombineTextInputFormat.setMinInputSplitSize(job, bufSize);

            FileInputFormat.addInputPath(job, new Path(inputPath));
            FileOutputFormat.setOutputPath(job, new Path(outputPath));

            // 将job交给YARN去运行
            job.waitForCompletion(true);

        } catch (Exception e) {
            e.printStackTrace();
            return e.getMessage();
        }

        return "Success";
    }
}
