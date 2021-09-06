package com.example.hdp;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.IOException;

public class PopularMoviesMapper extends Mapper<Object, Text, IntWritable, Text> {

    private static final IntWritable ratingKey = new IntWritable();
    private static final Text movieId = new Text();

    @Override
    protected void map(Object key, Text ratingCountRecord,
                       Context context) throws IOException, InterruptedException {
        String[] ratingCountRecordColumns = ratingCountRecord.toString().split("\t");
        ratingKey.set(Integer.parseInt(ratingCountRecordColumns[1]));
        movieId.set(ratingCountRecordColumns[0]);
        context.write(ratingKey, movieId);
    }
}
