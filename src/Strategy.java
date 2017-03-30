import weka.classifiers.Classifier;
import weka.classifiers.Evaluation;
import weka.classifiers.functions.LibSVM;
import weka.classifiers.lazy.IBk;
import weka.core.Instances;
import weka.core.SerializationHelper;

public class Strategy {
	
	public static void evaluateModel(Classifier model, Instances trainingData, Instances dataToClassify) throws Exception
	{
		Evaluation eval = new Evaluation(trainingData);
		eval.evaluateModel(model, dataToClassify);
		System.out.println(eval.toSummaryString("\nResults\n======\n", false));
	}
	
	
	public static Classifier getSVMClassifier() throws Exception {
		Classifier SVMClassifier  =  (LibSVM) SerializationHelper.read("SVM_C_5_10%.model"); //SVM_C_5_classifier.model
		return SVMClassifier;
	}
	
	public static Classifier getSVMClassifier2() throws Exception {
		Classifier SVMClassifier  =  (LibSVM) SerializationHelper.read("SVM_c_5_10%_diffData.model"); //SVM_C_5_classifier.model
		return SVMClassifier;
	}
	
	public static Classifier getKNNClassifier() throws Exception
	{	
		Classifier KNNClassifier  =  (IBk) SerializationHelper.read("KNN_k_1_10PercentDataWekaOld.model");
		return KNNClassifier;
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
		 for (int i = 0; i < data.numInstances(); i++)
		 {
			 returnVal += (int) model.classifyInstance(data.instance(i)) + System.lineSeparator(); 
		 }
		 
		 return returnVal;
	}
	
	/**
	 * Use a voting system to classify the instance. The result that was voted by the most instances will be taken.
	 * @param model
	 * @param data
	 * @return The decision taken by the evaluation for each instance
	 * @throws Exception
	 */
	public static String EvaluateWithVote(Classifier[] model, Instances[] data) throws Exception
	{
		String returnVal = "";
		int resultToFind;
		int countResult = 0;
		int bestCount = 0;
		int bestResult = 0;
		int[] results = new int[model.length];
		for (int i = 0; i < data[0].numInstances(); i++)
		 { 
		 
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
		 }
		return returnVal;
	}
	
}
