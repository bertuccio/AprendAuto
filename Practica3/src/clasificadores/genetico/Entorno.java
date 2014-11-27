package clasificadores.genetico;

import clasificadores.genetico.evaluacion.Evaluador;
import clasificadores.genetico.poblacion.Poblacion;
import clasificadores.genetico.poblacion.individuo.Individuo;
import clasificadores.genetico.poblacion.individuo.mutacion.Mutacion;
import clasificadores.genetico.recombinacion.Recombinacion;
import clasificadores.genetico.selecciones.Seleccion;
import datos.Datos;
import java.util.ArrayList;
import java.util.Iterator;

/**
 *
 * @author Andres Ruiz Carrasco
 */
public class Entorno {
    
    private Poblacion poblacion;
    private ArrayList<MuestraGenetica> entornoEvolutivo;
    private ArrayList<MuestraGenetica> entornoTesteo;
    private Individuo king;
    
    /*Cuando metemos estos*/
    ArrayList<Mutacion> mutaciones = new ArrayList<>();
    ArrayList<Recombinacion> cruces = new ArrayList<>();
    ArrayList<Seleccion> selecciones = new ArrayList<>();
    Evaluador evaluador;
    
    private double probMutation;
    private double probRecombine;
    
    public Entorno(int maxIndividuos,int nClases,int nAtributos,int rangoAtributos,
            int maxReglasIni,double probMutation,double probRecombine,Evaluador evaluator,
            Datos entornoEvolutivo, Datos entornoTesteo){
        
        this.entornoEvolutivo = new ArrayList<>();
        if (entornoEvolutivo == null)
            return;
        else if (entornoTesteo == null){
            for (double[] muestra : entornoEvolutivo.getDatos())
                this.entornoEvolutivo.add(new MuestraGenetica(muestra));
            this.entornoTesteo=null;
        }else{
            for (double[] muestra : entornoEvolutivo.getDatos())
                this.entornoEvolutivo.add(new MuestraGenetica(muestra));
            
            for (double[] muestra : entornoTesteo.getDatos())
                this.entornoTesteo.add(new MuestraGenetica(muestra));
        }
            
        this.probMutation = probMutation;
        this.probRecombine = probRecombine;
        this.evaluador = evaluator;
        this.poblacion = new Poblacion(maxIndividuos, nClases, nAtributos, rangoAtributos, maxReglasIni,evaluator);
    }
    
    
    /*Funcion que calcula los scores de los individuos que lo necesiten (aquellos marcados como mutados)*/
    /*Siempre se ejecuta sobre el entorno de evolucion*/
    public void scoring(){
        this.poblacion.scoring(this.evaluador,this.entornoEvolutivo);
    }
    
    /*Funcion que realiza los siguientes pasos:
        0) Calcula el score
        1) Selecciona un grupo de individuos a cruzar
        2) Recombina estos individuos
        3) Añade los nuevos individuos a la poblacion
    */
    public void evolve(){
        
        /*Actualizamos los scores de cada individuo*/
        this.scoring();
        
        /*Obtenemos los individuos selecionados para el cruce*/
        ArrayList<Integer> index = this.selecciones.get(UtilesGenetico.randomNumber(
                this.selecciones.size())).selecciona(
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
                        this.cruces.size())).recombina(
                                this.poblacion.getIndividuos()[a],
                                this.poblacion.getIndividuos()[b]);
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
            this.mutaciones.get(UtilesGenetico.randomNumber(this.mutaciones.size())).muta(this.poblacion.getIndividuos()[UtilesGenetico.randomNumber(this.poblacion.getnIndividuos()-1)]);
        }
    }
    
    /*
    Fucion que realiza los siguientes pasos:
        1) Evoluciona la poblacion
        2) Muta la poblacion
        3) Selecciona la nueva poblacion
    */
    public void epoch(){
        Individuo[] newPoblation = new Individuo[this.poblacion.MAX_INDIVIDUOS];
        this.evolve();
        this.mutate();
        this.scoring();
        
        /*Obtenemos los individuos selecionados para sobrevivir*/
        ArrayList<Integer> index = this.selecciones.get(UtilesGenetico.randomNumber(
                this.selecciones.size())).selecciona(
                        poblacion, 
                        new Double(this.poblacion.getMaxIndividuos()).intValue());
        
        int j=0;
        for (Integer i : index){
            newPoblation[j] = this.poblacion.getIndividuos()[i];
            j++;
        }
        
        this.poblacion.setIndividuos(newPoblation);
        this.poblacion.setSumScores(0);
        this.poblacion.setnIndividuos(j);
        
        this.scoring();
        this.poblacion.sort();
        this.king = this.poblacion.getIndividuos()[0];
        this.getClass();
    }
    
    /*
    Funcion que prueba el individuo mejor de la poblacion
    Retorno: Devuelve un array list con las predicciones del individuo mejor
    */
    public ArrayList<Integer> test(){
        ArrayList<Integer> results = new ArrayList();
        
        this.scoring();
        this.poblacion.sort();
        
        Individuo ganador = this.poblacion.getIndividuos()[0];
        
        int i = 0;
        for(MuestraGenetica muestra : entornoTesteo){
            results.add(i,ganador.evaluate(muestra.getArgs()));
        }
        return results;
    }
    
    
    
    
    
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

    public ArrayList<MuestraGenetica> getEntornoEvolutivo() {
        return entornoEvolutivo;
    }

    public void setEntornoEvolutivo(Datos entornoEvolutivo) {
        for (double[] muestra : entornoEvolutivo.getDatos())
            this.entornoEvolutivo.add(new MuestraGenetica(muestra));
    }

    public ArrayList<MuestraGenetica> getEntornoTesteo() {
        return entornoTesteo;
    }

    public void setEntornoTesteo(Datos entornoTesteo) {
        if (this.entornoTesteo == null) this.entornoTesteo = new ArrayList<>();
        for (double[] muestra : entornoTesteo.getDatos())
            this.entornoTesteo.add(new MuestraGenetica(muestra));
    }

    public Evaluador getEvaluador() {
        return evaluador;
    }

    public void setEvaluador(Evaluador evaluador) {
        this.evaluador = evaluador;
    }


    
    
}

