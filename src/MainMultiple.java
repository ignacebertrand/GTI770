import java.util.HashMap;
import java.util.Map;

import weka.classifiers.Classifier;
import weka.classifiers.meta.Vote;
import weka.core.Instances;
import weka.core.SelectedTag;
import weka.core.Tag;
import weka.gui.SelectedTagEditor;

public class MainMultiple {

	public static void main(String[] args) throws Exception {
		if (args.length!= 3)
		{
			System.out.println("usage: fichier-entrée1.arff fichier-entrée2.arff fichier-sortie-single.txt ");
			System.exit(1);
		}
		String filePathInput1 = args[0];
		String filePathInput2 = args[1];
		String filePathOutput = args[2];
		
		Instances i1 = DataFileManager.readData(filePathInput1); // TODO model SVM with c = 1 30% data
		Classifier c1 = Strategy.getSVMClassifier();

		Instances i2 = DataFileManager.readData(filePathInput2); // TODO model KNN with k = 1 30% data
		Classifier c2 = Strategy.getKNNClassifier();
		
		
		Instances i3 = DataFileManager.readData(filePathInput2); // TODO model KNN with k=5 30% data
		Classifier c3 = Strategy.getSVMClassifier2();
		
		
		if (i1.numInstances()!= i2.numInstances() && i1.numInstances() != i3.numInstances())
		{
			System.out.println("You need the same amount of instances on all files.");
			System.exit(1);
		}
		
		DataFileManager.writeToFile(filePathOutput,Strategy.EvaluateWithVote(new Classifier[]{c1,/*c2,*/c3},new Instances[]{i1/*,i2*/,i3}));
		
	}

}
