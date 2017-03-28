import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.print.attribute.standard.Destination;

import weka.classifiers.Classifier;
import weka.classifiers.bayes.NaiveBayes;
import weka.classifiers.trees.J48;
import weka.core.Instance;
import weka.core.Instances;
import weka.core.SerializationHelper;

import weka.core.Instances;
import weka.core.converters.ConverterUtils.DataSink;
import weka.core.converters.ConverterUtils.DataSource;
import weka.filters.Filter;
import weka.filters.unsupervised.instance.RemovePercentage;

public class Sample {

	public static void main(String[] args) throws Exception{
		// TODO Auto-generated method stub
		String inFolder = "../Data/";
		String outFolder = "../Sample/";
		// Initialize the filter
		RemovePercentage removeData = new RemovePercentage();
		removeData.setOptions(new String[]{ "-P", "90" });
				
		for (final File fileEntry : new File(inFolder).listFiles()) {
			if (fileEntry.getName().endsWith(".arff")) {
			    String filename = fileEntry.getPath();
			    Instances ds = DataSource.read(filename);
			    removeData.setInputFormat(ds);
			            
			    Instances newData = Filter.useFilter(ds, removeData);
			            
			    DataSink.write(outFolder + fileEntry.getName(), newData);
			}
		}
	}
	
	private static void cleanData(String inFolder, String outFolder) {
		// TODO Auto-generated method stub
		Writer writer = new BufferedWriter(new OutputStreamWriter(
	              new FileOutputStream(outFolder), "utf-8")); 
		
	   
		BufferedReader reader = new BufferedReader(new FileReader(inFolder));
		List<String> lines = new ArrayList<>();
		String line;
		int progressIndex = 0;
		int lineCount = 0;
	       while ((line = reader.readLine()) != null) { 
	         if (line.startsWith("@"))
	         {
	        	 if (line.contains("@attribute SAMPLEID") || line.contains("@attribute TRACKID"))
	        	 {
	        		 continue;	 
	        	 }
	         }
	         /*
	          * do something
	          * */
	       }
	       reader.close();
	       writer.close();
	       
	    
	}

	public static List<String> readFile(String path) throws IOException
	{
		BufferedReader reader = new BufferedReader(new FileReader(path));
		List<String> lines = new ArrayList<>();
		String line;
		int progressIndex = 0;
		int lineCount = 0;
	       while ((line = reader.readLine()) != null) { 
	         if (line.startsWith("@"))
	         {
	        	 if (line.contains("@attribute SAMPLEID") || line.contains("@attribute TRACKID"))
	        	 {
	        		 continue;	 
	        	 }
	         }
	         if (line.contains(","))
	         {
	        	int startOfFilteredLine =  line.indexOf(",");
	        	startOfFilteredLine = line.indexOf(",", startOfFilteredLine+1);
	        	line = line.substring(startOfFilteredLine + 1);
	        	
	         }
	         lineCount++;
	         progressIndex++;
	         if (progressIndex == 200)
	         {
	        	 progressIndex = 0;
	        	 System.out.println("Processed " + lineCount + " lines");
	         }
	         
	         lines.add(line);
	       }
	       return lines;
	       
	}
	
	public static Instances readData(String path) throws Exception
	{
		
		BufferedReader reader = new BufferedReader(new FileReader(path));
		
		Instances instance = new Instances(reader);

	     
		
		 if (instance.classIndex() == -1)
		 {
			 instance.setClassIndex(instance.numAttributes() - 1); 
		 }
		   
		 return instance;
	}
	
	/**
	 * Evaluate the data based on the model passed as parameter.
	 * @param model
	 * @param data
	 * @return The decision taken by the evaluation for each instance
	 * @throws Exception
	 */
	public static String evaluate(Classifier model, Instances data) throws Exception
	{
		String returnVal = "";
		 Iterator<Instance> it = data.iterator();
		 while (it.hasNext())
		 {
			returnVal += (int) model.classifyInstance(it.next()) + System.lineSeparator();
		 }	
		 
		 return returnVal;
	}
	public static void writeToFile(String filepath, String data) throws Exception
	{
		Writer writer = new BufferedWriter(new OutputStreamWriter(
	              new FileOutputStream(filepath), "utf-8")); 
		
	   writer.write(data);
	   writer.close();
		
	}
	

}
