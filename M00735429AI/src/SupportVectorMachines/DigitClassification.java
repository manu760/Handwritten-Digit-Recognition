package SupportVectorMachines;
/**
 * 
 * This class contains supp2
 *
 */
public class DigitClassification extends Point{
    private double[][] inputs; 			//array of input sets
    private double[] classifications;	//array of classifications
    private int classificationCounter;	//number of classifications 0 - 9 

    /**
     * 
     * @param trainingRows		Training Data Points 
     * @param testingRows		Testing Data Points
     * @param classificationCounter		Classification counter
     */
    public DigitClassification(DataPoint[] trainingRows, DataPoint[] testingRows,int classificationCounter) {
        super(trainingRows, testingRows);
        this.classificationCounter = classificationCounter; //number of classifications 
        initialiseClassifications(trainingRows); 			// initialise the inputs
        initialiseInputs(trainingRows);          			// initialise the classifications
    }

    /**
     * To run support vector machines algorithm
     */
    public void supportVectorMachines(){
    	Perceptron[][] perceptron = getPerceptron(); // initialise the perceptrons
        double[][] weightsOfPerceptrons = getWeights(perceptron); //get the weights for each perceptron
        DataPoint[] testingRows = getTestingRows(); //get testing rows
        for(int testingRowIndex = 0; testingRowIndex < getTestingRows().length;testingRowIndex++){
            DataPoint testingDataPoint = testingRows[testingRowIndex];
            double[] inputDataPoints = extendedValueOfX(testingDataPoint.getFeatures()); //extend the value of x0 for testing data sets
            double[] extendedValueOfX = extendedValueOfX(inputDataPoints);//extend the value of x0 for inputs
            //check if testing data points are equal to actual classification points
            if((int)testingDataPoint.getCategory() == (int)getRealClassification(perceptron,extendedValueOfX))
                correctClassification++;//if testing data points are equal to actual data point then increment correct classification
            else
                incorrectClassification++;//else increment InCorrect classification
        }
    }

    /**
     * This method will return the actual classification of the data sets
     * @param perceptron			perceptrons	
     * @param extendedValueOfX		inputs
     * @return						category
     */
    private double getRealClassification(Perceptron[][] perceptron, double[] extendedValueOfX) {
        double category = 0; // initialise category to zero
        for(int index = 0;index < perceptron.length;index++){
            double[] weights = perceptron[index][0].getWeights(); //get weights for perceptron
            if(Perceptron.getHypothesisOfSum(extendedValueOfX,weights) ==  1) //check if hypothesis is 1
                category = index;
        }
        return category;//return category
    }

    /**
     * This method will return the weights of each perceptron.
     * @param perceptron		To calculate the weights
     * @return					weights
     */
    private double[][] getWeights(Perceptron[][] perceptron) {
        double[][] weights = new double[perceptron.length][];
        //Go through perceptron length and assign weights
        for(int index = 0; index < perceptron.length;index++){
            Perceptron perceptron1 = perceptron[index][0];
            weights[index] = perceptron1.getWeights(); //get weights fro perceptron
        }
        return weights;//return weights
    }

    /**
     * This method will initialise the perceptrons
     * @return perceptrons
     */
    private Perceptron[][] getPerceptron() {
        int linesPerClassification = 1;// number of lines per classification
        Perceptron[][] perceptrons = new Perceptron[classificationCounter][linesPerClassification];
        for(int classificationIndex = 0; classificationIndex < classificationCounter;classificationIndex++){
            perceptrons[classificationIndex] = getClassification(classificationIndex); //get classification 
        }
        return perceptrons; //return perceptron

    }

    /**
     * This method will return the perceptron with classification 
     * @param expectedClassification		desired classification
     * @return								perceptron
     */
    private Perceptron[] getClassification(double expectedClassification) {
        int linePerClassifcation = 1;// number of lines per classification
        double[] desiredClassification = getBinaryClassification(classifications,expectedClassification);//get binary classification of classification and desired classification
        Perceptron[] perceptrons = new Perceptron[linePerClassifcation];
        for(int lineIndex = 0; lineIndex < linePerClassifcation; lineIndex++) {
            Perceptron perceptron1 = new Perceptron(inputs, desiredClassification);
            perceptron1.run(); // run perceptron algorithm here
            perceptrons[lineIndex] = perceptron1;
        }

        return perceptrons;//return perceptrons
    }

    /**
     * This method will return the binary classification of actual classification and expected Classification
     * @param classifications				classifications array
     * @param expectedClassification		expected classification index
     * @return								binary classification
     */
    private double[] getBinaryClassification(double[] classifications, double expectedClassification) {
        double[] binaryCategory = new double[classifications.length];
        //Go through length of classifications
        for (int binaryCategoryIndex = 0; binaryCategoryIndex < binaryCategory.length; binaryCategoryIndex++) {
            if (classifications[binaryCategoryIndex] == expectedClassification) // if classifications index if equals to expected classification then the bianry classification is 1
                binaryCategory[binaryCategoryIndex] = 1;
            else
                binaryCategory[binaryCategoryIndex] = -1;//otherwise -1 
        }
        return binaryCategory;
    }


    /**
     * This method will initialise the classifications
     * @param trainingRows		training data points
     */
    private void initialiseInputs(DataPoint[] trainingRows) {
        this.inputs = new double[trainingRows.length][];
        int trainingIndex = 0;
        for(DataPoint trainingRow: trainingRows){
            double[] input = trainingRow.getFeatures();// get features from file
            double[] extendedValueOfXInput = extendedValueOfX(input);// extend the value of features 
            this.inputs[trainingIndex] = extendedValueOfXInput;
            trainingIndex++;
        }

    }

    /**
     * This method will add the 1 at X0 
     * @param input   inputs array
     * @return		  extended value of input array
     */
    private double[] extendedValueOfX(double[] input) {
        double[] extendedX = new double[input.length + 1];
        extendedX[0] = 1;//assign 1 to x0 in inputs 
        System.arraycopy(input,0,extendedX, 1,input.length);//copy array 
        return extendedX;//return inputs with x0 = 1
    }

    /**
     * This method will initialise the classifications
     * @param trainingRows		training data points
     */
    private void initialiseClassifications(DataPoint[] trainingRows) {
        this.classifications = new double[trainingRows.length];
        int trainingIndex = 0;
        for(DataPoint trainingRow: trainingRows){
            double classification = trainingRow.getCategory();//get classification from file 
            this.classifications[trainingIndex] = classification;
            trainingIndex++;
        }
    }


}

