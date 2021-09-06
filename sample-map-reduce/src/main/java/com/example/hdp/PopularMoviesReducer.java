package com.example.hdp;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class PopularMoviesReducer extends Reducer<IntWritable, Text, IntWritable, Text> {
    private static final Text movies = new Text();

    @Override
    public void reduce(IntWritable ratingsCount, Iterable<Text> movieKeys, Context context)
            throws IOException, InterruptedException {
        List<String> movieIds = new ArrayList<>();
        movieKeys.forEach(key -> movieIds.add(key.toString()));
        movies.set(String.join(",", movieIds).trim());
        context.write(ratingsCount, movies);
    }
}
