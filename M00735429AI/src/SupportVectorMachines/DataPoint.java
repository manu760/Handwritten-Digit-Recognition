package SupportVectorMachines;

/**
 * This class contains the input features and classification 
 * for each input row.
 */
public class DataPoint {

	private double[] features;// array of inputs
	private double category;// classification

	// constructor with parameters features(inputs) and category
	public DataPoint(double[] features, double category) {
		this.features = features;
		this.category = category;
	}

	// get the features(inputs)
	public double[] getFeatures() {
		return features;
	}

	// set the features(inputs)
	public void setFeatures(double[] features) {
		this.features = features;
	}

	// get the category
	public double getCategory() {
		return category;
	}

	// set the category
	public void setCategory(double category) {
		this.category = category;
	}

	// get the distance between the features (inputs)
	public double getDistance(DataPoint point) {
		double sum = 0; // initialise sum equals to zero 
		//for loop going through all features array length
		for (int index = 0; index < features.length; index++) {
			//calculate the distance 
			double distance = Math.sqrt(Math.pow(features[index] - point.features[index], 2));
			//calculate sum 
			sum += distance;
		}
		return sum;
	}

	// print the output
	@Override
	public String toString() {
		String output = "";
		for (int inputNumber = 0; inputNumber < features.length; inputNumber++)
			output += features[inputNumber] + " ";
		output += category;

		return output;

	}

}
