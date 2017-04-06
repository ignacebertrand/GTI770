import java.io.File;
import java.io.FilterInputStream;
import java.util.List;

import weka.classifiers.Classifier;
import weka.classifiers.Evaluation;
import weka.classifiers.functions.LibSVM;
import weka.classifiers.lazy.IBk;
import weka.core.Instances;
import weka.core.SerializationHelper;
import weka.filters.unsupervised.attribute.RemoveByName;

public class Strategy {
	
	public boolean isDoneEvaluating;
	public int evaluateMethodIndex = 0;
	
	public static void evaluateModel(Classifier model, Instances trainingData, Instances dataToClassify) throws Exception
	{
		Evaluation eval = new Evaluation(trainingData);
		eval.evaluateModel(model, dataToClassify);
		System.out.println(eval.toSummaryString("\nResults\n======\n", false));
	}
	
	
	public static Classifier getSVMClassifier(String filepath) throws Exception {
		Classifier SVMClassifier  =  (LibSVM) SerializationHelper.read(filepath); //SVM_C_5_classifier.model
		return SVMClassifier;
	}
	
	public static Classifier getKNNClassifier(String filepath) throws Exception
	{	
		Classifier KNNClassifier = (IBk) SerializationHelper.read(filepath);
	
		/*List<String> filteredData = DataFileManager.readFile(filepath);
		
		StringBuilder tmp = new StringBuilder();
		 for (String s : filteredData)
		 {
			 tmp.append(s + System.lineSeparator());
		 }
		 DataFileManager.writeToFile(filepath + "tmp", tmp.toString());
		Classifier KNNClassifier  =  (IBk) SerializationHelper.read(filepath + "tmp");
		new File(filepath + "tmp").delete();*/
		return KNNClassifier;
	}
	
	public boolean getIsDoneEvaluating()
	{
		return isDoneEvaluating;
	}
	
	/**
	 * Evaluate the data based on the model passed as parameter.
	 * @param model
	 * @param data
	 * @return The decision taken by the evaluation for each instance
	 * @throws Exception
	 */
	public String evaluate(Classifier model, Instances data) throws Exception
	{
		isDoneEvaluating = false;
		String returnVal = "";
		boolean firsRound = true;
		 for (int i = evaluateMethodIndex; i < data.numInstances(); i++)
		 {
			 if (i%8000 == 0 && i!= 0 &&!firsRound)
			 {
				 isDoneEvaluating = false;
				 evaluateMethodIndex += 8000;
				 return returnVal;
			 }
			 firsRound = false;
			 returnVal += (int) model.classifyInstance(data.instance(i)) + System.lineSeparator(); 
			 if (i%8000 == 0 && i!= 0)
				 System.out.println(i+1 + "instances classified");
		 }
		 isDoneEvaluating = true;
		 evaluateMethodIndex = 0;
		 return returnVal;
	}
	
	/**
	 * Use a voting system to classify the instance. The result that was voted by the most instances will be taken.
	 * @param model
	 * @param data
	 * @return The decision taken by the evaluation for each instance
	 * @throws Exception
	 */
	public String EvaluateWithVote(Classifier[] model, Instances[] data) throws Exception
	{
		isDoneEvaluating = false;
		boolean firsRound = true;
		String returnVal = "";
		int resultToFind;
		int countResult = 0;
		int bestCount = 0;
		int bestResult = 0;
		int[] results = new int[model.length];
		for (int i = 0; i < data[0].numInstances(); i++)
		 { 
			if (i%8000 == 0 && i!=0 && !firsRound)
			 {
				 isDoneEvaluating = false;
				 evaluateMethodIndex += 8000;
				 return returnVal;
			 }
			firsRound = false;
		 
		for (int j = 0; j < model.length; j++)
		{
			results[j] = (int)model[j].classifyInstance(data[j].instance(i));
		}
		
		for (int k = 0; k < results.length; k++)
		{
			resultToFind = results[k];
			for (int l = 0; l < results.length; l++)
			{
				if (results[l] == resultToFind)
				{
					countResult++;
				}
				if (countResult > bestCount)
				{
					bestResult = resultToFind;
				}
			}
			
		}
		returnVal += bestResult + System.lineSeparator();
		bestResult = 0;
		countResult = 0;
		bestCount = 0;
		if (i%8000 == 0 && i!=0)
			 System.out.println(i+1 + "instances classified");
		 }
		 isDoneEvaluating = true;
		 evaluateMethodIndex = 0;
		return returnVal;
	}
	
}
