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

public class Q2Analysis {

    // Pattern to match words (letters only)
    private static final Pattern WORD_PATTERN = Pattern.compile("[a-zA-Z]+");
    
    public static class Q2Mapper extends Mapper<LongWritable, Text, Text, Text> {
        private Text word = new Text();
        private Text lineNumber = new Text();
        private String part;

        @Override
        protected void setup(Context context) throws IOException, InterruptedException {
            part = context.getConfiguration().get("part");
        }

        public void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
            String line = value.toString();
            
            switch (part) {
                case "A": // Inverted Index Construction
                    // Extract words from the line
                    java.util.regex.Matcher matcher = WORD_PATTERN.matcher(line.toLowerCase());
                    while (matcher.find()) {
                        String token = matcher.group();
                        if (!token.isEmpty()) {
                            word.set(token);
                            lineNumber.set(String.valueOf(key.get() + 1)); // Line numbers start from 1
                            context.write(word, lineNumber);
                        }
                    }
                    break;
                    
                case "B": // Maximum Occurrence Words
                    // Input format: word    line_numbers
                    String[] parts = line.split("\t");
                    if (parts.length == 2) {
                        word.set(parts[0]);
                        // Count occurrences by counting commas + 1
                        int count = parts[1].split(",").length;
                        lineNumber.set(String.valueOf(count));
                        context.write(word, lineNumber);
                    }
                    break;
            }
        }
    }

    public static class Q2Reducer extends Reducer<Text, Text, Text, Text> {
        private Text result = new Text();

        public void reduce(Text key, Iterable<Text> values, Context context) 
                throws IOException, InterruptedException {
            
            String part = context.getConfiguration().get("part");
            
            if ("A".equals(part)) {
                // Inverted Index: collect and sort line numbers
                Set<String> lineNumbers = new TreeSet<>();
                for (Text val : values) {
                    lineNumbers.add(val.toString());
                }
                
                StringBuilder sb = new StringBuilder();
                boolean first = true;
                for (String lineNum : lineNumbers) {
                    if (!first) {
                        sb.append(", ");
                    }
                    sb.append(lineNum);
                    first = false;
                }
                result.set(sb.toString());
                context.write(key, result);
                
            } else if ("B".equals(part)) {
                // Maximum Occurrence Words: just pass through the count
                for (Text val : values) {
                    result.set(val);
                    context.write(key, result);
                    break; // Should only be one value per key
                }
            }
        }
    }

    public static class Q2Combiner extends Reducer<Text, Text, Text, Text> {
        private Text result = new Text();

        @Override
        public void reduce(Text key, Iterable<Text> values, Context context) 
                throws IOException, InterruptedException {
            
            String part = context.getConfiguration().get("part");
            
            if ("B".equals(part)) {
                // For part B, combine by summing the counts
                int totalCount = 0;
                for (Text val : values) {
                    totalCount += Integer.parseInt(val.toString());
                }
                result.set(String.valueOf(totalCount));
                context.write(key, result);
            } else {
                // For part A, just pass through
                for (Text val : values) {
                    context.write(key, val);
                }
            }
        }
    }

    public static void main(String[] args) throws Exception {
        Configuration conf = new Configuration();
        
        if (args.length < 3) {
            System.err.println("Usage: Q2Analysis <in> <out> <part> [inputB]");
            System.err.println("part: A (InvertedIndex), B (MaxOccurrence)");
            System.exit(2);
        }
        
        String part = args[2];
        conf.set("part", part);
        
        Job job = Job.getInstance(conf, "Q2 Analysis " + part);
        job.setJarByClass(Q2Analysis.class);
        job.setMapperClass(Q2Mapper.class);
        
        if ("B".equals(part)) {
            job.setCombinerClass(Q2Combiner.class);
        }
        
        job.setReducerClass(Q2Reducer.class);
        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(Text.class);
        
        FileInputFormat.addInputPath(job, new Path(args[0]));
        FileOutputFormat.setOutputPath(job, new Path(args[1]));
        
        System.exit(job.waitForCompletion(true) ? 0 : 1);
    }
}
