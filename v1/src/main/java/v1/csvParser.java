package v1;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import com.opencsv.CSVReader;

public class csvParser {
	
	public List<Flower> Parse(int sepalLengthIndex, int sepalWidthIndex, int petalLengthIndex, int petalWidthIndex, int nameIndex, String fileName) throws IOException, CsvValidationException{
		List<Flower> flowers = new ArrayList<>();
		
		try {
            // load the file from the classpath
            InputStream inputStream = Perceptron.class.getClassLoader().getResourceAsStream(fileName);
            if (inputStream == null) {
                throw new IOException("File not found in classpath!");
            }

            InputStreamReader fileReader = new InputStreamReader(inputStream);
            CSVReader csvReader = new CSVReader(fileReader);

            String[] row = null;
            int rowIndex = 0;

            
            while ((row = csvReader.readNext()) != null) {
            	if (rowIndex > 0) {
            		flowers.add(new Flower(Float.parseFloat(row[sepalLengthIndex]), Float.parseFloat(row[sepalWidthIndex]), Float.parseFloat(row[petalLengthIndex]), Float.parseFloat(row[petalWidthIndex]), row[nameIndex]));
            	}
            	rowIndex++;
            }

            csvReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
		
		return flowers;
	}
	
}
