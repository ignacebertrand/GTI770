import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;

import weka.core.Instances;

public class DataFileManager {
	
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
	
	public static void writeToFile(String filepath, String data) throws Exception
	{
	//	Writer writer = new BufferedWriter(new OutputStreamWriter(
	   //           new FileOutputStream(filepath), "utf-8")); 
		File file = new File(filepath);
		if (!file.exists())		
			file.createNewFile();
		
		Files.write(Paths.get(filepath), data.getBytes(), StandardOpenOption.APPEND);	
		
		
	//   writer.write(data);
	 //  writer.close();
		
	}

}
