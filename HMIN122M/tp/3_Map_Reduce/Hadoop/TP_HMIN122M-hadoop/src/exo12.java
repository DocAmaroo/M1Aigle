import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.*;
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

// =========================================================================
// COMPARATEURS
// =========================================================================

/**
 * Comparateur qui inverse la méthode de comparaison d'un sous-type T de WritableComparable (ie. une clé).
 */
@SuppressWarnings("rawtypes")
class InverseComparator12<T extends WritableComparable> extends WritableComparator {

    public InverseComparator12(Class<T> parameterClass) {
        super(parameterClass, true);
    }

    /**
     * Cette fonction définit l'ordre de comparaison entre 2 objets de type T.
     * Dans notre cas nous voulons simplement inverser la valeur de retour de la méthode T.compareTo.
     *
     * @return 0 si a = b <br>
     *         x > 0 si a > b <br>
     *         x < 0 sinon
     */
    @SuppressWarnings("unchecked")
    @Override
    public int compare(WritableComparable a, WritableComparable b) {

        Double a1 = Double.parseDouble(a.toString());
        Double b1 = Double.parseDouble(b.toString());

        return -a1.compareTo(b1);
    }
}

/**
 * Inverseur de la comparaison du type Text.
 */
class TextInverseComparator12 extends InverseComparator10<Text> {

    public TextInverseComparator12() {
        super(Text.class);
    }
}
// =========================================================================
// CLASSE MAIN
// =========================================================================

public class exo12 {
    private static String INPUT_PATH = "input-tam/";
    private static String OUTPUT_PATH = "input-tmp/";
    private static final Logger LOG = Logger.getLogger(TriAvecComparaison.class.getName());

    static {
        System.setProperty("java.util.logging.SimpleFormatter.format", "%5$s%n%6$s");

        try {
            FileHandler fh = new FileHandler("out.log");
            fh.setFormatter(new SimpleFormatter());
            LOG.addHandler(fh);
        } catch (SecurityException | IOException e) {
            System.exit(1);
        }
    }


    // =========================================================================
    // MAPPER
    // =========================================================================

    public static class Map extends Mapper<LongWritable, Text, Text, Text> {
        private final static String emptyWords[] = { "" };

        public void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
            String line = value.toString();
            String[] words = line.split(";");

            if(Arrays.equals(words, emptyWords))
                return;
            try {
                String keys = words[3]; // stop_name

                if(!keys.equals("stop_name")){
                    if(Integer.parseInt(words[4]) < 5) // TRAM
                        context.write(new Text(keys), new Text("1"));
                }
            }catch (Exception e){}
        }
    }
    public static class Map1 extends Mapper<LongWritable, Text, Text, Text> {
        private final static String emptyWords[] = { "" };

        public void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
            String line = value.toString();
            String[] words = line.split(",");

            if(Arrays.equals(words, emptyWords))
                return;

            context.write(new Text(words[0]), new Text(","+words[1]));
        }
    }

    public static class Map2 extends Mapper<LongWritable, Text, Text, Text> {
        private final static String emptyWords[] = { "" };
        private int nb = 0;
        @Override
        public void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
            String line = value.toString();
            String[] words = line.split(",");

            if(Arrays.equals(words, emptyWords))
                return;

            if(nb<10)
                context.write(new Text(words[1]), new Text(words[0]));
            nb++;
        }
    }

    // =========================================================================
    // REDUCER
    // =========================================================================

    public static class Reduce extends Reducer<Text, Text, Text, Text> {

        public void reduce(Text key, Iterable<Text> values, Context context)
                throws IOException, InterruptedException {

            double sum = 0;

            for(Text t : values){
                sum += Double.parseDouble(t.toString());
            }

            context.write(new Text(sum+","),key);

        }
    }

    // =========================================================================
    // MAIN
    // =========================================================================

    public static void main(String[] args) throws Exception {
        Configuration conf = new Configuration();
        conf.set("fs.file.impl", "com.conga.services.hadoop.patch.HADOOP_7682.WinLocalFileSystem");

        Long tmp = Instant.now().getEpochSecond();

        Job job = new Job(conf, "12-reduc");

        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(Text.class);

        job.setMapperClass(Map.class);
        job.setReducerClass(Reduce.class);

        job.setInputFormatClass(TextInputFormat.class);
        job.setOutputFormatClass(TextOutputFormat.class);

        FileInputFormat.addInputPath(job, new Path(INPUT_PATH));
        FileOutputFormat.setOutputPath(job, new Path(OUTPUT_PATH + Instant.now().getEpochSecond()));

        job.waitForCompletion(true);

        //

        INPUT_PATH = OUTPUT_PATH + tmp;
        OUTPUT_PATH = "input-tmp/";

        Job job1 = new Job(conf, "12-Sort");

        job1.setSortComparatorClass(TextInverseComparator12.class);

        job1.setOutputKeyClass(Text.class);
        job1.setOutputValueClass(Text.class);

        job1.setMapperClass(Map1.class);

        job1.setInputFormatClass(TextInputFormat.class);
        job1.setOutputFormatClass(TextOutputFormat.class);

        FileInputFormat.addInputPath(job1, new Path(INPUT_PATH));
        FileOutputFormat.setOutputPath(job1, new Path(OUTPUT_PATH + tmp+"a"));

        job1.waitForCompletion(true);

        ///
        INPUT_PATH = OUTPUT_PATH + tmp +"a";
        OUTPUT_PATH = "output/12-TopTram-";
        Job job2 = new Job(conf, "12-topK");

        job2.setOutputKeyClass(Text.class);
        job2.setOutputValueClass(Text.class);

        job2.setMapperClass(Map2.class);

        job2.setInputFormatClass(TextInputFormat.class);
        job2.setOutputFormatClass(TextOutputFormat.class);

        FileInputFormat.addInputPath(job2, new Path(INPUT_PATH));
        FileOutputFormat.setOutputPath(job2, new Path(OUTPUT_PATH + Instant.now().getEpochSecond()));

        job2.waitForCompletion(true);
    }
}