package services;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.concurrent.atomic.AtomicLong;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import controllers.controllerWeka;
import weka.core.Instances;

@RestController
public class J48Controller {

    private static final String template = "Algoritmo %s!";
    private final AtomicLong counter = new AtomicLong();

    @RequestMapping("/j48")
    public J48 j48(@RequestParam(value="name", defaultValue="J48") String name) throws Exception {
    	String data = controllerWeka.decisionTree(getData());;
    	
        return new J48(counter.incrementAndGet(),
                            String.format(template, name),data);
    }

	private Instances getData() throws FileNotFoundException, IOException {
		Instances dataset = new Instances(new BufferedReader(new FileReader("C:\\Users\\Joao\\Desktop\\EAM\\pruebasCSV\\cancer.arff")));
		return dataset;
	}
}
