package optimizador.genetico.mutacion;

import optimizador.genetico.UtilesGenetico;
import optimizador.genetico.Individuo;
import static optimizador.genetico.UtilesGenetico.randomRangedNumberDecimal;
import weka.classifiers.functions.MultilayerPerceptron;
import weka.classifiers.lazy.IBk;

/**
 *
 * @author Andres Ruiz Carrasco
 * @author Adrian Lorenzo Mateo
 */
public class MutacionGen implements Mutacion {

    @Override
    public void muta(Individuo mutante) {
        
        if(mutante.getClasificador().equals(MultilayerPerceptron.class)){
            
            double rnd;
            double learningRate = ((MultilayerPerceptron) mutante.
                    getClasificador()).getLearningRate();
            
            do{
                rnd = randomRangedNumberDecimal(-10,10);
            
            }while((learningRate + rnd) <= 0);
            ((MultilayerPerceptron) mutante.getClasificador()).setLearningRate(learningRate + rnd);
        }
            
        else if(mutante.getClasificador().equals(IBk.class)){
        }
    }
}
