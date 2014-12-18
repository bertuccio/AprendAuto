package optimizador.genetico;

import weka.classifiers.Evaluation;
import weka.core.Instances;

/**
 * Clase que implementa un Evaluador (FitnessFunction)
 * @author Adrian Lorenzo Mateo
 * @author Andres Ruiz Carrasco
*/ 
public class FitnessFunction implements Evaluador {
        
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
        return (1 - error);
        
    }
}
