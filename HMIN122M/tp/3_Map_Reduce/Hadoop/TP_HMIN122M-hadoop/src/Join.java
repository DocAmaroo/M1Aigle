import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.DoubleWritable;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.input.TextInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.mapreduce.lib.output.TextOutputFormat;

import java.io.IOException;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

import static java.lang.System.exit;

public class Join {

    private static final String INPUT_PATH = "input-join/";
    private static final String OUTPUT_PATH = "output/join-";
    private static final Logger LOG = Logger.getLogger(Join.class.getName());

    static {
        System.setProperty("java.util.logging.SimpleFormatter.format", "%5$s%n%6$s");

        try {
            FileHandler fh = new FileHandler("out.log");
            fh.setFormatter(new SimpleFormatter());
            LOG.addHandler(fh);
        } catch (SecurityException | IOException e) {
            exit(1);
        }
    }

    public static class Map extends Mapper<LongWritable, Text, Text, Text> {
        private final static String emptyWords[] = { "" };

        @Override
        public void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
            String line = value.toString();
            String[] words = line.split("\\|");

            if(Arrays.equals(words, emptyWords))
                return;

            if (words.length == 8) // on Customer
                context.write(new Text(words[0]), new Text("||"+words[1]));

            else // on Order
                //context.write(new Text(words[1]), new Text(words[8]));
                context.write(new Text(words[1]), new Text(words[3]));
        }
    }

    public static class Reduce extends Reducer<Text, Text, Text, DoubleWritable> {

        @Override
        public void reduce(Text key, Iterable<Text> values, Context context)
                throws IOException, InterruptedException {

            ArrayList<String> cust = new ArrayList<>();
            //ArrayList<String> comment = new ArrayList<>();
            DoubleWritable total = new DoubleWritable(0);

            for (Text val : values) {
                String line = val.toString();
                String[] words = line.split("\\|\\|");

                if (words.length == 2) cust.add(words[1]);
                else {
                    //comment.add(words[0]);
                    total.set(total.get() + Double.parseDouble(words[0]));
                }
            }

            for(String _cust : cust)
                //for (String _comment : comment)
                if(total.get() != 0)
                    context.write(new Text(_cust), total);
        }
    }

    public static void main(String[] args) throws Exception {
        Configuration conf = new Configuration();
        conf.set("fs.file.impl", "com.conga.services.hadoop.patch.HADOOP_7682.WinLocalFileSystem");

        Job job = new Job(conf, "Join");

        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(Text.class);

        job.setMapperClass(Join.Map.class);
        job.setReducerClass(Join.Reduce.class);

        job.setOutputValueClass(Text.class);

        job.setInputFormatClass(TextInputFormat.class);
        job.setOutputFormatClass(TextOutputFormat.class);

        FileInputFormat.addInputPath(job, new Path(INPUT_PATH));
        FileOutputFormat.setOutputPath(job, new Path(OUTPUT_PATH + Instant.now().getEpochSecond()));

        job.waitForCompletion(true);
    }
}
