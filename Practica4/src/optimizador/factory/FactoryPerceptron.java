/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package optimizador.factory;

import static optimizador.genetico.UtilesGenetico.randomRangedNumber;
import static optimizador.genetico.UtilesGenetico.randomRangedNumberDecimal;
import weka.classifiers.Classifier;
import weka.classifiers.functions.MultilayerPerceptron;

/**
 *
 * @author pinwi
 */
public class FactoryPerceptron extends Factory {
    
    @Override
    public Classifier makeProduct(){
        
        MultilayerPerceptron perceptron = new MultilayerPerceptron();
        perceptron.setLearningRate(randomRangedNumberDecimal(1,10));
        Integer rand = randomRangedNumber(3,25);
        perceptron.setHiddenLayers(rand.toString());
        perceptron.setTrainingTime(100);
        
        return perceptron;
    }
    
}
