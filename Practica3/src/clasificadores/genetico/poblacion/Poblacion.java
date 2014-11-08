package clasificadores.genetico.poblacion;
import clasificadores.genetico.UtilesGenetico;
import clasificadores.genetico.poblacion.individuo.Individuo;
import clasificadores.genetico.poblacion.individuo.mutacion.Mutacion;
import clasificadores.genetico.poblacion.individuo.recombinacion.Recombinacion;
import clasificadores.genetico.poblacion.selecciones.Seleccion;
import java.util.ArrayList;

/**
 *
 * @author temporal
 */
public class Poblacion {
    
    private ArrayList<Individuo> individuos = new ArrayList<>();
    
    ArrayList<Mutacion> mutaciones = new ArrayList<>();
    ArrayList<Recombinacion> cruces = new ArrayList<>();
    ArrayList<Seleccion> selecciones = new ArrayList<>();
    
    private float probMutation;
    private float probRecombine;
    private int maxIndividuos;
    
    
    public Poblacion(int maxIndividuos,int nClases,int nAtributos,int rangoAtributos,int maxReglasIni,float probMutation,float probRecombine){
        this.maxIndividuos = maxIndividuos;
        this.probMutation = probMutation;
        this.probRecombine = probRecombine;
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
        return this.cruces.get(cruceIndex).recombina(a,b);
   }
   
    public ArrayList<Individuo> seleccionNatural(ArrayList<Individuo> nuevaPoblacion,int[][] entorno){
        int seleccionIndex = UtilesGenetico.randomNumber(this.selecciones.size() - 1);
        return this.selecciones.get(seleccionIndex).selecciona(nuevaPoblacion,this.maxIndividuos,entorno);
   }
   
    public ArrayList<Individuo> seleccionEvolutiva(ArrayList<Individuo> nuevaPoblacion,int[][] entorno){
        int seleccionIndex = UtilesGenetico.randomNumber(this.selecciones.size() - 1);
        Float nRecombinaciones;
        nRecombinaciones = this.probRecombine * this.individuos.size();
        return this.selecciones.get(seleccionIndex).selecciona(nuevaPoblacion,nRecombinaciones.intValue(),entorno);
   }
    
    public ArrayList<Individuo> evolve(){
        
        
        return null;
    }
    
    public ArrayList<Individuo> evolve(){
        
        
        return null;
    }
    
    
    public float getProbMutation() {
        return probMutation;
    }

    public void setProbMutation(float probMutation) {
        this.probMutation = probMutation;
    }

    public float getProbRecombine() {
        return probRecombine;
    }

    public void setProbRecombine(float probRecombine) {
        this.probRecombine = probRecombine;
    }
    
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

    public int getMaxIndividuos() {
        return maxIndividuos;
    }

    public void setMaxIndividuos(int maxIndividuos) {
        this.maxIndividuos = maxIndividuos;
    }
}
