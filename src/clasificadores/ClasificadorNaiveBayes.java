/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clasificadores;

import datos.Datos;
import datos.Diccionario;
import java.util.ArrayList;

/**
 * Clasificador mediante el método de Naive Bayes (Bayes ingenuo).
 *
 * @author Adrián Lorenzo Mateo
 */
public class ClasificadorNaiveBayes extends Clasificador {

    private ArrayList<Double> apriori = new ArrayList<>();
    private double aposteriori[][];

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

            apriori.add(aprioriCounter);

        }
    }
    /**
     * Obtiene las probabilidades condicionadas de cada uno de los campos.
     * 
     * @param datosTrain 
     */
    private void getProbCondicionada(Datos datosTrain) {

        int numCategorias = Diccionario.getInstance().getDiccionario().size();
        int numInstancias = datosTrain.getDatos().length;

        aposteriori = new double[numCategorias][datosTrain.getCategorias().size()];

        /*obtiene el indice de la clase dentro de la matriz de datos*/
        int indexClass = datosTrain.getCategorias().indexOf("Class");

        for (Integer clase : Diccionario.getInstance().getDiccionario().values()) {

            for (double[] dato : datosTrain.getDatos()) {

                if (dato[indexClass] == clase) {
                    double[] rowData = dato;
                }
            }

        }

    }

    @Override
    public ArrayList<Integer> clasifica(Datos datosTest) {

        return null;
    }

}
