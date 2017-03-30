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
		
		Classifier c = Strategy.getSVMClassifier();
		Instances i = DataFileManager.readData(filePathInput);
		//Strategy.evaluateModel(c,trainindData,i);
		DataFileManager.writeToFile(filePathOutput,Strategy.evaluate(c, i));
		

	}

}
