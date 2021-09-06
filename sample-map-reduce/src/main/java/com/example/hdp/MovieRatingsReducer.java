package com.example.hdp;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import java.io.IOException;

public class MovieRatingsReducer extends Reducer<Text, IntWritable, Text, IntWritable> {
    private static final IntWritable ratingsCount = new IntWritable();

    @Override
    public void reduce(Text movieKey, Iterable<IntWritable> ratingFlags, Context context)
            throws IOException, InterruptedException {
        int count = 0;
        for(IntWritable flag : ratingFlags) {
            count+=flag.get();
        }
        ratingsCount.set(count);
        context.write(movieKey, ratingsCount);
    }
}
