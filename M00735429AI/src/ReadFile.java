
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import SupportVectorMachines.DataPoint;

/**
 * This file will read the data from the file.
 *
 */
public class ReadFile {
	// private DataPoint[] dataPoints;//points from the file
	private int numberOfPoints;// Number of total points in file
	private final String FILE_NOT_FOUND_MESSAGE = "File does not found by system";// display message if the file does //
																					// not found by system
	private File file; // file to read the data from

	public ReadFile(File fileName) {
		this.file = fileName;// file name
	}

	/**
	 * this method will read the inputs and category from the file
	 * 
	 * @return array of points
	 */
	public DataPoint[] getDataFromFile() {
		numberOfPoints = getTotalNumberOfPoints(file);// get total number of points from file
		DataPoint[] points = new DataPoint[numberOfPoints];
		int pointCounter = 0;// count the points
		int numberOfFeatures = 64;// number of inputs in the file
		String regex = ",";

		try {
			Scanner scanner = new Scanner(file);

			while (scanner.hasNext()) {
				String[] line = scanner.nextLine().split(regex);
				double[] features = new double[numberOfFeatures];
				// get the number of inputs which is 64 from file
				for (int inputIndex = 0; inputIndex < line.length - 1; inputIndex++) {
					features[inputIndex] = Integer.parseInt(line[inputIndex]);
				}
				// get category row
				int categoryAtIndex = line.length - 1;
				int category = Integer.parseInt(line[categoryAtIndex]);
				points[pointCounter] = new DataPoint(features, category);
				pointCounter++;
			}
		} catch (FileNotFoundException fileNotFoundException) {
			System.out.println(FILE_NOT_FOUND_MESSAGE);
			fileNotFoundException.printStackTrace();
		}

		return points;// return points

	}

	/**
	 * this method will read the total number of lines in the file
	 * 
	 * @param file file to read the data from
	 * @return counter of the lines
	 */
	private int getTotalNumberOfPoints(File file) {
		int counter = 0;
		try {

			Scanner scanner = new Scanner(file);
			while (scanner.hasNext()) {

				String line = scanner.nextLine();
				counter++;
			}

		} catch (FileNotFoundException fileNotFoundException) {
			System.out.println(FILE_NOT_FOUND_MESSAGE);
			fileNotFoundException.printStackTrace();
		}
		return counter;
	}

}
