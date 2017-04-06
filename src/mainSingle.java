//import java.nio.file.DirectoryStream.Filter;

import weka.classifiers.Classifier;
//import weka.classifiers.trees.J48;

import weka.core.Instances;
import weka.core.converters.ConverterUtils.DataSink;
//import weka.core.SerializationHelper;
import weka.core.converters.ConverterUtils.DataSource;
import weka.filters.unsupervised.attribute.Remove;
import weka.filters.Filter;

import java.io.BufferedReader;
import java.io.FileReader;

//import weka.gui.Main;

public class mainSingle {

	public static void main(String[] args) throws Exception {
		if (args.length!= 2)
		{
			System.out.println("usage: fichier-entrée.arff fichier-sortie-single.txt ");
		}
		
		String filePathInput = args[0];
		String filePathOutput = args[1];
		//System.out.println("usage: " +filePathOutput);
		/*
		 * Classifier c = Strategy.getSVMClassifier();
		Instances ds = DataSource.read(filePathInput);
		removeData.setInputFormat(ds);
		
		Instances newData = Filter.useFilter(ds, removeData);
		System.out.println(newData);*/
		Classifier c = Strategy.getSVMClassifier();
		Instances i = DataFileManager.readData(filePathInput);
		
		//DataSink.write(filePathOutput, i);
		//System.out.println(i);
		DataFileManager.writeToFile(filePathOutput,Strategy.evaluate(c, i));
		

	}

}
