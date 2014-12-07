package optimizador.genetico.recombinacion;

import optimizador.genetico.UtilesGenetico;
import optimizador.genetico.Individuo;
import java.util.ArrayList;
import optimizador.factory.Factory;
import weka.classifiers.functions.MultilayerPerceptron;
import weka.classifiers.lazy.IBk;

/**
 *
 * @author Andres Ruiz Carrasco
 * @author Adrian Lorenzo Mateo
 */
public class RecombinacionSimple implements Recombinacion {
    
        /*Funcion que recombina dos individuos,
    Salida: Una lista con los individuos nuevos.
    */
    @Override
    public ArrayList<Individuo> recombina(Individuo a, Individuo b, Factory factory){
        
        ArrayList<Individuo> res = new ArrayList<>();
        

        Individuo a1 = new Individuo(factory);
        Individuo b1 = new Individuo(factory);
        if(a.getClasificador().equals(MultilayerPerceptron.class)){
            ((MultilayerPerceptron) a1.getClasificador()).setHiddenLayers(
            ((MultilayerPerceptron) b.getClasificador()).getHiddenLayers());
            ((MultilayerPerceptron) a1.getClasificador()).setLearningRate(
            ((MultilayerPerceptron) a.getClasificador()).getLearningRate());
            
            ((MultilayerPerceptron) b1.getClasificador()).setHiddenLayers(
            ((MultilayerPerceptron) a.getClasificador()).getHiddenLayers());
            ((MultilayerPerceptron) b1.getClasificador()).setLearningRate(
            ((MultilayerPerceptron) b.getClasificador()).getLearningRate());
        }
        else if(a.getClasificador().equals(IBk.class)){
        
        }
        
        
        res.add(0,a1);
        res.add(1,b1);
        
        return res;
    }
   
}