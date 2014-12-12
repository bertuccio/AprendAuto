/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package practica4;

import ensemble.Ensemble;
import java.util.Random;
import optimizador.data.Datos;
import weka.classifiers.Evaluation;
import weka.classifiers.bayes.NaiveBayes;
import weka.classifiers.functions.Logistic;
import weka.classifiers.functions.MultilayerPerceptron;
import weka.classifiers.lazy.IBk;

/**
 *
 * @author pinwi
 */
public class EnsembleApp {
    
        public static void main(String[] args) throws Exception {
            
            int nFolds = 10;
            String inputFile = "input";
            int k = 5;
            Integer neurons = new Integer(10);
            int epochs = 500;
            
            for (int i = 0; i < args.length; i++) {
            
                if (args[i].compareTo("-input") == 0) {

                    inputFile = args[i + 1];
                    i++;
                }
                if (args[i].compareTo("-nFolds") == 0) {

                    nFolds = Integer.parseInt(args[i + 1]);
                    i++;
                }
                if (args[i].compareTo("-K") == 0) {
                    
                    k = Integer.parseInt(args[i + 1]);
                }
                if (args[i].compareTo("-neurons") == 0) {

                    neurons = Integer.parseInt(args[i + 1]);
                }
                if (args[i].compareTo("-epochs") == 0) {

                    epochs = Integer.parseInt(args[i + 1]);
                }
            }
            
            Random rnd = new Random(System.currentTimeMillis());
            Datos data = new Datos(inputFile);
            Ensemble e = new Ensemble();
            
            MultilayerPerceptron perceptron = new MultilayerPerceptron();
            perceptron.setHiddenLayers(neurons.toString());                   // Perceptron parameter: nr. of neurons per layer, separated by commas
            perceptron.setTrainingTime(epochs); 
            e.addClassifier(perceptron);
//            Evaluation eval = new Evaluation(data.getData());
//            eval.crossValidateModel(perceptron, data.getData(), nFolds, rnd);
//            System.out.println("Perceptron: "+(1-eval.errorRate()));
            
            IBk knn = new IBk();
            knn.setKNN(k);                                    // Nr. neighbors
            e.addClassifier(knn);
//            eval = new Evaluation(data.getData());
//            eval.crossValidateModel(knn, data.getData(), nFolds, rnd);
//            System.out.println("IBk: "+(1-eval.errorRate()));
            
            NaiveBayes nb = new NaiveBayes();
            e.addClassifier(nb);
//            eval = new Evaluation(data.getData());
//            eval.crossValidateModel(nb, data.getData(), nFolds, rnd);
//            System.out.println("NaiveBayes: "+(1-eval.errorRate()));
            
            Logistic logistic = new Logistic();
            logistic.setMaxIts(epochs);                           // Nr. epochs
            e.addClassifier(logistic);
//            eval = new Evaluation(data.getData());
//            eval.crossValidateModel(logistic, data.getData(), nFolds, rnd);
//            System.out.println("Logistic: "+(1-eval.errorRate()));
            
            System.out.println("Ensemble: "+(1-e.eval(data.getData(), 10)));
            
        }
    
}
