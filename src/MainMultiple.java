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
		}
		String filePathInput1 = args[0];
		String filePathInput2 = args[1];
		String filePathOutput = args[2];
		String trainingFilePath = "trainingFile.arff";
		
		Instances i1 = DataFileManager.readData(filePathInput1);
		//Classifier c1 = Strategy.getSVMClassifier();
		//DataFileManager.writeToFile(filePathOutput,Strategy.evaluate(c1, i1));

		
		Instances i2 = DataFileManager.readData(filePathInput2);
		//Classifier c2 = Strategy.getSVMClassifier();
		//DataFileManager.writeToFile(filePathOutput,Strategy.evaluate(c2, i2));
		
		Classifier multiClassifier = Strategy.merge2MainClassifierWithDefaultTrainingData();
		DataFileManager.writeToFile(filePathOutput,Strategy.evaluate(multiClassifier, Instances.mergeInstances(i1, i2)));
		
	}

}
