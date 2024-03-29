package configuration;


import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;

import weka.core.Instances;
import weka.core.converters.CSVLoader;

public class ConverterTypeToData {
	
	public static void main(String[] args) throws Exception{
		
		  if (args.length != 2) {
		      System.out.println("\nUsage: CSV2Arff <input.csv> <output.arff>\n");
		      System.exit(1);
		    }

		    // load CSV
		    CSVLoader loader = new CSVLoader();
		    loader.setSource(new File(args[0]));
		    Instances data = loader.getDataSet();
		    String fileName = args[1];


		    // save ARFF
		    BufferedWriter writer = new BufferedWriter(new FileWriter(fileName));
		    writer.write(data.toString());
		    writer.flush();
		    writer.close();
		
	}


}
