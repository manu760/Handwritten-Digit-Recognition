import java.io.File;
import java.util.Random;

import SupportVectorMachines.DataPoint;
import SupportVectorMachines.DigitClassification;
import euclidDistance.NearestNeighbour;

/**
 * This file will run the algorithms implemented for this coursework
 * 1. Support Vector Machines
 * 2. Euclidean Distance 
 *    Enter the path to the file to run the algorithm.
 */
public class Main {
	
	//Read data from  training file 
	private static ReadFile training = new ReadFile(
			new File("C:\\Users\\ASUS\\eclipseProjects\\M00735429AI\\Resources\\dataset1.csv"));

	//Read data from testing file 
	private static ReadFile testing = new ReadFile(
			new File("C:\\Users\\ASUS\\eclipseProjects\\M00735429AI\\Resources\\dataset2.csv"));
	
	//get data from files 
	//fold 1
	private static final DataPoint[] TRAINING_DATA_POINTS_1 = training.getDataFromFile();
	private static final DataPoint[] TESTING_DATA_POINTS_1 = testing.getDataFromFile();
	//fold 2
	private final static DataPoint[] trainingDataSets = testing.getDataFromFile(); // training data
	private final static DataPoint[] testingDataSets = training.getDataFromFile(); // test data

	public static void main(String[] args) {
		
		runSupportVectorMachines(); //To run support vector machines
		//euclidDistance();			//To run euclidean Distance
	}
	
	//this method will run the euclidean distance algorithm
	private static void euclidDistance() {
		NearestNeighbour nearestNeighbour = new NearestNeighbour(TRAINING_DATA_POINTS_1,TESTING_DATA_POINTS_1);
		nearestNeighbour.run();//TO run euclidean distance algorithm
		System.out.println("--------------------------------------------------------------------------");
		System.out.println("                             EUCLIDEAN DISTANCE                           ");
		System.out.println("--------------------------------------------------------------------------");
		System.out.println(nearestNeighbour);//print results
	}

	//this method will run the support vector machines 
	private static void runSupportVectorMachines() {
		DataPoint[] trainRows = new DataPoint[2810];//take 2810 data Points
		System.arraycopy(TRAINING_DATA_POINTS_1, 0, trainRows, 0, trainRows.length); // copy training data points array
		DataPoint[] testRows = new DataPoint[2810];//take 2810 data Points
		System.arraycopy(TESTING_DATA_POINTS_1, 0, testRows, 0, testRows.length);//copy testing data points array
		System.out.println("--------------------------------------------------------------------------");
		System.out.println("                               FOLD 1                                     ");
		System.out.println("--------------------------------------------------------------------------");
		double firstFold = run(trainRows, testRows, 1);//run SVM 
		System.out.println("--------------------------------------------------------------------------");
		System.out.println("                               FOLD 2                                     ");
		System.out.println("--------------------------------------------------------------------------");
		double secondFold = run(testRows, trainRows, 2);//run SVM
		String result = "Average of fold tests: " + (firstFold + secondFold) / 2;//add both folds and divided by 2
		System.out.println(result);//print result
	}

	/**
	 * this method will get the results from support vector machines class
	 * @param trainingDataPoints		Training data sets 
	 * @param testingDataPoints			Testing data sets 
	 * @param foldsCounter				counter for fold test's
	 * @return							accuracy of algorithm
	 */
	private static double run(DataPoint[] trainingDataPoints, DataPoint[] testingDataPoints, int foldsCounter) {
		int numberOfClassifications = 10;// total number of classifications 0 - 9
		DigitClassification supportVectorMachines = new DigitClassification(trainingDataPoints, testingDataPoints, numberOfClassifications);//call SVM
		supportVectorMachines.supportVectorMachines();//Run SVM
		double accuracy = supportVectorMachines.getAccuracy();//Get accuracy on data sets 
		System.out.println(supportVectorMachines.toString());//Print results
		return accuracy;//return accuracy
	}
}
