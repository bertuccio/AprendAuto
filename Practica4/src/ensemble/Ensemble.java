package ensemble;

import java.util.ArrayList;
import java.util.Random;
import weka.classifiers.Classifier;
import weka.classifiers.Evaluation;
import weka.core.Instance;
import weka.core.Instances;

/**
 * Clase que contiene el modelo de Ensemble.
 * @author Adrian Lorenzo Mateo
 * @author Andres Ruiz Carrasco
 */
public class Ensemble {
    
    private ArrayList<Classifier> clasificadores = new ArrayList<>();
    
    
    /**
     * Añade un clasificador al Conjunto de clasificadores
     * @author Adrian Lorenzo Mateo
     * @author Andres Ruiz Carrasco
     * @param c Clasificador a añadir
    */
    public void addClassifier(Classifier c){
        clasificadores.add(c);
    }
    
    /**
     * Evalua obteniendo la clase mayoritaria segun los clasificadores que contiene
     * @author Adrian Lorenzo Mateo
     * @author Andres Ruiz Carrasco
     * @param data conjunto de datos 
     * @param nFolds numero de divisiones que se van a generar
     * @return Error obtenido en la evaluacion
     * @throws java.lang.Exception Excepcion generica no controlada
    */ 
    public double eval(Instances data, int nFolds) throws Exception{
        
        Random rnd = new Random(System.currentTimeMillis());

        double error = 0;
        
        
        int max;
        int index = 0;
        
        for (int fold = 0; fold < nFolds; fold++) {
            Instances training = data.trainCV(nFolds, fold);
            Instances test = data.testCV(nFolds, fold);
            
            for(Classifier c : clasificadores)
                c.buildClassifier(training);
            
            for(int i=0; i<test.size(); i++){
                
                int votaciones[] = new int[data.numClasses()];
                Instance instancia = test.get(i);
                
                for(Classifier c : clasificadores){
                    Evaluation eval = new Evaluation(data);
                    //System.out.println();
                    //System.out.println(instancia.classValue());
                    Double d = eval.evaluateModelOnce(c, instancia);
                    votaciones[d.intValue()]++;
                }
                
                max = -1;
                for(int j=0; j<votaciones.length; j++){
                    if(votaciones[j] > max){
                        max = votaciones[j];
                        index = j;
                    }
                }

                Double prediccion = (double) index;
                if(instancia.classValue() != prediccion)
                        error++;
            }
        }
        return (error / nFolds)/100;
    }
}
