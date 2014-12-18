package optimizador.factory;

import static optimizador.genetico.UtilesGenetico.randomRangedNumber;
import static optimizador.genetico.UtilesGenetico.randomRangedNumberDecimal;
import weka.classifiers.Classifier;
import weka.classifiers.functions.MultilayerPerceptron;

/**
 * Instanciacion de factoria para la creacion de una RN aleatoria (MLP)
 * @author Adrian Lorenzo Mateo
 * @author Andres Ruiz Carrasco
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