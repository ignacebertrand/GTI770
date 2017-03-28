import weka.classifiers.Classifier;
import weka.classifiers.trees.J48;
import weka.core.Instance;
import weka.core.Instances;
import weka.core.SerializationHelper;
import weka.filters.unsupervised.instance.RemovePercentage;
import weka.gui.Main;

public class mainSingle {

	public static void main(String[] args) throws Exception {
		if (args.length!= 2)
		{
			System.out.println("usage: fichier-entrée.arff fichier-sortie-single.txt ");
		}
		String filePathInput = args[0];
		String filePathOutput = args[1];
		
		//Classifier bestModel  =  (J48) SerializationHelper.read(Main.class.getClass().getResourceAsStream("/Project/algoPerformantModel.model"));
		//Classifier worstModel  =  (NaiveBayes) SerializationHelper.read(Main.class.getClass().getResourceAsStream("/Project/pireAlgoModel.model"));
		
		// Just for quick early tests
		//RemovePercentage removeData = new RemovePercentage();
		//removeData.setOptions(new String[]{ "-P", "90" });
		
	//	Instances i = DataFileManager.readData(filePathInput);
	//	DataFileManager.writeToFile(filePathOutput,Strategy.classifyWithDefaultTrainingSet_SVM(i));
		//Classifier c = Strategy.getKNNClassifier();
		//DataFileManager.writeToFile(filePathOutput,Strategy.evaluate(c, i));
		

	}

}
