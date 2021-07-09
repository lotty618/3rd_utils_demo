package com.ltnet.hadoopdemo.service.mapreduce.wordcount;

import com.huaban.analysis.jieba.JiebaSegmenter;
import com.huaban.analysis.jieba.SegToken;
import org.apache.commons.lang3.StringUtils;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.IOException;
import java.util.List;

public class WordCountMap extends Mapper<Object, Text, Text, IntWritable> {
    private IntWritable one = new IntWritable(1);
    private Text word = new Text();

    @Override
    protected void map(Object key, Text value, Context context) throws IOException, InterruptedException {
        // 防止中文乱码
        String line = new String(value.getBytes(), 0, value.getLength(), "UTF-8").trim();

        if (StringUtils.isNotEmpty(line)) {
            // 使用jieba分词器
            JiebaSegmenter segmenter = new JiebaSegmenter();
            List<SegToken> tokens = segmenter.process(line, JiebaSegmenter.SegMode.SEARCH);

            for (SegToken token : tokens) {
                System.out.println(token.word + ": 开始位置 " + token.startOffset);
                word.set(token.word);
                context.write(word, one);
            }
        }
    }
}
