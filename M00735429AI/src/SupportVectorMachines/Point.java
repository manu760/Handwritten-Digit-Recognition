package SupportVectorMachines;
/**
 * 
 * This class contains the training rows and testing 
 * rows of data and it calculates the accuracy and 
 * error Rate of training and testing sets and print 
 * the results.
 *
 */
public class Point {
    private static DataPoint[] trainingRows;//training Rows Array
    private static DataPoint[] testingRows;//testing Rows Array
    public  int correctClassification = 0;// count correct classifications
    public  int incorrectClassification = 0;//count incorrect classifications

    /**
     * 
     * @param trainingRows		training Rows Array
     * @param testingRows		testing Rows Array
     */
    public Point(DataPoint[] trainingRows,DataPoint[] testingRows){
        this.trainingRows = trainingRows;
        this.testingRows = testingRows;
    }
    
    //get incorrect classifications
    public int getIncorrectClassification() {
        return incorrectClassification;
    }

    //set incorrect classifications
    public void setIncorrectClassification(int incorrectClassification) {
        this.incorrectClassification = incorrectClassification;
    }

    //get correct classifications
    public int getCorrectClassification() {
        return correctClassification;
    }

    //set correct classifications
    public void setCorrectClassification(int correctClassification) {
        this.correctClassification = correctClassification;
    }

    //get testing rows
    public DataPoint[] getTestingRows() {
        return testingRows;
    }

    //set testing rows
    public void setTestingRows(DataPoint[] testingRows) {
        this.testingRows = testingRows;
    }

    //get training rows
    public DataPoint[] getTrainingRows() {
        return trainingRows;
    }

    //set training rows
    public void setTrainingRows(DataPoint[] trainingRows) {
        this.trainingRows = trainingRows;
    }

    //calculate accuracy of algorithm
    public double getAccuracy() {
        return ((double)correctClassification / testingRows.length) * 100.0;
    }


    //print the results
    @Override
    public String toString(){
        double accuracy = getAccuracy();
        double errorRate = 100.0 - accuracy;

        return "ALGORITHM NAME: \t\t" + "SUPPORT VECTOR MACHINES" + "\nCORRECT CLASSIFICATIONS: \t" + correctClassification + "\nINCORRECT CLASSIFICATIONS: \t" + incorrectClassification + "\nACCURACY: \t\t\t" + accuracy + "%\nERROR RATE: \t\t\t" + errorRate;

    }
}
