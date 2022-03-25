package euclidDistance;

import SupportVectorMachines.DataPoint;

/**
 * This class holds the euclidean distance Algorithm.Which goes through all the
 * training data points and get the nearest data point and compare the
 * classification with that data point.
 */

public class NearestNeighbour {
	private int correctClassification = 0; // counter of correct classification
	private int incorrectClassification = 0;// counter of incorrect classification
	private DataPoint[] trainingDataPoints; // number of training data points
	private DataPoint[] testingDataPoints; // number of testing data points

	/**
	 * 
	 * @param trainingDataPoints Training data Points
	 * @param testDataPoints     Testing data Points
	 */
	public NearestNeighbour(DataPoint[] trainingDataPoints, DataPoint[] testDataPoints) {
		this.trainingDataPoints = trainingDataPoints;
		this.testingDataPoints = testDataPoints;
	}

	// To run euclidean distance algorithm
	public void run() {
		DataPoint[] testRow = getTestingDataPoints(); // get the testing data points
		for (int index = 0; index < testRow.length; index++) {
			double classifyTestRow = classify(testRow[index]); // classify the data points
			double category = testRow[index].getCategory(); // get actual category from file
			if (category == classifyTestRow)//check if category is equals to classify row 
				addCorrect();
			else
				addIncorrect();
		}
	}

	/**
	 * This method will classify the categories
	 * 
	 * @param newRow new rows of data
	 * @return classifications
	 */
	private double classify(DataPoint newRow) {
		int shortestIndex = -1; //initialise shortest index 
		double shortestDistance = Double.MAX_VALUE; // set shortest distance to maximum value
		DataPoint[] trainingRows = getTrainingDataPoints();//get training data points 

		for (int trainingIndex = 0; trainingIndex < trainingRows.length; trainingIndex++) {
			DataPoint neighbour = trainingRows[trainingIndex];
			double distance = newRow.getDistance(neighbour);//get distance from new row to training data sets row
			if (distance < shortestDistance) {//if the distance is less than shortest Distance then swap 
				shortestIndex = trainingIndex; // shortest index with training index 
				shortestDistance = distance; // shortest distance with distance 
			}
		}

		return trainingRows[shortestIndex].getCategory();//return category of training rows 
	}

	//add correct classification
	public int addCorrect() {
		return correctClassification++;
	}

	// add incorrect classification
	public int addIncorrect() {
		return incorrectClassification++;
	}

	// get correct classification
	public int getCorrectClassification() {
		return correctClassification;
	}

	//set correct classification
	public void setCorrectClassification(int correctClassification) {
		this.correctClassification = correctClassification;
	}

	//get incorrect classifications
	public int getIncorrectClassification() {
		return incorrectClassification;
	}

	//set incorrect classifications
	public void setIncorrectClassification(int incorrectClassification) {
		this.incorrectClassification = incorrectClassification;
	}

	// get training data sets
	public DataPoint[] getTrainingDataPoints() {
		return trainingDataPoints;
	}

	// set trainign data sets
	public void setTrainingDataPoints(DataPoint[] trainingDataPoints) {
		this.trainingDataPoints = trainingDataPoints;
	}

	// get testing data sets
	public DataPoint[] getTestingDataPoints() {
		return testingDataPoints;
	}

	// set testing data sets
	public void setTestingDataPoints(DataPoint[] testingDataPoints) {
		this.testingDataPoints = testingDataPoints;
	}

	// print results
	@Override
	public String toString() {
		double correctCounter = ((double) correctClassification / testingDataPoints.length);//calculate correct counter
		double accuracy = correctCounter * 100.00;//calculate accuracy
		double errorRate = 100.00 - accuracy;//calculate error Rate
		return "Correct Classification: " + correctClassification + " Incorrect Classification: "
				+ incorrectClassification + " Accuracy: " + accuracy + "%" + " Error Rate: " + errorRate + "%";
	}
}
