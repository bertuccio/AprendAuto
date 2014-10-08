package clasificadores;

import datos.Datos;
import datos.Diccionario;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;

/**
 * Clasificador mediante el método de Naive Bayes (Bayes ingenuo).
 *
 * @author Adrián Lorenzo Mateo
 * @author Andres Ruiz Carrasco
 */
public class ClasificadorNaiveBayes extends Clasificador {
    
    private ArrayList<Double> probApriori = new ArrayList<>();
    private ArrayList<ArrayList<Object>> probCond = new ArrayList<>();
    
    private boolean LAPLACE_FLAG = false;
    private boolean DEBUG_FLAG = false;

    public void setLAPLACE_FLAG(boolean LAPLACE_FLAG) {
        this.LAPLACE_FLAG = LAPLACE_FLAG;
    }

    public void setDEBUG_FLAG(boolean DEBUG_FLAG) {
        this.DEBUG_FLAG = DEBUG_FLAG;
    }   
    

    @Override
    public void entrenamiento(Datos datosTrain) {
        
        getProbAPriori(datosTrain);
        getProbCondicionada(datosTrain);
 
    }
    
 
    /**
     *
     * Obtiene las probabilidades a priori de las clases del conjunto de
     * entrenamiento.
     *
     * @param datosTrain conjunto de datos de los que obtiene las probabilidades a priori
     * @see datos.Datos
     */
    private void getProbAPriori(Datos datosTrain) {
        
        int totalClases = Diccionario.getInstance().getDiccionarioClases().size();
        int numTuplas = datosTrain.getDatos().length;
        
        /*obtiene el indice de la clase dentro de la matriz de datos*/
        int indexClass = datosTrain.getCategorias().indexOf("class");
        
        /*Itera sobre las clases del conjunto de datos*/
        for (int numClass = 0; numClass < totalClases; numClass++) {
            
            double aprioriCounter = 0;
            
            /*Itera sobre cada una de las filas del conjunto de datos*/
            for (int indexTuplas = 0; indexTuplas < numTuplas; indexTuplas++) {
                
                double classElement = datosTrain.getDatos()[indexTuplas][indexClass];
                
                if (numClass == classElement)
                    aprioriCounter++;
            }
            
            
            /*Se obtinen todas las probabilidades de clase*/
            aprioriCounter = aprioriCounter / numTuplas;
            probApriori.add(aprioriCounter);
            
            if(DEBUG_FLAG){
                String clase = Diccionario.getKeyByValue(Diccionario.getInstance().getDiccionarioClases(),numClass);
                System.out.println("P(Class="+clase+") = "+probApriori.get(numClass));
            }
            
        }
    }
    
    /**
     * Obtiene las probabilidades condicionadas de cada uno de los campos. de
     * con la siguiente estructura
     * Clases->Atrubuto->valor->probabilidad
     * 
     * @param datosTrain conjunto de datos de los que obtiene las probabilidades condicionadas
     * @see datos.Datos
     */
    private void getProbCondicionada(Datos datosTrain) {
        
        
        int numClases = Diccionario.getInstance().getDiccionarioClases().size();
        int numCategorias = datosTrain.getCategorias().size()-1;
        
        /*Variables temporales*/
        double varianza_media[];
        double contMedia;
        double acumMedia;
        double contVarianza;
        
        /*obtiene el indice de la clase dentro de la matriz de datos*/
        int indexClass = datosTrain.getCategorias().indexOf("class");
        
        /*Por cada clase*/
        for (int clase=0; clase<numClases; clase++) {
            
            ArrayList<Object> prob = new ArrayList<>();
            
            /*Por cada atributo*/
            for(int i=0; i<numCategorias; i++){
                
                if(datosTrain.getTipoAtributos().get(i).name().equals("Nominal")){
                    
                    HashMap<Double,Double> map = new HashMap<>();
                    
                    /*Por cada muestra*/
                    for (double[] dato : datosTrain.getDatos()) {                    
                        if (dato[indexClass] == clase) {                                                
                            if(!map.containsKey(dato[i]))
                                    map.put(dato[i], 1.0);
                            else{
                                Double actualizacion = map.get(dato[i]);
                                map.put(dato[i], actualizacion+1);
                            }
                        }
                    }                   

                    double numElementClase = 0;                
                    for(Double value : map.values())
                        numElementClase +=value;                   

                    /*Aqui aplicamos correccion de Laplace antes de normalizar*/
                    
                     
                    for (Double key : map.keySet()) {
                        
                        if (this.LAPLACE_FLAG) 
                            map.put(key, (double)(map.get(key) + 1) / (numElementClase + map.keySet().size()));
                        else 
                            map.put(key, map.get(key) / (numElementClase));
                     
                        if(DEBUG_FLAG){
                            String claseName = Diccionario.getKeyByValue(Diccionario.getInstance().
                                    getDiccionarioClases(), clase);
                            String atributoNominalName = Diccionario.getKeyByValue(Diccionario.getInstance().
                                    getDiccionarioAtributos(), key.intValue());

                            System.out.println("P(" + datosTrain.getCategorias().get(i) + 
                                    "=" + atributoNominalName + "| Class=" + claseName + ") = " + map.get(key));
                    
                        }
                    }
                    
                    prob.add(map);  
                    
                }
                else{
                    varianza_media = new double[2];
                    /*Por cada muestra*/
                    contMedia = 0;
                    acumMedia = 0;
                    contVarianza = 0;
                    for (double[] dato : datosTrain.getDatos()) {                    
                        if (dato[indexClass] == clase) {   
                            contMedia ++; 
                            acumMedia += dato[i];
                        }
                    }
                    varianza_media[1] = acumMedia/(double)contMedia;
                    for (double[] dato : datosTrain.getDatos()) {                    
                        if (dato[indexClass] == clase) {   
                            contVarianza += (dato[i] - varianza_media[1]) * (dato[i] - varianza_media[1]);  
                        }
                    }
                    varianza_media[0] = contVarianza/(double)contMedia;
                    prob.add(varianza_media);
                    if(DEBUG_FLAG){
                        String atributoContinuoName = datosTrain.getCategorias().get(i);
                        System.out.println("Media: "+varianza_media[1]+" | Varianza: "+varianza_media[0]+" | "+atributoContinuoName+"[Clase "+clase+"]");
                    }
                    
                }
            }
            probCond.add(prob);
        }
    }
    
    /**
     * Funcion que calcula la el valor de la distribucion normal estimada.
     * 
     * @param valor dato a obtener la probabilidad
     * @param media media de la coleccion 
     * @param varianza varianza de la coleccion
     * @return dobule probabilidad obtenida
     */
    private double calculaGaussian(double valor, double media,double varianza){
        return (double) ((1.0)*Math.pow(Math.E,(-(Math.pow(valor-media,2))/(2*varianza))))/(Math.sqrt(varianza)*Math.sqrt(2*Math.PI));
    }

    /**
     * Funcion que clasifica un conjunto de datos de test
     * 
     * @param datosTest conjunto de datos a clasificar
     * @return Lista de clases predichas
     * @see datos.Datos
     */
    @Override
    public ArrayList<Integer> clasifica(Datos datosTest) {
        ArrayList<Integer> res = new ArrayList<>();
        
        /*Una instancia por sample <Clase,Probabilidad>*/
        HashMap<Integer,Double> sampleProbs;
        double bayes;
        int numClases = Diccionario.getInstance().getDiccionarioClases().size();
        /*numero de categorias, sin contar con la clase*/
        int numCategorias = datosTest.getCategorias().size()-1;
    
        double matrizSamples[][] = datosTest.getDatos();
        
        /*itera sobre el conjunto de test*/
        for (double[] sample : matrizSamples){
            
            sampleProbs = new HashMap<>();
            
            /*itera sobre el numero de clases*/
            for (int j = 0;j < numClases;j++){
                
                sampleProbs.put(j,this.probApriori.get(j));
                
                /*itera sobre las categorias de cada elemento del conjunto
                    de test*/
                for (int i = 0;i < numCategorias;i++){
                    
                    
                    if(datosTest.getTipoAtributos().get(i).name().equals("Nominal"))
                    {
                        if(((HashMap<Double,Double>)probCond.get(j).get(i)).get(sample[i]) !=null){
                            /**
                             * bayes = p(Clase)*p(Categoria nº 1 | Clase)*p(Categoria nº 2 | Clase)*
                             *  *p(Categoria nº i | Clase)
                             */
                            bayes = sampleProbs.get(j)*((HashMap<Double,Double>)probCond.get(j).get(i)).get(sample[i]);
                        }
                        else
                            bayes = sampleProbs.get(j)*0.01;
                        
                    }  
                    else{
                        double gaussian = this.calculaGaussian(sample[i], ((double[])probCond.get(j).get(i))[1], ((double[])probCond.get(j).get(i))[0]);
                        //System.out.println("GUASSIAN ="+gaussian);
                        bayes = sampleProbs.get(j)*gaussian;
                    }
                    sampleProbs.put(j, bayes);
                }
            }
            
            /**
             * obtiene la mayor probabilidad de todas. Devuelve la key
             * que corresponde al identificador de la clase con mayor prob.
             */
            Integer maxKey = null; 
            Double maxValue = -1.0; 
            if(!sampleProbs.entrySet().isEmpty()){
                for(Entry<Integer,Double> entry : sampleProbs.entrySet()) { 
                    if(entry.getValue().isNaN())
                        entry. setValue(0.0);
                    if(entry.getValue().isInfinite())
                        entry.setValue(Double.MAX_VALUE);
                    if(entry.getValue() >= maxValue) { 
                        maxValue = entry.getValue(); 
                        maxKey = entry.getKey(); 
                    } 
                }
                res.add(maxKey);  
            }
        }
        /*El retorno es una lista de Clases de las muestras de test en orden*/
        return res;
    }
}


 