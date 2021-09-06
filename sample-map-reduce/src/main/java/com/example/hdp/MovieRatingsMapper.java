package com.example.hdp;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.IOException;

public class MovieRatingsMapper extends Mapper<Object, Text, Text, IntWritable> {

    private static final Text movieKey = new Text();
    private static final IntWritable ratingFlag = new IntWritable(1);

    @Override
    protected void map(Object key, Text movieRatingRecord,
                       Context context) throws IOException, InterruptedException {
        String[] movieRatingColumns = movieRatingRecord.toString().split("\t");
        movieKey.set(movieRatingColumns[1]);
        context.write(movieKey, ratingFlag);
    }
}
