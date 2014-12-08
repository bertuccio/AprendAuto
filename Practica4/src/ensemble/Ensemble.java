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
        for(Classifier c : clasificadores){
            Evaluation eval = new Evaluation(data);
            eval.crossValidateModel(c, data, nFolds, rnd);
            error += eval.errorRate();
        }
        return error/clasificadores.size();
    }
    
    
    
}
