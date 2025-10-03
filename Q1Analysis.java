import java.io.IOException;
import java.util.*;
import java.util.regex.Pattern;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

public class Q1Analysis {

    // Common tokenizer pattern
    private static final Pattern TOKENIZER = Pattern.compile("[^a-zA-Z]+");
    
    public static class Q1Mapper extends Mapper<LongWritable, Text, Text, IntWritable> {
        private final static IntWritable one = new IntWritable(1);
        private Text word = new Text();
        private String part;

        @Override
        protected void setup(Context context) throws IOException, InterruptedException {
            part = context.getConfiguration().get("part");
        }

        public void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
            String line = value.toString().toLowerCase();
            String[] tokens = TOKENIZER.split(line);
            
            for (String token : tokens) {
                if (token.isEmpty()) continue;
                
                switch (part) {
                    case "A": // Word Count
                        word.set(token);
                        context.write(word, one);
                        break;
                        
                    case "B": // Target Words
                        if (token.equals("whale") || token.equals("sea") || token.equals("ship")) {
                            word.set(token);
                            context.write(word, one);
                        }
                        break;
                        
                    case "C": // Unique Words by Pattern
                        if (token.length() > 0) {
                            String keyPattern = token.length() + "," + token.charAt(0);
                            word.set(keyPattern);
                            context.write(word, one);
                        }
                        break;
                }
            }
        }
    }

    public static class Q1Reducer extends Reducer<Text, IntWritable, Text, IntWritable> {
        private IntWritable result = new IntWritable();

        public void reduce(Text key, Iterable<IntWritable> values, Context context) 
                throws IOException, InterruptedException {
            
            String part = context.getConfiguration().get("part");
            
            if ("C".equals(part)) {
                // For pattern matching, we only need to count unique words (each word appears once per key)
                result.set(1);
                context.write(key, result);
            } else {
                // For word count and target words, sum up the counts
                int sum = 0;
                for (IntWritable val : values) {
                    sum += val.get();
                }
                result.set(sum);
                context.write(key, result);
            }
        }
    }

    public static void main(String[] args) throws Exception {
        Configuration conf = new Configuration();
        
        if (args.length != 3) {
            System.err.println("Usage: Q1Analysis <in> <out> <part>");
            System.err.println("part: A (WordCount), B (TargetWords), or C (Pattern)");
            System.exit(2);
        }
        
        String part = args[2];
        conf.set("part", part);
        
        Job job = Job.getInstance(conf, "Q1 Analysis " + part);
        job.setJarByClass(Q1Analysis.class);
        job.setMapperClass(Q1Mapper.class);
        job.setCombinerClass(Q1Reducer.class);
        job.setReducerClass(Q1Reducer.class);
        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(IntWritable.class);
        
        FileInputFormat.addInputPath(job, new Path(args[0]));
        FileOutputFormat.setOutputPath(job, new Path(args[1]));
        
        System.exit(job.waitForCompletion(true) ? 0 : 1);
    }
}
