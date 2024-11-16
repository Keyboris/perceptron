package v1;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.io.InputStreamReader;
import java.io.IOException;
import java.io.InputStream;
import java.math.*;

public class Perceptron {
	
	private float bias;
	private float[] weights = {0, 0, 0, 0};
	private float learningRate;
	
	
	public Perceptron(float learningRate) {
		this.learningRate = learningRate;
		Random rand = new Random();
		for (int i = 0; i < 4; i++) {
			weights[i] = rand.nextFloat() * 2 - 1;  //i just found 8 to be suitable enough for a good enough range
		}
		bias = rand.nextFloat() * 2 - 1;  //same here
	}
	
	private void train(List<Flower> flowers, int epochs) { 
		//here we will assume that setosa is 1 and versicolor is 0
		for (int n = 0; n < epochs; n++) {
			for (int i = 0; i < flowers.size(); i++) {
				float tempResult = 0;
				for (int j = 0; j < weights.length; j++) {
					tempResult += weights[j] * flowers.get(i).getParameter(j);
				}
				int guess = activationFunction(tempResult - bias);
				adjustBias(flowers, guess, i);
				for (int j = 0; j < weights.length; j++) {
					adjustWeight(flowers, j, i, guess);
				}
			}
		}
	}
	
	private int activationFunction(float result) { //can be changed for whatever
		return result > 0 ? 1 : 0;  //if the result gives us 0 the function should not fire
	}
	
	private void adjustBias(List<Flower> flowers ,int guess, int index) {
		int actualValue;
		if (flowers.get(index).getName().equals("setosa")) {
			actualValue = 1;
		}
		else {
			actualValue = 0;
		}
		
		bias = bias + learningRate * (actualValue - guess);
	}
	
	private void adjustWeight(List<Flower> flowers, int weightIndex, int flowerIndex, int guess) {
		int actualValue;
		if (flowers.get(flowerIndex).getName().equals("setosa")) {
			actualValue = 1;
		}
		else {
			actualValue = 0;
		}
		
		weights[weightIndex] = weights[weightIndex] + learningRate * (actualValue - guess) * flowers.get(flowerIndex).getParameter(weightIndex);
		
	}
	
	private int predict(Flower flower) {
		float tempResult = 0;
		for (int j = 0; j < weights.length; j++) {
			tempResult += weights[j] * flower.getParameter(j);
		}
		int guess = activationFunction(tempResult - bias);
		String name;
		if (guess == 1) {
			name = "setosa";
		}
		else {
			name = "versicolor";
		}
		
		System.out.println("guess: " + name);
		System.out.println("actual name: " + flower.getName());
		
		if (name.equals(flower.getName())) {
			return 1;
		}
		else {
			return 0;
		}
	}
	
	
	
	public static void main(String[] args) throws IOException, CsvValidationException {
		
        List<Flower> flowers = new ArrayList<>();
		
		try {
            // load the file from the classpath
            InputStream inputStream = Perceptron.class.getClassLoader().getResourceAsStream("iris_extended.csv");
            if (inputStream == null) {
                throw new IOException("File not found in classpath!");
            }

            // use InputStreamReader to read the file
            InputStreamReader fileReader = new InputStreamReader(inputStream);
            CSVReader csvReader = new CSVReader(fileReader);

            String[] row = null;
            int rowIndex = 0;

            
            while ((row = csvReader.readNext()) != null) {
            	if ((rowIndex > 1 && rowIndex <= 201) || (rowIndex > 401 && rowIndex <= 601)) {
            		flowers.add(new Flower(Float.parseFloat(row[3]), Float.parseFloat(row[4]), Float.parseFloat(row[5]), Float.parseFloat(row[6]), row[0]));
            	}
            	rowIndex++;
            }

            csvReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
		
		Collections.shuffle(flowers, new Random(3)); //this needs to be done for a better training, 3 was chosen at random
		
		

		
		List<Flower> flowers2 = new ArrayList<>();
		
		try {
            // load the file from the classpath
            InputStream inputStream = Perceptron.class.getClassLoader().getResourceAsStream("iris.csv");
            if (inputStream == null) {
                throw new IOException("File not found in classpath!");
            }

            InputStreamReader fileReader = new InputStreamReader(inputStream);
            CSVReader csvReader = new CSVReader(fileReader);

            String[] row = null;
            int rowIndex = 0;

            
            while ((row = csvReader.readNext()) != null) {
            	if (rowIndex > 0 && rowIndex < 101) {
            		flowers2.add(new Flower(Float.parseFloat(row[0]), Float.parseFloat(row[1]), Float.parseFloat(row[2]), Float.parseFloat(row[3]), row[4]));
            	}
            	rowIndex++;
            }

            csvReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
		
		
		Collections.shuffle(flowers2, new Random(3));
		
		
		Perceptron p = new Perceptron((float) 0.2); //pososesh ok?
		
		System.out.println("starting weights: ");
		for (Float w: p.weights) {
			System.out.println(w);
		}
		
		System.out.println("starting bias: " + p.bias);
		
		p.train(flowers2, 100);
		
		int rightPredictions = 0;
		
		for (Flower f : flowers) {
			rightPredictions += p.predict(f);
		}
		
		System.out.println("\npredicted right " + rightPredictions + " out of " + flowers.size());
		System.out.println("precentage: " + (float) rightPredictions/ (float) flowers.size()*100 + "%");
		
		System.out.println("final weights: ");
		for (Float w: p.weights) {
			System.out.println(w);
		}
		
		System.out.println("final bias: " + p.bias);
		
		
	}
}



//TODO:
//organise the loading functions
//maybe decrease the number of weights and features?
//make it more generalised