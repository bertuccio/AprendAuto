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

/**
 * Clasificador mediante el método de Naive Bayes (Bayes ingenuo).
 *
 * @author Adrián Lorenzo Mateo
 */
public class ClasificadorNaiveBayes extends Clasificador {

    private ArrayList<Double> probApriori = new ArrayList<>();
    private double probCond[][];

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
        probCond = new double[numClases][numCategorias];
        
        /*obtiene el indice de la clase dentro de la matriz de datos*/
        int indexClass = datosTrain.getCategorias().indexOf("Class");
        
        
        /**esqueleto bien, contenido mal**/
        for (Integer clase : Diccionario.getInstance().getDiccionario().values()) {

            int counterNumClase = 0;
            
            for (double[] dato : datosTrain.getDatos()) {

                if (dato[indexClass] == clase) {
                    
                    counterNumClase++;
                    for(int i=0; i<numCategorias; i++){
                        
                        probCond[clase][i] += dato[i];
                    }
                    
                }
            }
            if(counterNumClase != 0){
                
                for(int i=0; i<numCategorias; i++){
                        
                        probCond[clase][i] /= counterNumClase;
                }
                
            }
            

        }

    }

    @Override
    public ArrayList<Integer> clasifica(Datos datosTest) {

        return null;
    }

}
