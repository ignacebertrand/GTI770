import java.util.Iterator;

import weka.classifiers.Classifier;
import weka.classifiers.Evaluation;
import weka.classifiers.bayes.NaiveBayes;
import weka.classifiers.functions.LibSVM;
import weka.classifiers.lazy.IBk;
import weka.classifiers.meta.Vote;
import weka.classifiers.trees.J48;
import weka.core.Attribute;
import weka.core.Instance;
import weka.core.Instances;
import weka.core.SelectedTag;
import weka.core.SerializationHelper;
import weka.core.Tag;
import weka.gui.Main;

public class Strategy {
	/*public static Classifier strategyKNN(Instances... set) throws Exception {
		Classifier[] classifiers = new Classifier[set.length];
		
		for (int i=0; i<set.length; i++) {
			Instances inst = set[i];
			IBk ibk = new IBk();
			ibk.setOptions(new String[]{ "-K", "3", "-W", "0", "-A", "weka.core.neighboursearch.LinearNNSearch -A \"weka.core.EuclideanDistance -R first-last\"" });
			
			Classifier classifier = new AttributeClassifier(ibk);
			classifier.buildClassifier(inst);
			classifiers[i] = classifier;
		}
		
		Vote vote = new Vote();
		vote.setClassifiers(classifiers);
		return vote;
	}*/
	
	/*public static Classifier strategyBayes(Instances... set) throws Exception {
		Classifier[] classifiers = new Classifier[set.length];
		
		for (int i=0; i<set.length; i++) {
			Instances inst = set[i];
			NaiveBayes bayes = new NaiveBayes();
			bayes.setOptions(new String[]{ "-D" });
			
			Classifier classifier = new AttributeClassifier(bayes);
			classifier.buildClassifier(inst);
			classifiers[i] = classifier;
		}
		
		Vote vote = new Vote();
		vote.setClassifiers(classifiers);
		return vote;
	}*/
	
	public static void evaluateModel(Classifier model, Instances trainingData, Instances dataToClassify) throws Exception
	{
		Evaluation eval = new Evaluation(trainingData);
		eval.evaluateModel(model, dataToClassify);
		System.out.println(eval.toSummaryString("\nResults\n======\n", false));
	}
	public static Classifier merge2MainClassifierWithDefaultTrainingData() throws Exception
	{
		
		Instances trainingData = DataFileManager.readData("trainingData.arff");
		
		// build ibk classifier
		IBk ibk = new IBk();
		ibk.setOptions(new String[]{ "-K", "1"});
		//ibk.buildClassifier(trainingData);
		
		//build svm classifier
		LibSVM svm = new LibSVM();
		svm.setOptions(new String[]{ "-C", "5", "-K", "0"});
		//svm.buildClassifier(trainingData);
		
		Vote v = new Vote();
		Classifier[] classifiers = new Classifier[2];
		classifiers[1] = ibk;
		classifiers[2] = svm;
		v.setClassifiers(classifiers);
		Tag[] tag = new Tag[1];
		Tag tmp = new Tag();
		tmp.setID(0);
		tmp.setIDStr("Avg");
		tag[0] = tmp;
		
	
		SelectedTag ste = new SelectedTag(0, tag);
		v.setCombinationRule(ste);
		v.buildClassifier(trainingData); // Don't know if good or not
		//DataFileManager.writeToFile(filePathOutput,Strategy.evaluate(v, Instances.mergeInstances(i1, i2)));
		return v;
		
	}
	
	public static String classifyWithDefaultTrainingSet_KNN(Instances dataToClassify) throws Exception
	{
		Instances trainingData = DataFileManager.readData("trainingData.arff");
		IBk ibk = new IBk();
		ibk.setOptions(new String[]{ "-K", "1"});
		ibk.buildClassifier(trainingData);
		
		String returnVal = "";
		 for (int i = 0; i < dataToClassify.numInstances(); i++)
		 {
			returnVal += (int) ibk.classifyInstance(dataToClassify.instance(i)) + System.lineSeparator(); 		 
		 }
		 
		 return returnVal;
	}
	
	public static String classifyWithDefaultTrainingSet_SVM(Instances dataToClassify) throws Exception
	{
		Instances trainingData = DataFileManager.readData("trainingData.arff");
		LibSVM svm = new LibSVM();
		svm.setOptions(new String[]{ "-C", "5", "-K", "0"});
		svm.buildClassifier(trainingData);
		//evaluateModel(svm, trainingData, dataToClassify);
		
		String returnVal = "";
		 for (int i = 0; i < dataToClassify.numInstances(); i++)
		 {
			 returnVal += (int) svm.classifyInstance(dataToClassify.instance(i)) + System.lineSeparator(); 
		 } 
		 
		 return returnVal;
	}
	
	public static Classifier getSVMClassifier() throws Exception {
		Classifier SVMClassifier  =  (LibSVM) SerializationHelper.read("SVM_C_5_10PercentData.model"); //SVM_C_5_classifier.model
		return SVMClassifier;
	}
	
	public static Classifier getKNNClassifier() throws Exception
	{
		
		Classifier KNNClassifier  =  (IBk) SerializationHelper.read("KNN_k_1_10PercentDataWekaOld.model");
		//Classifier KNNClassifier  =  (IBk) SerializationHelper.read(Main.class.getClass().getResourceAsStream("/Project/KNN_k_1_classifier.model"));
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
	 * Evaluate the data based on the model passed as parameter.
	 * @param model
	 * @param data
	 * @return The decision taken by the evaluation for each instance
	 * @throws Exception
	 */
	public static String evaluate(Vote v, Instances data) throws Exception
	{
		String returnVal = "";
		 for (int i = 0; i < data.numInstances(); i++)
		 {
			 returnVal += (int) v.classifyInstance(data.instance(i)) + System.lineSeparator(); 
		 }
		 
		 return returnVal;
	}
}
