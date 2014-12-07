package optimizador.genetico;

import optimizador.genetico.Individuo;
import java.util.ArrayList;
import weka.classifiers.Evaluation;
import weka.core.Instances;

/**
 *
 * @author Andres Ruiz Carrasco
 * @author Adrian Lorenzo Mateo
 */
public class FitnessFunction implements Evaluador {
    
    /*Funcion que puntua al individuo segun el porcentajes de acierto en un conjunto dado*/
    @Override
    public double puntua(Individuo individuo, Instances data, int nFolds) throws Exception {
    	
        double error = 0;
        
        for (int fold = 0; fold < nFolds; fold++) {
            Instances training = data.trainCV(nFolds, fold);
            Instances test = data.testCV(nFolds, fold);
        
            Evaluation eval;
            individuo.getClasificador().buildClassifier(training);
            eval = new Evaluation(training);
            eval.evaluateModel(individuo.getClasificador(), test);

            error += eval.errorRate();
        }
        error /= nFolds;
        return (100 - error);
        
    }
}
