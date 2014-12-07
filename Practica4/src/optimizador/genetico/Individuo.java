package optimizador.genetico;

import optimizador.factory.Factory;
import java.util.ArrayList;
import static optimizador.genetico.UtilesGenetico.randomRangedNumber;
import static optimizador.genetico.UtilesGenetico.randomRangedNumberDecimal;
import weka.classifiers.*;
import weka.classifiers.functions.MultilayerPerceptron;
import weka.classifiers.lazy.IBk;
import weka.core.Instances;
/**
 *
 * @author Andres Ruiz Carrasco
 * @author Adrian Lorenzo Mateo
 */
public class Individuo implements Comparable<Individuo> {
    
    private Classifier clasificador;
    private int mutated;
    private double score;
    

    public Individuo(Factory factory){
        this.mutated = 1; 
        this.clasificador = factory.makeProduct();
        this.score = 0; 
        
    }
    
    /*Muestra debe de ser solo los datos a machear sin la clase*/
    public int[] evaluate(Instances muestra){
        int res[] = new int[2];

        return res;
    }
 

    public int getMutated() {
        return mutated;
    }

    public void setMutated(int mutated) {
        this.mutated = mutated;
    }

    public double getScore() {
        return score;
    }

    public void setScore(double score) {
        this.score = score;
    }

    @Override
    public int compareTo(Individuo o) {
        if(this.score > o.score) return 1;
        else if(this.score < o.score) return -1;
        return 0;
    }

    public Classifier getClasificador() {
        return clasificador;
    }
    
    

}
