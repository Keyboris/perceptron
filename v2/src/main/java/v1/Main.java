package v1;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import com.opencsv.exceptions.CsvValidationException;

public class Main {
	public static void main(String[] args) throws IOException, CsvValidationException {
		
//      List<Flower> flowers = new ArrayList<>();
//		
//		try {
//          // load the file from the classpath
//          InputStream inputStream = Perceptron.class.getClassLoader().getResourceAsStream("iris_extended.csv");
//          if (inputStream == null) {
//              throw new IOException("File not found in classpath!");
//          }
//
//          // use InputStreamReader to read the file
//          InputStreamReader fileReader = new InputStreamReader(inputStream);
//          CSVReader csvReader = new CSVReader(fileReader);
//
//          String[] row = null;
//          int rowIndex = 0;
//
//          
//          while ((row = csvReader.readNext()) != null) {
//          	if ((rowIndex > 1 && rowIndex <= 201) || (rowIndex > 401 && rowIndex <= 601)) {
//          		flowers.add(new Flower(Float.parseFloat(row[3]), Float.parseFloat(row[4]), Float.parseFloat(row[5]), Float.parseFloat(row[6]), row[0]));
//          	}
//          	rowIndex++;
//          }
//
//          csvReader.close();
//      } catch (IOException e) {
//          e.printStackTrace();
//      }
		
		
		csvParser parser = new csvParser();
		
		List<Flower> bigger = parser.Parse(3,4,5,6,0, "iris_extended.csv");
		
		Collections.shuffle(bigger, new Random(3)); //this needs to be done for a better training, 3 was chosen at random
		
		

		
		List<Flower> smaller = parser.Parse(0, 1, 2, 3, 4, "iris.csv");
		
		Collections.shuffle(smaller, new Random(4));
		
//		try {
//          // load the file from the classpath
//          InputStream inputStream = Perceptron.class.getClassLoader().getResourceAsStream("iris.csv");
//          if (inputStream == null) {
//              throw new IOException("File not found in classpath!");
//          }
//
//          InputStreamReader fileReader = new InputStreamReader(inputStream);
//          CSVReader csvReader = new CSVReader(fileReader);
//
//          String[] row = null;
//          int rowIndex = 0;
//
//          
//          while ((row = csvReader.readNext()) != null) {
//          	if (rowIndex > 0 && rowIndex < 101) {
//          		flowers2.add(new Flower(Float.parseFloat(row[0]), Float.parseFloat(row[1]), Float.parseFloat(row[2]), Float.parseFloat(row[3]), row[4]));
//          	}
//          	rowIndex++;
//          }
//
//          csvReader.close();
//      } catch (IOException e) {
//          e.printStackTrace();
//      }
		
		

		
		
		Perceptron pSetosa = new Perceptron((float) 0.3, "setosa"); //pososesh ok?
		Perceptron pVersicolor = new Perceptron((float) 0.3, "versicolor");
		
		System.out.println("starting weights: ");
//		for (Float w: p.weights) {
//			System.out.println(w);
//		}
		
		//System.out.println("starting bias: " + p.bias);
		
		pSetosa.train(smaller, 1000);
		pVersicolor.train(smaller, 1000);
		
		int rightPredictions = 0;
		
		List<Flower> wrong = new ArrayList<>();
		
		
		for (Flower f: bigger) {
			String prediction;
			if (pSetosa.predict(f) == 1) {
				prediction = "setosa";
			}
			else {
				if (pVersicolor.predict(f) == 1) {
					prediction = "versicolor";
				}
				else {
					prediction = "virginica";
				}
			}
			
			System.out.println("prediction: " + prediction);
			System.out.println("actual name: " + f.getName());
			
			if (prediction.equals(f.getName())) {
				rightPredictions ++;
			}
			else {
				wrong.add(f);
			}
			
		}
		
//		for (Flower f : flowers1) {
//			rightPredictions += p.predict(f);
//		}
//		
		System.out.println("\npredicted right " + rightPredictions + " out of " + bigger.size());
		System.out.println("precentage: " + (float) rightPredictions/ (float) bigger.size()*100 + "%");
		
//		for (Flower f: wrong) {
//			System.out.println(f.getName());
//		}
//		
		
//		
//		System.out.println("final weights: ");
//		for (Float w: p.weights) {
//			System.out.println(w);
//		}
//		
//		System.out.println("final bias: " + p.bias);
		
		
	}
	
	
}
