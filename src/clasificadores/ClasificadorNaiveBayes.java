/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
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
 */
public class ClasificadorNaiveBayes extends Clasificador {
    private boolean LAPLACE_FLAG = true;

    public boolean isLAPLACE_FLAG() {
        return LAPLACE_FLAG;
    }

    public void setLAPLACE_FLAG(boolean LAPLACE_FLAG) {
        this.LAPLACE_FLAG = LAPLACE_FLAG;
    }

    private ArrayList<Double> probApriori = new ArrayList<>();
    private ArrayList<ArrayList<Object>> probCond = new ArrayList<>();

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
     * @param datosTrain
     */
    private void getProbAPriori(Datos datosTrain) {
        
        int totalClases = Diccionario.getInstance().getDiccionarioClases().size();
        int numTuplas = datosTrain.getDatos().length;
        
        /*obtiene el indice de la clase dentro de la matriz de datos*/
        int indexClass = datosTrain.getCategorias().indexOf("Class");
        
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
        }
    }
    
    /**
     * Obtiene las probabilidades condicionadas de cada uno de los campos. de
     * con la siguiente estructura
     * Clases->Atrubuto->valor->probabilidad
     * 
     * @param datosTrain 
     */
    private void getProbCondicionada(Datos datosTrain) {
        
        
        int numClases = Diccionario.getInstance().getDiccionarioClases().size();
        int numCategorias = datosTrain.getCategorias().size()-1;
        
        /*Variables temporales*/
        double varianza_media[] = null;
        double contMedia = 0;
        double acumMedia = 0;
        double contVarianza = 0;
        
        /*obtiene el indice de la clase dentro de la matriz de datos*/
        int indexClass = datosTrain.getCategorias().indexOf("Class");
        
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

                    /*Aqui aplicamos correcion de Laplace antes de normalizar*/
                    if(this.LAPLACE_FLAG){
                        for(Double key : map.keySet())
                            map.put(key, map.get(key)+1/(numElementClase+map.keySet().size()));                
                    }else{
                        for(Double key : map.keySet())
                            map.put(key, map.get(key)/(numElementClase));                
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
                            contVarianza += (- varianza_media[1] + dato[i]) * (- varianza_media[1] + dato[i]);  
                        }
                    }
                    varianza_media[0] = contVarianza/(double)contMedia;
                    prob.add(varianza_media);
                }
            }
            probCond.add(prob);
        }
    }
    
    private double calculaGaussian(double valor, double media,double varianza){
        return ((1.0/(Math.sqrt(varianza)*Math.sqrt(2*Math.PI)))*Math.pow(Math.E,(-((valor-media)*(valor-media)/(2*varianza)))));
    }

    @Override
    public ArrayList<Integer> clasifica(Datos datosTest) {
        //double counter;
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
                        /**
                         * bayes = p(Clase)*p(Categoria nº 1 | Clase)*p(Categoria nº 2 | Clase)*
                         *  *p(Categoria nº i | Clase)
                         */
                        bayes = sampleProbs.get(j)*((HashMap<Double,Double>)probCond.get(j).get(i)).get(sample[i]);
                        
                    } 
                    else{
                        double gaussian = this.calculaGaussian(sample[i], ((double[])probCond.get(j).get(i))[1], ((double[])probCond.get(j).get(i))[0]);
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
            Double maxValue = Double.MIN_VALUE; 
            
            for(Entry<Integer,Double> entry : sampleProbs.entrySet()) { 
                
                if(entry.getValue() > maxValue) { 
                    
                    maxValue = entry.getValue(); 
                    maxKey = entry.getKey(); 
                } 
            }
            res.add(maxKey);  
        }
        /*El retorno es una lista de Clases de las muestras de test en orden*/
        return res;
    }
}


 