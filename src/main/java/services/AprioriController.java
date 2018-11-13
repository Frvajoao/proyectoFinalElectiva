package services;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.concurrent.atomic.AtomicLong;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import configuration.ConverterTypeToData;
import controllers.controllerWeka;
import weka.core.Instances;

@RestController
public class AprioriController {

    private static final String template = "Algoritmo %s!";
    private final AtomicLong counter = new AtomicLong();

    @RequestMapping("/apriori")
    public Apriori apriori(@RequestParam(value="name", defaultValue="Apriori") String name) throws Exception {
    	String data = controllerWeka.apriori(getData());
    	controllerWeka.decisionTree(getData());
    	
    	String [] test ;
    	test = new  String[] {"C:\\Users\\Joao\\Desktop\\EAM\\pruebasCSV\\babytest.csv","C:\\Users\\Joao\\Desktop\\EAM\\pruebasCSV\\babytest.arff"};
    	ConverterTypeToData.main(test);
        return new Apriori(counter.incrementAndGet(),
                            String.format(template, name),data);
    }

	private Instances getData() throws FileNotFoundException, IOException {
		Instances dataset = new Instances(new BufferedReader(new FileReader("C:\\Users\\Joao\\Desktop\\EAM\\pruebasCSV\\cancer.arff")));
		return dataset;
	}
}
