package SupportVectorMachines;
import java.util.Arrays;
import java.util.Random;

/**
 * 
 *This Class contains perceptron learning algorithm.To generate perceptrons for support vector machines.
 *The random weights has been assigned to the inputs to get the dot product which then finds the hypothesis 
 *of the dot product 
 *
 */
public class Perceptron {
	private static double[][] inputs;			// Array of Inputs
	private static double[] classifications;	// classifications array
	private static Random random = new Random();// Generate random numbers
	private static int features;				// Total Number of Features
	private double[] weights;					// Assign Random Weights Array
	private int[] errorCounter ;				//count the error
	private final int maximumError = 60;		//set maximum error to 60

	/**
	 * @param inputs          Total inputs x1,x2________________________x64
	 * @param classifications Number of classifications for each row of input
	 * @param randomSeed      Assign random same seed
	 *
	 */
	public Perceptron(double[][] inputs, double[] classifications, long randomSeed, int features) {
		this.classifications = classifications;
		this.features = features;
		this.inputs = extendedValueOfX(inputs, this.features + 1);//extended value of inputs
		this.random.setSeed(randomSeed);
		this.weights = getRandomWeights();//get random weights 

	}

	/**
	 * 
	 * @param inputs          Total number of inputs
	 * @param classifications Number of classification for each row
	 */
	public Perceptron(double[][] inputs, double[] classifications) {
		this.classifications = classifications;
		this.features = inputs[0].length;
		this.inputs = extendedValueOfX(inputs, this.features + 1);//extended value of inputs
		this.weights = getRandomWeights();//get random weights 
		int numberOfInputs = inputs.length;
		this.errorCounter = new int[numberOfInputs];

	}

	/**
	 * Empty Constructor
	 */
	public Perceptron() {

	}

	/**
	 * @param inputs          Total inputs x1,x2________________________x64
	 * @param classifications Number of classifications for each row of input
	 *
	 */
	public Perceptron(double[][] inputs, double[] classifications, int features) {
		this.features = features + 1; // Add on 1 to features
		this.inputs = extendedValueOfX(inputs, this.features + 1);//extended value of Inputs
		this.classifications = classifications;
		this.weights = getRandomWeights();//get random weights 

	}

	/**
	 * @return inputs array
	 */
	public double[][] getInputs() {
		return inputs;
	}

	/**
	 * @param inputs Total numbers in input array
	 */
	public void setInputs(double[][] inputs) {
		this.inputs = inputs;
	}

	/**
	 * @return weights to assign to the inputs
	 */
	public double[] getWeights() {
		return weights;
	}

	/**
	 * @param weights Weights for perceptron
	 */
	public void setWeights(double[] weights) {
		this.weights = weights;
	}

	/**
	 * To run the perceptron learning algorithm
	 */
	public void run() {
		weights = getRandomWeights();// assign random weights
		int[] misclassifiedExamples = forecast(inputs, classifications, weights);// get misclassifications from the
																					// input
		// run the loop until the length of misclassifications array becomes zero
		while (misclassifiedExamples.length != 0) {
			int misclassifiedIndex = pickOneFromMisclassifiedExamples(misclassifiedExamples);// pick index of random
																								// misclassification
			double[] xValue = inputs[misclassifiedIndex];// assign index to inputs
			double classification = classifications[misclassifiedIndex];// assign index to classifications
			weights = updatedWeights(classification, weights, xValue);// update the weights
			misclassifiedExamples = forecast(inputs, classifications, weights);// again try to find misclassifications
																				// from the input array
		}
	}

	/**
	 * This method will update the weights for inputs to get equation of staright
	 * line
	 * 
	 * @param classification actual classification
	 * @param weights        random weights
	 * @param xValue         value of x0
	 * @return updated weights for inputs
	 */
	private double[] updatedWeights(double classification, double[] weights, double[] xValue) {
		double[] updateWeights = new double[weights.length];
		if (classification == 1)//if classification is one
			updateWeights = addMatrix(weights, xValue);// add one matrix
		else
			updateWeights = subtractMatrix(weights, xValue);// delete one matrix
		return updateWeights;//return updated weights

	}

	/**
	 * This method returns the difference between given matrices
	 *
	 * @param matrix1 matrix1 array i.e [2,2]
	 * @param matrix2 matrix2 array i.e [4,5]
	 * @return difference of given matrices
	 */
	private double[] subtractMatrix(double[] matrix1, double[] matrix2) {
		double[] subtraction = new double[matrix1.length];
		//Go through both matrices length 
		for (int additionIndex = 0; additionIndex < matrix1.length && additionIndex < matrix2.length; additionIndex++) {
			subtraction[additionIndex] = matrix1[additionIndex] - matrix2[additionIndex]; //subtract matrices
		}
		return subtraction;//return difference
	}

	/**
	 * This method returns the sum of given matrices
	 *
	 * @param matrix1 matrix1 array i.e [2,2]
	 * @param matrix2 matrix2 array i.e [4,5]
	 * @return sum of given matrices
	 */
	private double[] addMatrix(double[] matrix1, double[] matrix2) {
		double[] addition = new double[matrix1.length];
		//Go through both matrices length
		for (int additionIndex = 0; additionIndex < matrix1.length && additionIndex < matrix2.length; additionIndex++) {
			addition[additionIndex] = matrix1[additionIndex] + matrix2[additionIndex];//Expression to add both matrices
		}
		return addition;//return addition results
	}

	/**
	 * This method returns the random index from misClassifiedExamples array
	 *
	 * @param misclassifiedExamples Array of misclassified Examples
	 * @return index of array
	 */
	private int pickOneFromMisclassifiedExamples(int[] misclassifiedExamples) {
		int randomNumber = random.nextInt(misclassifiedExamples.length);//generate random indices for misclassified Examples array
		return misclassifiedExamples[randomNumber];// return index of misclassified Examples

	}

	/**
	 * This method returns the array of misclassifications in the given input array
	 *
	 * @param inputs         inputs array to calculate the number of
	 *                       misclassifications i.e {{2,2},{3,3}}
	 * @param classification classifications array i.e [1,-1]
	 * @param weights        assign random weights
	 * @return 				 index of misclassifiedExamples array
	 */
	private int[] forecast(double[][] inputs, double[] classification, double[] weights) {
		int[] inputLength = new int[inputs.length];
		double[] hypothesis = getHypothesis(inputs, weights);//get hypothesis 
		int misclassifiedCounter = 0;//initialise nnumber of mis classified examples to zero
		for (int forecastIndex = 0; forecastIndex < inputLength.length; forecastIndex++) {//Going through all inputs
			//Check if the hypothesis predictions are not equal to classifications
			if (hypothesis[forecastIndex] != classification[forecastIndex]) {
				int error = errorCounter[forecastIndex]++;
				if(error == maximumError) {// if error is equals to maximum error
					//take it out from input
					double[][] newPoints = new double[inputs.length - 1][];
					System.arraycopy(inputs, 0, newPoints, 0, forecastIndex);//Copy inputs new array
					System.arraycopy(inputs,0,newPoints,forecastIndex,inputs.length - 1 - forecastIndex);
					
					//take it out from class
					double[] newClassifications = new double[newPoints.length];
					System.arraycopy(classification, 0, newClassifications, 0, forecastIndex);//Copy classifications new array
					System.arraycopy(classification,0,newClassifications,forecastIndex,inputs.length - 1 - forecastIndex);
					
					
					this.inputs = newPoints;
					this.classifications= newClassifications;
					misclassifiedCounter--;//decrement mis classified Counter
				}
				misclassifiedCounter++;//Increment mis classified Counter
			}
				
		}
		//get misclassified Indices
		int[] misclassifiedIndexes = getMisclassifiedExamplesIndices(misclassifiedCounter, inputs, hypothesis,
				classification);

		return misclassifiedIndexes;//return misclassified Indices
	}

	/**
	 * This method returns the index of misclassificatios array
	 *
	 * @param misclassifiedCounter counter of misclassifications in array
	 * @param inputs               inputs array to calculate the number of
	 *                             misclassifications i.e {{2,2},{3,3}}
	 * @param hypothesis           hypothesis of inputs
	 * @param classification       actual classification of features
	 * @return 					   indices of misclassification Array
	 */
	private int[] getMisclassifiedExamplesIndices(int misclassifiedCounter, double[][] inputs, double[] hypothesis,
			double[] classification) {
		int[] inputLength = new int[inputs.length];
		int[] misclassifiedIndexes = new int[misclassifiedCounter];
		for (int forecastIndex = 0, misclassifiedIndex = 0; forecastIndex < this.inputs.length && misclassifiedIndex < misclassifiedCounter; forecastIndex++) {
			//Check if the hypothesis predictions are not equal to classifications
			if (hypothesis[forecastIndex] != this.classifications[forecastIndex])			
				misclassifiedIndexes[misclassifiedIndex++] = forecastIndex;
		}
		return misclassifiedIndexes;//return index 
	}

	/**
	 * This method will get the hypothesis and initialise it
	 * 
	 * @param inputs 		 inputs array to give to calculate the hypothesis
	 * @param weights 		 random weights
	 * @return 				 initialise hypothesis array
	 */
	private double[] getHypothesis(double[][] inputs, double[] weights) {
		double[] hypothesis = new double[inputs.length];
		for (int indexOfPoint = 0; indexOfPoint < inputs.length; indexOfPoint++) {
			hypothesis[indexOfPoint] = getHypothesisOfSum(inputs[indexOfPoint], weights);// get hypothesis of inputs and weights
		}
		return hypothesis;
	}

	/**
	 * This method calculates the hypothesis of sum of dot product of given matrices
	 * and categories it into 2 categories 1 and -1
	 * 
	 * @param input   	inputs array
	 * @param weights 	random weights
	 * @return 			classification 1 and -1
	 */
	public static double getHypothesisOfSum(double[] input, double[] weights) {
		double bias = random.nextDouble();//create random bias 
		double hypothesis = getSumOfDotProduct(input, weights) + bias;//get sum of dot product 
		if (hypothesis >= 0)//if hypothesis is greater than equals to zero then return 1 otherwise - 1
			return 1;
		else
			return -1;
	}

	/**
	 * This method returns the sum of dot product of given matrices
	 *
	 * @param matrix1 	matrix1 array i.e [2,2]
	 * @param matrix2 	matrix2 array i.e [4,5]
	 * @return 			sum of dot product of the given matrices
	 */
	private static double getSumOfDotProduct(double[] matrix1, double[] matrix2) {
		double dotProductSum = 0;
		double matrix1Length = matrix1.length;
		double matrix2Length = matrix2.length;
		for (int xIndex = 0; xIndex < matrix1Length && xIndex < matrix2Length; xIndex++) {
			dotProductSum += (matrix1[xIndex] * matrix2[xIndex]);//calculate dot product of 2 matrices
		}
		return dotProductSum;// return sum of dot product
	}

	/**
	 * This method returns the random weights for the inputs
	 *
	 * @return random weights
	 */
	private double[] getRandomWeights() {
		int inputLength = inputs[0].length;
		double[] weights = new double[inputLength];
		for (int weightIndex = 0; weightIndex < weights.length; weightIndex++) {
			weights[weightIndex] = random.nextDouble();//generate random weights array
		}
		return weights;

	}

	/**
	 * Extends the value of inputs array to make x0 = 1
	 *
	 * @param inputs   	Input array to extend
	 * @param features 	total number of features
	 * @return 			extended value of input array results in making [1,2] array to
	 *         			[1,1,2]
	 */
	public static double[][] extendedValueOfX(double[][] inputs, int features) {
		double[][] extendedX = new double[inputs.length][features];
		for (int dataPointIndex = 0; dataPointIndex < extendedX.length; dataPointIndex++) {
			double[] currentDataPoint = inputs[dataPointIndex];
			extendedX[dataPointIndex][0] = 1;//set X0 to 1
			for (int indexOfFeature = 0; indexOfFeature < currentDataPoint.length; indexOfFeature++) {
				double feature = currentDataPoint[indexOfFeature];
				extendedX[dataPointIndex][indexOfFeature + 1] = feature;
			}
		}
		return extendedX;//return value of input array with 1 at the front of the row
	}

	/**
	 * This is a testing method to test equation of hyperplane
	 * @param weights 		Calculate the gradient and y-intercept from random weights
	 * @return 				equation of straight line
	 */
	public String printLineEquation(double[] weights) {
		// Testing method
		double w0 = weights[0];
		double w1 = weights[1];
		double w2 = weights[2];
		System.out.println("WEIGHTS: " + Arrays.toString(weights));
		//calculate gradient
		double gradient = -(w0 / w2) / (w0 / w1);
		//calculate bias 
		double yIntercept = -(w0) / (w2); 
		
		return "y = " + gradient + "x" + " + " + yIntercept;
	}

	//print results
	@Override
	public String toString() {
		String result = "";
		for (double w : weights)
			result += w + ", \t";
		return result + " \t" + printLineEquation(weights);
	}

}
