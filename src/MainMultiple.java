import java.util.HashMap;
import java.util.Map;

import weka.classifiers.Classifier;
import weka.classifiers.meta.Vote;
import weka.core.Instances;
import weka.core.SelectedTag;
import weka.core.Tag;
import weka.filters.Filter;
import weka.filters.unsupervised.attribute.RemoveByName;
import weka.gui.SelectedTagEditor;

public class MainMultiple {

	public static void main(String[] args) throws Exception {
		if (args.length!= 4)
		{
			System.out.println("usage: fichier-entrée1.arff fichier-entrée2.arff fichier-entrée3.arff fichier-sortie-single.txt ");
			System.exit(1);
		}
		String filePathInput1 = args[0];
		String filePathInput2 = args[1];
		String filePathInput3 = args[2];
		String filePathOutput = args[3];
		
		Instances i1 = DataFileManager.readData(filePathInput1); // TODO model SVM with c = 1 30% data
		Classifier c1 = Strategy.getSVMClassifier("SVM_c_1_linear_30%.model");

		Instances i2 = DataFileManager.readData(filePathInput2); // TODO model KNN with k = 1 30% data
		Classifier c2 = Strategy.getSVMClassifier("SVM_c_5_linear_30%.model");
		
		
		Instances i3 = DataFileManager.readData(filePathInput3); // TODO model KNN with k=5 30% data
		Classifier c3 = Strategy.getKNNClassifier("KNN_k_1_linear.model");
		
		
		if (i1.numInstances()!= i2.numInstances() && i1.numInstances() != i3.numInstances())
		{
			System.out.println("You need the same amount of instances on all files.");
			System.exit(1);
		}
		
		RemoveByName remove = new RemoveByName();
		remove.setExpression("(SAMPLEID|TRACKID)");
		remove.setInputFormat(i1);
		i1 = Filter.useFilter(i1, remove);
		remove.setInputFormat(i2);
		i2 = Filter.useFilter(i2, remove);
		remove.setInputFormat(i3);
		i3 = Filter.useFilter(i3, remove);
		
		Strategy s = new Strategy();
		
		do {
			
			DataFileManager.writeToFile(filePathOutput,s.EvaluateWithVote(new Classifier[]{c1,c2,c3},new Instances[]{i1,i2,i3}));
		} while (!s.isDoneEvaluating);
		//DataFileManager.writeToFile(filePathOutput,Strategy.EvaluateWithVote(new Classifier[]{c1,c2,c3},new Instances[]{i1,i2,i3}));
		
	}

}
