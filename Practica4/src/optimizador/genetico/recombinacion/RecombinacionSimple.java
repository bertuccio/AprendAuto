package optimizador.genetico.recombinacion;

import java.util.ArrayList;
import optimizador.factory.Factory;
import optimizador.genetico.Individuo;
import weka.classifiers.functions.MultilayerPerceptron;
import weka.classifiers.lazy.IBk;

/**
 * Implementacion de la recombinacion Simple de los clasificadores IBK y MPL 
 * @author Adrian Lorenzo Mateo
 * @author Andres Ruiz Carrasco
*/ 
public class RecombinacionSimple implements Recombinacion {
    
    @Override
    public ArrayList<Individuo> recombina(Individuo a, Individuo b, Factory factory){
        
        ArrayList<Individuo> res = new ArrayList<>();
        

        Individuo a1 = new Individuo(factory);
        Individuo b1 = new Individuo(factory);
        
        if(a.getClasificador().getClass().equals(MultilayerPerceptron.class)){
            
            
            ((MultilayerPerceptron) a1.getClasificador()).setHiddenLayers(
            ((MultilayerPerceptron) b.getClasificador()).getHiddenLayers());
            ((MultilayerPerceptron) a1.getClasificador()).setLearningRate(
            ((MultilayerPerceptron) a.getClasificador()).getLearningRate());
            
            ((MultilayerPerceptron) b1.getClasificador()).setHiddenLayers(
            ((MultilayerPerceptron) a.getClasificador()).getHiddenLayers());
            ((MultilayerPerceptron) b1.getClasificador()).setLearningRate(
            ((MultilayerPerceptron) b.getClasificador()).getLearningRate());
        }
        else if(a.getClasificador().getClass().equals(IBk.class)){
        
           
            int knna =((IBk) a.getClasificador()).getKNN();
            int knnb = ((IBk) b.getClasificador()).getKNN();
            
            ((IBk) a1.getClasificador()).setKNN((int) Math.floor((knna +knnb) / 2.0));
            ((IBk) a1.getClasificador()).setKNN((int) Math.ceil((knna +knnb) / 2.0));
        }
        
        
        res.add(0,a1);
        res.add(1,b1);
        
        return res;
    }
   
}
