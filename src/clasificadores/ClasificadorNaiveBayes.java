/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clasificadores;

import datos.Datos;
import datos.Diccionario;
import java.util.ArrayList;
import java.util.Arrays;
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
            //Corrección de Laplace
            if (aprioriCounter == 0) {
                aprioriCounter++;
            }

            aprioriCounter = aprioriCounter / numTuplas;

            probApriori.add(aprioriCounter);

        }
    }
    /**
     * Obtiene las probabilidades condicionadas de cada uno de los campos.
     * 
     * @param datosTrain 
     */
    private void getProbCondicionada(Datos datosTrain) {

        int numClases = Diccionario.getInstance().getDiccionario().size();
        int numCategorias = datosTrain.getCategorias().size()-1;
        
        /*obtiene el indice de la clase dentro de la matriz de datos*/
        int indexClass = datosTrain.getCategorias().indexOf("Class");
        
        for (int clase=0; clase<numClases; clase++) {

            int counterNumClase = 0;
            
            ArrayList<HashMap<Double,Double>> prob = new ArrayList<>();
            
            for(int i=0; i<numCategorias; i++){
                
                HashMap<Double,Double> map = new HashMap<>();
                
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
                    
                for(Double key : map.keySet())
                    map.put(key, map.get(key)/numElementClase);
                
                prob.add(map);
                
            }
            probCond.add(prob);
        }
    }

    @Override
    public ArrayList<Integer> clasifica(Datos datosTest) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
//            if(counterNumClase != 0){
//                
//                for(int i=0; i<numCategorias; i++){
//                        
//                        probCond[clase][i] /= counterNumClase;
//                }
//                
//            }
          

 