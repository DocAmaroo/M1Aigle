
import java.io.IOException;
import java.time.Instant;
import java.util.*;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

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


/*
 * Jusqu'à présent nous avons défini nos mappers et reducers comme des classes internes à notre classe principale.
 * Dans des applications réelles de map-reduce cela ne sera généralement pas le cas, les classes seront probablement localisées dans d'autres fichiers.
 * Dans cet exemple, nous avons défini Map et Reduce en dehors de notre classe principale.
 * Il se pose alors le problème du passage du paramètre 'k' dans notre reducer, car il n'est en effet plus possible de déclarer un paramètre k dans notre classe principale qui serait partagé avec ses classes internes ; c'est la que la Configuration du Job entre en jeu.
 */

class InverseComparator11<T extends WritableComparable> extends WritableComparator {

	public InverseComparator11(Class<T> parameterClass) {
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
class TextInverseComparator11 extends InverseComparator11<Text> {

	public TextInverseComparator11() {
		super(Text.class);
	}
}

class Map1 extends Mapper<LongWritable, Text, Text, Text> {
	private final static String emptyWords[] = { "" };

	@Override
	public void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
		String line = value.toString();
		String[] words = line.split(",");

		if(Arrays.equals(words, emptyWords))
			return;

		try {
			String word = ","+words[0]; // row id
			String val = words[20]; // profit

			if(!val.equals("Profit"))
				context.write(new Text(val), new Text(word));
		}catch (Exception e){}
	}
}

class Reduce1 extends Reducer<Text, Text, Text, DoubleWritable> {

	public void reduce(Text key, Iterable<Text> values, Context context)
			throws IOException, InterruptedException {


	}
}
// =========================================================================
// MAPPER
// =========================================================================

class Map extends Mapper<LongWritable, Text, Text, Text> {
	private final static IntWritable one = new IntWritable(1);
	private final static String emptyWords[] = { "" };
	private int nb = 0;

	@Override
	public void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
		String line = value.toString();
		String[] words = line.split(",");

		try {
			if(nb<10)
				context.write(new Text(words[0]), new Text(words[1]));
			nb++;
		}catch (Exception e){}
	}
}

// =========================================================================
// REDUCER
// =========================================================================
class Reduce extends Reducer<Text, Text, Text, Text> {
	/**
	 * Map avec tri suivant l'ordre naturel de la clé (la clé représentant la fréquence d'un ou plusieurs mots).
	 * Utilisé pour conserver les k mots les plus fréquents.
	 *
	 * Il associe une fréquence à une liste de mots.
	 */
	private HashMap<String, String> map = new HashMap<>();
	private int nbsortedWords = 0;
	private int k;

	/**
	 * Méthode appelée avant le début de la phase reduce.
	 */
	@Override
	public void setup(Context context) {
		// On charge k
		k = context.getConfiguration().getInt("k", 1);
	}

	@Override
	public void reduce(Text key, Iterable<Text> values, Context context)
			throws IOException, InterruptedException {
		String t = "";
		for (Text val : values)
			t += val.toString();

		map.put(key.toString(),t);
		nbsortedWords++;
	}

	/**
	 * Méthode appelée à la fin de l'étape de reduce.
	 *
	 * Ici on envoie les mots dans la sortie, triés par ordre descendant.
	 */
	@Override
	public void cleanup(Context context) throws IOException, InterruptedException {
		TreeMap<String, String> sorted = new TreeMap<>();
		sorted.putAll(map);
		for(String key : sorted.descendingKeySet()){
			context.write(new Text(key),new Text(sorted.get(key)));
		}
	}

}

public class TopkWordCount {
	private static String INPUT_PATH = "input-groupBy/";
	private static String OUTPUT_PATH = "input-tmp/";
	private static final Logger LOG = Logger.getLogger(TopkWordCount.class.getName());

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

	/**
	 * Ce programme permet le passage d'une valeur k en argument de la ligne de commande.
	 */
	public static void main(String[] args) throws Exception {
		// Borne 'k' du topk
		int k = 10;

		try {
			// Passage du k en argument ?
			if (args.length > 0) {
				k = Integer.parseInt(args[0]);

				// On contraint k à valoir au moins 1
				if (k <= 0) {
					LOG.warning("k must be at least 1, " + k + " given");
					k = 1;
				}
			}
		} catch (NumberFormatException e) {
			LOG.severe("Error for the k argument: " + e.getMessage());
			System.exit(1);
		}

		Configuration conf = new Configuration();
		conf.set("fs.file.impl", "com.conga.services.hadoop.patch.HADOOP_7682.WinLocalFileSystem");
		conf.setInt("k", k);

		Long tmp = Instant.now().getEpochSecond();

		Job job = new Job(conf, "10-Sort");

		job.setSortComparatorClass(TextInverseComparator11.class);

		job.setOutputKeyClass(Text.class);
		job.setOutputValueClass(Text.class);

		job.setMapperClass(Map1.class);
		//job.setReducerClass(Reduce1.class);

		job.setInputFormatClass(TextInputFormat.class);
		job.setOutputFormatClass(TextOutputFormat.class);

		FileInputFormat.addInputPath(job, new Path(INPUT_PATH));
		FileOutputFormat.setOutputPath(job, new Path(OUTPUT_PATH + tmp));

		job.waitForCompletion(true);

		///
		INPUT_PATH = OUTPUT_PATH + tmp;
		OUTPUT_PATH = "output/TopK";

		Job job2 = new Job(conf, "wordcount");

		job2.setOutputKeyClass(Text.class);
		job2.setOutputValueClass(Text.class);

		job2.setMapperClass(Map.class);
		job2.setReducerClass(Reduce.class);

		job2.setInputFormatClass(TextInputFormat.class);
		job2.setOutputFormatClass(TextOutputFormat.class);

		FileInputFormat.addInputPath(job2, new Path(INPUT_PATH));
		FileOutputFormat.setOutputPath(job2, new Path(OUTPUT_PATH + Instant.now().getEpochSecond()));

		job2.waitForCompletion(true);
	}
}