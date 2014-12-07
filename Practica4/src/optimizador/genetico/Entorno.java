package optimizador.genetico;

import optimizador.genetico.UtilesGenetico;
import optimizador.genetico.Evaluador;
import optimizador.genetico.Poblacion;
import optimizador.genetico.Individuo;
import optimizador.genetico.seleccion.Seleccion;
import optimizador.factory.Factory;
import java.util.ArrayList;
import java.util.Iterator;
import optimizador.genetico.mutacion.Mutacion;
import optimizador.genetico.recombinacion.Recombinacion;
import weka.core.Instances;

/**
 *
 * @author Andres Ruiz Carrasco
 * @author Adrian Lorenzo Mateo
 */
public class Entorno {
    
    private Poblacion poblacion;
    Instances data;
    private double king;
    
    /*Cuando metemos estos*/
    ArrayList<Mutacion> mutaciones = new ArrayList<>();
    ArrayList<Recombinacion> cruces = new ArrayList<>();
    ArrayList<Seleccion> selecciones = new ArrayList<>();
    Evaluador evaluador;
    Factory factory;
    private double probMutation;
    private double probRecombine;
    
    public Entorno(int maxIndividuos,double probMutation,double probRecombine,Evaluador evaluator,
            Instances data, Factory factory){
         
        this.data = data;
        this.factory = factory;
        this.probMutation = probMutation;
        this.probRecombine = probRecombine;
        this.evaluador = evaluator;
        this.poblacion = new Poblacion(maxIndividuos, this.factory);    
    }
    
    
    /*Funcion que calcula los scores de los individuos que lo necesiten (aquellos marcados como mutados)*/
    /*Siempre se ejecuta sobre el entorno de evolucion*/
    public void scoring(int nFolds)  throws Exception{
        this.poblacion.scoring(this.evaluador,data,nFolds);
    }
    
    /*Funcion que realiza los siguientes pasos:
        0) Calcula el score
        1) Selecciona un grupo de individuos a cruzar
        2) Recombina estos individuos
        3) Añade los nuevos individuos a la poblacion
    */
    public void evolve() throws Exception{
        
        /*Actualizamos los scores de cada individuo*/
        this.scoring(3);
        
        /*Obtenemos los individuos selecionados para el cruce*/
        ArrayList<Integer> index = this.selecciones.get(UtilesGenetico.randomNumber(
                this.selecciones.size() - 1)).selecciona(
                        poblacion, 
                        new Double(this.probRecombine * this.poblacion.getnIndividuos()).intValue());
        
        /*Realizamos los cruces pertinentes*/
        Iterator itr = index.iterator();
        ArrayList<Individuo> recombineResult;
        while(itr.hasNext()) {
            Integer a = (Integer) itr.next();
            Integer b;
            if (itr.hasNext()) {
                b = (Integer) itr.next();
                recombineResult = this.cruces.get(UtilesGenetico.randomNumber(
                        this.cruces.size() - 1)).recombina(
                                this.poblacion.getIndividuos()[a],
                                this.poblacion.getIndividuos()[b], this.factory);
                for (Individuo i : recombineResult){
                    this.poblacion.addIndividuo(i);
                }
            }
        }
    }
    
    /*
    Funcion que muta un numero concreto de Individuos al azar
    */
    public void mutate(){
        int nMutantes = (int) (this.probMutation * this.poblacion.getnIndividuos());
        for(int i = nMutantes; i>=0; i--){
            
            this.mutaciones.get(
                    UtilesGenetico.randomNumber(
                            this.mutaciones.size()-1)).muta(
                                    this.poblacion.getIndividuos()[UtilesGenetico.randomNumber(this.poblacion.getnIndividuos()-1)]);
        }
    }
    
    /*
    Fucion que realiza los siguientes pasos:
        1) Evoluciona la poblacion
        2) Muta la poblacion
        3) Selecciona la nueva poblacion
    */
    public void epoch() throws Exception{
        
        Individuo[] newPoblation = new Individuo[this.poblacion.MAX_INDIVIDUOS];
        
        
        this.evolve();
        this.mutate();
        this.scoring(3);
        
        this.poblacion.sort();
        
        newPoblation[0] = this.poblacion.getIndividuos()[0];
        this.poblacion.getIndividuos()[0].setScore(0);
        this.poblacion.getIndividuos()[0].setMutated(1);
        
        newPoblation[1] = this.poblacion.getIndividuos()[1];
        this.poblacion.getIndividuos()[1].setScore(0);
        this.poblacion.getIndividuos()[1].setMutated(1);
        
        /*Obtenemos los individuos selecionados para sobrevivir*/
        ArrayList<Integer> index = this.selecciones.get(UtilesGenetico.randomNumber(
                this.selecciones.size()- 1)).selecciona(
                        this.poblacion, 
                        new Double(this.poblacion.getMaxIndividuos()).intValue() - 2);
        
        int j=2;
        for (Integer i : index){
            newPoblation[j] = this.poblacion.getIndividuos()[i];
            j++;
        }
        
        this.poblacion.setIndividuos(newPoblation);
        this.poblacion.setSumScores(0);
        this.poblacion.setnIndividuos(j);
        
        this.scoring(3);
        this.poblacion.sort();
//        if (this.king > this.poblacion.getIndividuos()[0].getScore()){
//                System.out.println(" Raro ");
//        }
        this.king = this.poblacion.getIndividuos()[0].getScore();
    }
    
    /*
    Funcion que prueba el individuo mejor de la poblacion
    Retorno: Devuelve un array list con las predicciones del individuo mejor
    *//*
    public ArrayList<Integer> test(){
        ArrayList<Integer> results = new ArrayList();
        
        this.scoring();
        this.poblacion.sort();
        
        Individuo ganador = this.poblacion.getIndividuos()[0];
        
        int i = 0;
        for(MuestraGenetica muestra : entornoTesteo){
            results.add(i,ganador.evaluate(muestra.getArgs())[0]);
            i++;
        }
        
        return results;
    }
    
    */
    
    
    
    /*Metodos de acceso*/
    public double getProbMutation() {
        return probMutation;
    }

    public void setProbMutation(double probMutation) {
        this.probMutation = probMutation;
    }

    public double getProbRecombine() {
        return probRecombine;
    }

    public void setProbRecombine(double probRecombine) {
        this.probRecombine = probRecombine;
    }
    
    public Poblacion getPoblacion() {
        return this.poblacion;
    }

    public Poblacion setPoblacion(Poblacion poblacion) {
        return this.poblacion = poblacion;
    }

    public ArrayList<Mutacion> getMutaciones() {
        return mutaciones;
    }

    public void setMutaciones(ArrayList<Mutacion> mutaciones) {
        this.mutaciones = mutaciones;
    }

    public ArrayList<Recombinacion> getCruces() {
        return cruces;
    }

    public void setCruces(ArrayList<Recombinacion> cruces) {
        this.cruces = cruces;
    }

    public ArrayList<Seleccion> getSelecciones() {
        return selecciones;
    }

    public void setSelecciones(ArrayList<Seleccion> selecciones) {
        this.selecciones = selecciones;
    }

    public Evaluador getEvaluador() {
        return evaluador;
    }

    public void setEvaluador(Evaluador evaluador) {
        this.evaluador = evaluador;
    }

    public double getKing() {
        return king;
    }

    public void setKing(double king) {
        this.king = king;
    }
    
}

