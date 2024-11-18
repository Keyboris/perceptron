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
	
	public float bias = 0;   //change for private, make functions for all to read
	public float[] weights = {0, 0, 0, 0};
	public float learningRate;
	public String targetName;
	
	
	public Perceptron(float learningRate, String targetName) {
		
		this.learningRate = learningRate;	
		this.targetName = targetName;
		Random rand = new Random();
		
		for (int i = 0; i < 4; i++) {
			weights[i] = rand.nextFloat() * 2 - 1;  //i just found this to be suitable enough for a good enough range
		}
		bias = rand.nextFloat() * 2 - 1;  //same here
	}
	
	public  void train(List<Flower> flowers, int epochs) { 
		//here we will assume that our tagetName is 1 and everything else is 0
		for (int n = 0; n < epochs; n++) {
			for (int i = 0; i < flowers.size(); i++) {
				float tempResult = 0;
				for (int j = 0; j < weights.length; j++) {
					tempResult += weights[j] * flowers.get(i).getParameter(j);
				}
				int guess = activationFunction(tempResult - bias); //HERE
				adjustBias(flowers, guess, i);
				for (int j = 0; j < weights.length; j++) {
					adjustWeight(flowers, j, i, guess);
				}
			}
		}
	}
	
	public int activationFunction(float result) { //can be changed for whatever
		return result > 0 ? 1 : 0;  //if the result gives us 0 the function should not fire
	}
	
	public void adjustBias(List<Flower> flowers ,int guess, int index) {
		int actualValue;
		if (flowers.get(index).getName().equals(targetName)) {
			actualValue = 1;
		}
		else {
			actualValue = 0;
		}
		
		bias = bias + learningRate * (actualValue - guess);
	}
	
	public void adjustWeight(List<Flower> flowers, int weightIndex, int flowerIndex, int guess) {
		int actualValue;
		if (flowers.get(flowerIndex).getName().equals(targetName)) {
			actualValue = 1;
		}
		else {
			actualValue = 0;
		}
		
		weights[weightIndex] = weights[weightIndex] + learningRate * (actualValue - guess) * flowers.get(flowerIndex).getParameter(weightIndex);
		
	}
	
	public int predict(Flower flower) {
		float tempResult = 0;
		for (int j = 0; j < weights.length; j++) {
			tempResult += weights[j] * flower.getParameter(j);
		}
		int guess = activationFunction(tempResult - bias);

		if (guess == 1) {
			return 1;
		}
		else {
			return 0;
		}
//			name = targetName;
//		}
//		else {
//			name = "not" + targetName;
//		}
//		
//		System.out.println("guess: " + name);
//		System.out.println("actual name: " + flower.getName());
//		
//		if (name.equals(flower.getName())) {
//			return 1;
//		}
//		else {
//			return 0;
//		}
			
			
			
			
	}
	
	
	
	
	
	
}



//TODO:
//organise the loading functions
//maybe decrease the number of weights and features?
//make it more generalised