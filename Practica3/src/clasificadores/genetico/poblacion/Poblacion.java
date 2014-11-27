package clasificadores.genetico.poblacion;

import clasificadores.genetico.MuestraGenetica;
import clasificadores.genetico.evaluacion.Evaluador;
import clasificadores.genetico.poblacion.individuo.Individuo;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;


/**
 *
 * @author temporal
 */
public class Poblacion {
    
    /**
     * Numero maximo de individuos en una poblacion
     */
    public final int MAX_INDIVIDUOS = 5000;
    
    private int nIndividuos;
    private Individuo[] individuos;
    private double sumScores;
    
    private int maxIndividuos;
    
    public Poblacion(int maxIndividuos,int nClases,int nAtributos,int rangoAtributos,int maxReglasIni,Evaluador evaluator){
        this.nIndividuos = 0;
        this.individuos = new Individuo[MAX_INDIVIDUOS];
        
        this.maxIndividuos = maxIndividuos;
        for (int i = 0; i < maxIndividuos;i++){
            this.individuos[i]=new Individuo(nClases,nAtributos,rangoAtributos,maxReglasIni);
            this.nIndividuos++;
        }    
    }
    
    public void addIndividuo(Individuo individuo){
        if (this.MAX_INDIVIDUOS-this.nIndividuos > 0) {
            this.nIndividuos++;
            this.individuos[this.nIndividuos]=individuo;
        }
    }
    
    public void scoring(Evaluador evaluador,ArrayList<MuestraGenetica> entorno){
        this.sumScores = 0;
        for (int i = 0; i < this.nIndividuos;i++){
            if(this.individuos[i].getMutated() == 1)
                this.individuos[i].setScore(evaluador.puntua(this.individuos[i], entorno));   
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
        Arrays.sort(this.individuos, Collections.reverseOrder());
    }
}
