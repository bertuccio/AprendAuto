/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ensemble;

import java.util.ArrayList;
import java.util.Random;
import weka.classifiers.Classifier;
import weka.classifiers.Evaluation;
import weka.core.Instance;
import weka.core.Instances;

/**
 *
 * @author pinwi
 */
public class Ensemble {
    
    private ArrayList<Classifier> clasificadores = new ArrayList<>();
    
    
    
    public void addClassifier(Classifier c){
        clasificadores.add(c);
    }
    
    public double eval(Instances data, int nFolds) throws Exception{
        
        Random rnd = new Random(System.currentTimeMillis());

        double error = 0;
        
        
        int max = -1;
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
            
        System.out.print(error+"/"+nFolds+"="+error / nFolds+'\n');
        return (error / nFolds)/100;
    }
    
    
    
}
