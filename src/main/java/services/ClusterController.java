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
public class ClusterController {

    private static final String template = "Algoritmo %s!";
    private final AtomicLong counter = new AtomicLong();

    @RequestMapping("/cluster")
    public Cluster cluster(@RequestParam(value="name", defaultValue="Cluster") String name) throws Exception {
    	
    	
        return new Cluster(counter.incrementAndGet(),
                            String.format(template, name),String.valueOf("pruebas"));
    }

	private Instances getData() throws FileNotFoundException, IOException {
		Instances dataset = new Instances(new BufferedReader(new FileReader("C:\\Users\\Joao\\Desktop\\EAM\\pruebasCSV\\cancer.arff")));
		return dataset;
	}
}
