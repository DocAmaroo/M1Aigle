import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.io.WritableComparable;
import org.apache.hadoop.io.WritableComparator;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.input.TextInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.mapreduce.lib.output.TextOutputFormat;

import java.io.IOException;
import java.time.Instant;
import java.util.Arrays;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

import static java.lang.System.exit;

public class requete2 {
    private static String INPUT_PATH = "input/";
    private static String OUTPUT_PATH = "input-tmp/req2Join-";
    private static final Logger LOG = Logger.getLogger(requete1.class.getName());

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
            String[] words = line.split(",");

            if(Arrays.equals(words, emptyWords))
                return;

            // call du Order
            if(words.length == 3 && words[2].equals("[X]")){
                context.write(new Text(words[1]), new Text(words[0]));
            }
            // on Inscription -> idDate,idPromotion,idEvent,idJoueur,PrixInscription,Gain
            else if (words.length == 6){
                if(!words[0].equals("idDate"))
                    context.write(new Text(words[0]), new Text("|"+words[4]+":"+words[5]));
            }
            // on Date -> idDate,Horodatage
            else if (words.length == 2){
                if(!words[0].equals("idDate")){
                    String[] split = words[1].split("/");
                    context.write(new Text(words[0]), new Text("|"+split[1]+"/"+split[2]));
                }
            }
        }
    }

    public static class Reduce extends Reducer<Text, Text, Text, Text> {

        @Override
        public void reduce(Text key, Iterable<Text> values, Context context)
                throws IOException, InterruptedException {
            String nkey = "";
            double inscrip = 0; double gain = 0;
            for (Text val : values) {
                String line = val.toString();
                String[] words = line.split("\\|");
                for (String s : words){
                    String[] tmp = s.split("/");
                    if(tmp.length > 1){
                        nkey = s;
                    }else{
                        String[] data = s.split(":");
                        if(data.length > 1){
                            inscrip += Double.parseDouble(data[0]);
                            gain += Double.parseDouble(data[1]);
                        }
                    }
                }
            }
            double res = gain - inscrip;
            context.write(new Text(nkey), new Text(","+res+",[X]"));
        }
    }

    public static void main(String[] args) throws Exception {
        Configuration conf = new Configuration();
        conf.set("fs.file.impl", "com.conga.services.hadoop.patch.HADOOP_7682.WinLocalFileSystem");

        //##################################### JOIN #####################################//
        Job job = new Job(conf, "Join");
        long tmp = Instant.now().getEpochSecond();

        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(Text.class);

        job.setMapperClass(Map.class);
        job.setReducerClass(Reduce.class);

        job.setOutputValueClass(Text.class);

        job.setInputFormatClass(TextInputFormat.class);
        job.setOutputFormatClass(TextOutputFormat.class);

        FileInputFormat.addInputPath(job, new Path(INPUT_PATH));
        FileOutputFormat.setOutputPath(job, new Path(OUTPUT_PATH + tmp));

        job.waitForCompletion(true);

        //##################################### SORT #####################################/
        INPUT_PATH = OUTPUT_PATH + tmp;
        OUTPUT_PATH = "output/req2-";
        Job job2 = new Job(conf, "Sort");

        job2.setSortComparatorClass(TextComparator1.class);

        job2.setOutputKeyClass(Text.class);
        job2.setOutputValueClass(Text.class);

        job2.setMapperClass(Map.class);

        job2.setInputFormatClass(TextInputFormat.class);
        job2.setOutputFormatClass(TextOutputFormat.class);

        FileInputFormat.addInputPath(job2, new Path(INPUT_PATH));
        FileOutputFormat.setOutputPath(job2, new Path(OUTPUT_PATH + tmp));

        job2.waitForCompletion(true);
    }
}