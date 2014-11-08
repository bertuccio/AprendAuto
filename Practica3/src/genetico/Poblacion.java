/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package genetico;
import genetico.cruce.Cruce;
import genetico.mutacion.Mutacion;
import java.util.ArrayList;
import genetico.selecciones.Seleccion;

/**
 *
 * @author temporal
 */
public class Poblacion {
    
    private ArrayList<Individuo> individuos = new ArrayList<>();;
    
    /****************************************************/
    /*Cada mutacion/cruce dentro de la lista tiene una  */
    /*probabilidad equiprobable de suceder es decir,    */
    /*Despues de saber si hay mutacion p(0.1) o cruce   */
    /* p(0.6), se elije al azar entre una de la lista.  */
    /* caso base -> un unico cruce/mutacion             */
    /****************************************************/
    ArrayList<Mutacion> mutaciones = new ArrayList<>();
    ArrayList<Cruce> cruces = new ArrayList<>();
    ArrayList<Seleccion> selecciones = new ArrayList<>();

    public ArrayList<Individuo> getIndividuos() {
        return individuos;
    }

    public void setIndividuos(ArrayList<Individuo> individuos) {
        this.individuos = individuos;
    }

    public ArrayList<Mutacion> getMutaciones() {
        return mutaciones;
    }

    public void setMutaciones(ArrayList<Mutacion> mutaciones) {
        this.mutaciones = mutaciones;
    }

    public ArrayList<Cruce> getCruces() {
        return cruces;
    }

    public void setCruces(ArrayList<Cruce> cruces) {
        this.cruces = cruces;
    }

    public ArrayList<Seleccion> getSelecciones() {
        return selecciones;
    }

    public void setSelecciones(ArrayList<Seleccion> selecciones) {
        this.selecciones = selecciones;
    }

    public int getMaxIndividuos() {
        return maxIndividuos;
    }

    public void setMaxIndividuos(int maxIndividuos) {
        this.maxIndividuos = maxIndividuos;
    }
    
    private int maxIndividuos;
    
    public Poblacion(int maxIndividuos,int nClases,int nAtributos,int rangoAtributos,int maxReglasIni){
        this.maxIndividuos = maxIndividuos;
        for (int i = 0; i < maxIndividuos;i++){
            this.individuos.add(new Individuo(nClases,nAtributos,rangoAtributos,maxReglasIni));
        }    
    }
    
   public ArrayList<Individuo> muta(Individuo mutante){
        int mutacionIndex = UtilesGenetico.randomNumber(this.mutaciones.size() - 1);
        return this.mutaciones.get(mutacionIndex).muta(mutante);
   }
    
   public ArrayList<Individuo> reproduce(Individuo a,Individuo b){
        int cruceIndex = UtilesGenetico.randomNumber(this.cruces.size() - 1);
        return this.cruces.get(cruceIndex).cruza(a,b);
   }
   
   public ArrayList<Individuo> seleccionNatural(ArrayList<Individuo> nuevaPoblacion){
        int seleccionIndex = UtilesGenetico.randomNumber(this.selecciones.size() - 1);
        return this.selecciones.get(seleccionIndex).selecciona(nuevaPoblacion,this.maxIndividuos);
   } 
}
