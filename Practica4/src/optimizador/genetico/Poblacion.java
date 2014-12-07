package optimizador.genetico;

import optimizador.factory.Factory;
import optimizador.genetico.Individuo;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import weka.classifiers.Classifier;
import weka.core.Instances;


/**
 *
 * @author Andres Ruiz Carrasco
 * @author Adrian Lorenzo Mateo
 */
public class Poblacion {
    
    /**
     * Numero maximo de individuos en una poblacion
     */
    public final int MAX_INDIVIDUOS = 50000;
    
    private int nIndividuos;
    private Individuo[] individuos;
    private double sumScores;
    
    private int maxIndividuos;
    
    public Poblacion(int maxIndividuos, Factory factory){
        this.nIndividuos = 0;
        this.individuos = new Individuo[MAX_INDIVIDUOS];
        
        this.maxIndividuos = maxIndividuos;
        for (int i = 0; i < maxIndividuos;i++){
            this.individuos[i]=new Individuo(factory);
            this.nIndividuos++;
        }    
    }
    
    public void addIndividuo(Individuo individuo){
        if (this.MAX_INDIVIDUOS-this.nIndividuos > 0) {
            this.individuos[this.nIndividuos]=individuo;
            this.nIndividuos++;
        }
    }
    
    public void scoring(Evaluador evaluador, Instances data, int nFolds) throws Exception{
        this.sumScores = 0;
        for (int i = 0; i < this.nIndividuos;i++){
            if(this.individuos[i].getMutated() == 1)
                this.individuos[i].setScore(evaluador.puntua(this.individuos[i], data, nFolds));   
            this.individuos[i].setMutated(0);
            this.sumScores += this.individuos[i].getScore();
        }
    }

    /*Metodos de acceso*/
    public Individuo[] getIndividuos() {
        return individuos;
    }

    public void setIndividuos(Individuo[] individuos) {
        this.individuos = individuos;
    }
    
    public int getMaxIndividuos() {
        return maxIndividuos;
    }

    public void setMaxIndividuos(int maxIndividuos) {
        this.maxIndividuos = maxIndividuos;
    }

    public int getnIndividuos() {
        return nIndividuos;
    
    }    
    public ArrayList<Individuo> getIndividuosPoblacion() {
        
        ArrayList<Individuo> individuos = new ArrayList<>();
 
        for(int i=0; i<maxIndividuos;i++){
            individuos.add(this.individuos[i]);
        }
        return individuos;
            
    }

    public void setnIndividuos(int nIndividuos) {
        this.nIndividuos = nIndividuos;
    }

    public double getSumScores() {
        return sumScores;
    }

    public void setSumScores(double sumScores) {
        this.sumScores = sumScores;
    }
    
    public void sort(){
        Arrays.sort(this.individuos,0,this.nIndividuos, Collections.reverseOrder());
    }
}
