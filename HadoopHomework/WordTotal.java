// MR3.java
// Barrett Koster 2017 new Hadoop terms ... 

import java.io.IOException;
import java.util.Iterator;
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

public class WordTotal
{
    public static void main(String[] args) throws Exception
    {
       JobConf job1 = new JobConf( WordTotal.class );
       job1.setJobName("WordTotal");
       
       job1.setOutputKeyClass( Text.class );
       job1.setOutputValueClass( IntWritable.class);
      
       job1.setMapperClass( Map1.class );
       job1.setReducerClass( Reduce1.class );
       
       FileInputFormat.setInputPaths(job1, args[0]);
       FileOutputFormat.setOutputPath(job1, new Path("middle")); 
       
       //       job2.setInputFormat(TextInputFormat.class);
       // job2.setOutputFormat(TextOutputFormat.class);

       JobClient.runJob(job1);
     
       // ----------------------
       JobConf job2 = new JobConf( WordTotal.class );
       job2.setJobName("WordTotal");
       
       job2.setOutputKeyClass( Text.class );
       job2.setOutputValueClass( IntWritable.class);
      
       job2.setMapperClass( Map2.class );
       job2.setReducerClass( Reduce2.class );
       
       FileInputFormat.setInputPaths(job2, "middle");
       FileOutputFormat.setOutputPath(job2, new Path(args[1])); 
       
       //       job2.setInputFormat(TextInputFormat.class);
       // job2.setOutputFormat(TextOutputFormat.class);

       JobClient.runJob(job2);

       
       
       
       
       
       
       
       
       
       
    }
}