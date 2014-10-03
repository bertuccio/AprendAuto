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

/**
 * Clasificador mediante el método de Naive Bayes (Bayes ingenuo).
 *
 * @author Adrián Lorenzo Mateo
 */
public class ClasificadorNaiveBayes extends Clasificador {

    private ArrayList<Double> probApriori = new ArrayList<>();
    private ArrayList<ArrayList<HashMap<Double,Double>>> probCond = new ArrayList<>();

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
        int numCategorias = Diccionario.getInstance().getDiccionario().size();
        int numTuplas = datosTrain.getDatos().length;
        /*obtiene el indice de la clase dentro de la matriz de datos*/
        int indexClass = datosTrain.getCategorias().indexOf("Class");
        /*Itera sobre las clases del conjunto de datos*/
        for (int numClass = 0; numClass < numCategorias; numClass++) {
            double aprioriCounter = 0;
            /*Itera sobre cada una de las filas del conjunto de datos*/
            for (int indexTuplas = 0; indexTuplas < numTuplas; indexTuplas++) {
                double classElement = datosTrain.getDatos()[indexTuplas][indexClass];
                if (numClass == classElement)
                    aprioriCounter++;
            }
            
            /* La correccion de Laplace no va aki :P
            //Corrección de Laplace
            if (aprioriCounter == 0) {
                aprioriCounter++;
            }
            */
            
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
        int numClases = Diccionario.getInstance().getDiccionario().size();
        int numCategorias = datosTrain.getCategorias().size()-1;    
        
        /*obtiene el indice de la clase dentro de la matriz de datos*/
        int indexClass = datosTrain.getCategorias().indexOf("Class");
        
        /*Por cada clase*/
        for (int clase=0; clase<numClases; clase++) {
            ArrayList<HashMap<Double,Double>> prob = new ArrayList<>();
            /*Por cada atributo*/
            for(int i=0; i<numCategorias; i++){
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
                for(Double key : map.keySet())
                    map.put(key, map.get(key)+1/numElementClase);                
                
                prob.add(map);                
            }
            probCond.add(prob);
        }
    }

    @Override
    public ArrayList<Integer> clasifica(Datos datosTest) {
        //double counter;
        ArrayList<Integer> res = new ArrayList<>();
        
        /*Una instancia por sample <Clase,Probabilidad>*/
        HashMap<Integer,Double> sampleProbs;
        
        int numClases = Diccionario.getInstance().getDiccionario().size();
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
                    
                    
                    if(probCond.get(j).get(i).get(sample[i]) != null)
                    {
                        /**
                         * bayes = p(Clase)*p(Categoria nº 1 | Clase)*p(Categoria nº 2 | Clase)*
                         *  *p(Categoria nº i | Clase)
                         */
                        double bayes = sampleProbs.get(j)*probCond.get(j).get(i).get(sample[i]);
                        sampleProbs.put(j, bayes);
                    }
                        
                    else
                        sampleProbs.put(j,sampleProbs.get(j)*0.001);  ///AQUI ES LO QUE DIGO ... Meto un alfa pequeño pero esto no es Laplace
                }
            }
            
            /**
             * obtiene la mayor probabilidad de todas. Devuelve la key
             * que corresponde al identificador de la clase con mayor prob.
             */
            Integer maxKey = null; 
            Double maxValue = Double.MIN_VALUE; 
            
            for(HashMap.Entry<Integer,Double> entry : sampleProbs.entrySet()) { 
                
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


 