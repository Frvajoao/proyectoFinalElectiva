package controllers;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.StringReader;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Random;

import weka.associations.Apriori;
import weka.classifiers.Classifier;
import weka.classifiers.trees.J48;
import weka.clusterers.ClusterEvaluation;
import weka.clusterers.Cobweb;
import weka.clusterers.EM;
import weka.clusterers.SimpleKMeans;
import weka.core.Instances;
import weka.core.converters.ArffLoader;
import weka.core.pmml.jaxbbindings.Cluster;

public class controllerWeka {
	
    //Definimos el formato para los decimales
    DecimalFormat formato = new DecimalFormat("#.##");

    public String definirEncabezado(Instances data) {

        //Se define el encabezado del mensaje, teniendo en cuenta el atributo clase
        String descripcion = "<b>El atribbuto clase seleccionado es "
                + data.attribute(data.numAttributes() - 1).name() + "</b>";
        descripcion += " <b>con posibles valores:</b> ";
        //Se recorren los posibles valores del atributo clase
        for (int z = 0; z < data.attribute(data.numAttributes() - 1).numValues(); z++) {
            descripcion += "<b>" + data.attribute(data.numAttributes() - 1).value(z) + "</br>";
        }
        return descripcion;
    }
    
    public static String apriori (Instances data){
        try {
            //creamos un objeto de asociacion por Apriori
            Apriori aso = new Apriori();
            
            //creamos el descriptivo apriori con los datos
            aso.buildAssociations(data);
            
            /*se cargan los resultados de la asociación apriori*/
            String resApriori = "<br><b><center>Resultados Asociacion "
                    + "Apriori</center<br>========<br>El modelo de asociacion "
                    + "generado indica los siguientes resultados: "
                    + "<br>========<br></b>";
            
            
            //obtenemos los resultados
            for (int i = 0; i < aso.getAssociationRules().getRules().size(); i++) {
                resApriori = resApriori + "<b>" + (i+1)+". Si</b> "
                        + aso.getAssociationRules().getRules().get(i).getPremise().toString();
                
                resApriori = resApriori + "<b>" + (i+1)+". Entonces</b> "
                        + aso.getAssociationRules().getRules().get(i).getConsequence().toString();
                
                resApriori = resApriori + "<b>" + (i+1)+". Con un</b> "
                        + (int) aso.getAssociationRules().getRules().get(i).getPrimaryMetricValue() * 100 +"% de probabilidades.";
                
            }
            
            return resApriori;
            
        } catch (Exception e) {
            return "El error es"+e.getMessage();
        }
    }
    
    public static String decisionTree (Instances data){
        try {
        	
        	String modelFile = "C:\\Users\\Joao\\Desktop\\EAM\\pruebasCSV\\model_test";
            
        	BufferedReader reader = new BufferedReader(
    				new FileReader("C:\\Users\\Joao\\Desktop\\EAM\\pruebasCSV\\cancer.arff"));
    		Instances inst = new Instances(reader);
    		reader.close();
    		inst.setClassIndex(inst.numAttributes() - 1);

    		//Init classifier
    		Classifier cls = new J48();
    		cls.buildClassifier(inst);
    		
    		//Print tree
    	     System.out.println(cls);

    		// serialize model
    		weka.core.SerializationHelper.write(modelFile, cls);
                
    		return cls.toString();
            
        } catch (Exception e) {
            return "El error es"+e.getMessage();
        }
		
    }
    
    public static void decisionTreeV2 () throws Exception{
    	
        
        ArffLoader loader= new ArffLoader();
        loader.setSource(new File("C:\\Users\\Joao\\Desktop\\EAM\\pruebasCSV\\cancer.arff"));
        Instances data= loader.getDataSet();


       //Setting class attribute 
       data.setClassIndex(data.numAttributes() - 1);

      //Make tree
      J48 tree = new J48();
      String[] options = new String[1];
      options[0] = "-U"; 
      tree.setOptions(options);
      tree.buildClassifier(data);

      //Print tree
      System.out.println(tree);

      //Predictions with test and training set of data
      
/*      ArffLoader loaderr= new ArffLoader();
      loaderr.setSource(new File("C:\\Users\\Joao\\Desktop\\EAM\\pruebasCSV\\cancer.arff"));
      Instances datafile= loaderr.getDataSet();
      //Setting class attribute 
      datafile.setClassIndex(datafile.numAttributes() - 1);

      ArffLoader loaderTest= new ArffLoader();
      loaderTest.setSource(new File("C:\\Users\\Joao\\Desktop\\EAM\\pruebasCSV\\cancer.arff"));
      Instances testfile= loaderTest.getDataSet();
      //Setting class attribute 
      testfile.setClassIndex(testfile.numAttributes() - 1);

      
      // train classifier
      Classifier cls = new J48();
      cls.buildClassifier(datafile);
      // evaluate classifier and print some statistics
      Evaluation eval = new Evaluation(datafile);
      eval.evaluateModel(cls, testfile);
      System.out.println(eval.toSummaryString("\nResults\n======\n", false));*/
    	
    }
    
	public static void performClustering(Instances dataset) throws Exception {

		
		// new instance of clusterer
		EM model = new EM();
		// build the clusterer
		model.buildClusterer(dataset);
		System.out.println(model);
		System.out.println("NroCluster--> "+model.getNumClusters());
		System.out.println("NroClusterMax--> "+model.getMaximumNumberOfClusters());
		System.out.println("NroClusterKmeans--> "+model.getNumKMeansRuns());
		System.out.println("NroClusterSlots--> "+model.getNumExecutionSlots());
		System.out.println("NroClusterMaxIterations--> "+model.getMaxIterations());
		System.out.println("NroClusterMaxIFlosds--> "+model.getNumFolds());
		
		double logLikelihood = ClusterEvaluation.crossValidateModel(model, dataset, 10, new Random(1));
		System.out.println(logLikelihood);

	}
    
    public static BufferedReader readDataFile(String filename) {
        BufferedReader inputReader = null;

         try {
             inputReader = new BufferedReader(new FileReader(filename));
          } catch (FileNotFoundException ex) {
             System.err.println("File not found: " + filename);
         }

         return inputReader;
     }
    
    public ArrayList<Cluster> cluster(Instances data){
    	try {
    		ArrayList<Cluster> elementos = new ArrayList<Cluster>();
    		final Cobweb coweb = new Cobweb();
    		coweb.buildClusterer(data);
    		SimpleKMeans kmeans = new SimpleKMeans();
    		kmeans.setSeed(5);
    		
    		kmeans.setPreserveInstancesOrder(true);
    		kmeans.setNumClusters(coweb.numberOfClusters());
    		
    		kmeans.buildClusterer(data);
    		
    		Instances instances = kmeans.getClusterCentroids();
    		
    		for (int i = 0; i < kmeans.getNumClusters(); i++) {
				
    			System.out.println("Instances "+instances.instance(i));
    			String[] instanceData = (instances.instance(i)).toString().split(",");
    			Cluster clu = new Cluster();
    			
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		return null;
    }
    
    
    public String mineria(String datos){

        //Los datos tipos string lo convertimos  a un StringReader
        StringReader sr = new StringReader(datos);
        //El stringreader lo convertimos a un buffer
        BufferedReader br = new BufferedReader(sr);

        try {
        	
            //Definimos un objeto que contendra  los datos a clasificar
            Instances data = new Instances(br);
            //Seleccionamos cual sera el atributo clase, el cual  es el total de atributos  -  1(El ultimo)
            data.setClassIndex(data.numAttributes() - 1);
            //Cerramos el objeto buffer
            br.close();

 
            String resultadoApriori = apriori(data);
            
                      
            return resultadoApriori;

        } catch (IOException e) {
            return "El error es" + e.getMessage();
        }
        

    }

    
 

}
