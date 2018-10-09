import java.io.IOException;
import java.util.StringTokenizer;

import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapred.FileInputFormat;
import org.apache.hadoop.mapred.FileOutputFormat;
import org.apache.hadoop.mapred.JobClient;
import org.apache.hadoop.mapred.JobConf;
import org.apache.hadoop.mapred.MapReduceBase;
import org.apache.hadoop.mapred.Mapper;
import org.apache.hadoop.mapred.OutputCollector;
import org.apache.hadoop.mapred.Reducer;
import org.apache.hadoop.mapred.Reporter;
import org.apache.hadoop.mapred.TextInputFormat;
import org.apache.hadoop.mapred.TextOutputFormat;

public class Map2 extends MapReduceBase 
   implements Mapper<LongWritable, Text, Text, IntWritable>
{
       private final static IntWritable one = new IntWritable(1);
       private Text word = new Text();
   
       //public void map(LongWritable key, Text value, Context context)
       //      throws IOException, InterruptedException
       public void map(LongWritable key, 
             Text value, // whole file
                OutputCollector<Text, IntWritable> output, 
                Reporter reporter
              ) throws IOException
       {
          int count = 0;
           String line = value.toString();
           StringTokenizer tokenizer = new StringTokenizer(line);
           while (tokenizer.hasMoreTokens())
           {
              tokenizer.nextToken();
              count += Integer.parseInt( tokenizer.nextToken());
           }
           output.collect( new Text("all"), new IntWritable( count ) );

       }
}