package com.example.hdp;

import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapred.JobConf;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.LinkedHashMap;
import java.util.Map;

@SpringBootApplication
public class SampleMapReduceApplication {

	private static final String INPUT_PATH = "--input";
	private static final String OUTPUT_PATH = "--output";

	public static void main(String[] args) {
		SpringApplication.run(SampleMapReduceApplication.class, args);
	}

	@Bean
	public CommandLineRunner commandLineRunner() {
		return args -> {
			Map<String, String> argsMap = argsToMap(args);
			if(!argsMap.containsKey(INPUT_PATH)) {
				throw new IllegalArgumentException("--input arg is required");
			}
			if(!argsMap.containsKey(OUTPUT_PATH)) {
				throw new IllegalArgumentException("--output arg is required");
			}

			Path inputPath = new Path(argsMap.get(INPUT_PATH));
			Path job1OutputPath = new Path(argsMap.get(OUTPUT_PATH) + "/movie-ratings-job/" + System.currentTimeMillis());

			Job job1 = Job.getInstance(new JobConf(), "movie-ratings-job");
			job1.setJarByClass(SampleMapReduceApplication.class);
			job1.setMapperClass(MovieRatingsMapper.class);
			job1.setCombinerClass(MovieRatingsReducer.class);
			job1.setReducerClass(MovieRatingsReducer.class);
			job1.setOutputKeyClass(Text.class);
			job1.setOutputValueClass(IntWritable.class);

			FileInputFormat.addInputPath(job1, inputPath);
			FileOutputFormat.setOutputPath(job1, job1OutputPath);
			if(!job1.waitForCompletion(true)) {
				System.exit(1);
			}

			Path job2OutputPath = new Path(argsMap.get(OUTPUT_PATH) + "/popular-movie-job/" + System.currentTimeMillis());

			Job job2 = Job.getInstance(new JobConf(), "popular-movie-job");
			job2.setJarByClass(SampleMapReduceApplication.class);
			job2.setMapperClass(PopularMoviesMapper.class);
			job2.setCombinerClass(PopularMoviesReducer.class);
			job2.setReducerClass(PopularMoviesReducer.class);
			job2.setOutputKeyClass(IntWritable.class);
			job2.setOutputValueClass(Text.class);

			FileInputFormat.addInputPath(job2, new Path(job1OutputPath.toUri()+"/part-r-00000"));
			FileOutputFormat.setOutputPath(job2, job2OutputPath);
			job2.waitForCompletion(true);
			if(!job2.waitForCompletion(true)) {
				System.exit(1);
			}
			System.out.println(job2OutputPath.toUri()+"/part-r-00000");
			System.exit(0);
		};
	}

	private Map<String, String> argsToMap(String[] args) {
		if(args.length % 2 != 0) {
			throw new IllegalArgumentException("Invalid args size: "+args.length);
		}

		Map<String, String> argsMap = new LinkedHashMap<>();
		for(int i=0; i<args.length; i+=2) {
			argsMap.put(args[i], args[i+1]);
		}
		return argsMap;
	}
}
