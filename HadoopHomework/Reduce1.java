import java.io.IOException;
import java.util.Iterator;

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

public class Reduce1 extends MapReduceBase 
   implements Reducer<Text, IntWritable, Text, IntWritable> {

/*    public void reduce(
          Text key, 
          Iterable<IntWritable> values, 
          OutputCollector<Text,IntWritable> output,
          Reporter reporter
                      ) */
    public void reduce(Text key, Iterator<IntWritable> values,
          OutputCollector<Text, IntWritable> output, 
          Reporter reporter) 

      throws IOException  {
        int sum = 0;
        //for (IntWritable val : values) 
           while (values.hasNext())
        {
              IntWritable val = values.next();
            sum += val.get();
        }
        output.collect(key, new IntWritable(sum));
    }
 }
