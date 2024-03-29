package optimizador.genetico.mutacion;

import optimizador.genetico.Individuo;
import static optimizador.genetico.UtilesGenetico.coinDrop;
import static optimizador.genetico.UtilesGenetico.randomRangedNumberDecimal;
import weka.classifiers.functions.MultilayerPerceptron;
import weka.classifiers.lazy.IBk;

/**
 * Implementacion de la mutacion de los clasificadores IBK y MPL 
 * @author Adrian Lorenzo Mateo
 * @author Andres Ruiz Carrasco
*/ 
public class MutacionGen implements Mutacion {
    
    @Override
    public void muta(Individuo mutante) {
        
        if(mutante.getClasificador().getClass().equals(MultilayerPerceptron.class)){
            
            double rnd;
            double learningRate = ((MultilayerPerceptron) mutante.
                    getClasificador()).getLearningRate();
            
            do{
                rnd = randomRangedNumberDecimal(-10,10);
            
            }while((learningRate + rnd) <= 0);
            ((MultilayerPerceptron) mutante.getClasificador()).setLearningRate(learningRate + rnd);
        }
            
        else if(mutante.getClasificador().getClass().equals(IBk.class)){
            
            int k[] = {-1,1};
            int lastValue = ((IBk) mutante.getClasificador()).getKNN();
            do{
                
                ((IBk) mutante.getClasificador()).setKNN(
                    ((IBk) mutante.getClasificador()).getKNN()+k[coinDrop()]);
                
                while(((IBk) mutante.getClasificador()).getKNN()<=1)
                    ((IBk) mutante.getClasificador()).setKNN(
                            ((IBk) mutante.getClasificador()).getKNN()+1);
                
            }while((((IBk) mutante.getClasificador()).getKNN() == lastValue) || 
                    ((((IBk) mutante.getClasificador()).getKNN() % 2) == 0));
        }
    }
}
