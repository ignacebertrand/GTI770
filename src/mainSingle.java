import java.io.File;

import weka.classifiers.Classifier;
import weka.classifiers.trees.J48;
import weka.core.Instance;
import weka.core.Instances;
import weka.core.SerializationHelper;
import weka.filters.Filter;
import weka.filters.unsupervised.attribute.Remove;
import weka.filters.unsupervised.attribute.RemoveByName;
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
		File tmp = new File(filePathOutput);
		if (tmp.exists())
		{
			tmp.delete();
		}
		
		Classifier c = Strategy.getKNNClassifier("KNN_k_1_linear.model");

		//remove.
		Instances i = DataFileManager.readData(filePathInput);
		RemoveByName remove = new RemoveByName();
		remove.setExpression("(SAMPLEID|TRACKID)");
		remove.setInputFormat(i);
		i = Filter.useFilter(i, remove);
		Strategy s = new Strategy();
		do {
			
			DataFileManager.writeToFile(filePathOutput,s.evaluate(c, i));
		} while (!s.isDoneEvaluating);
		//DataFileManager.writeToFile(filePathOutput,Strategy.evaluate(c, i));
		

	}

}
