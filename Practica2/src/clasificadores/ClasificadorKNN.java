/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clasificadores;

import datos.Datos;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;


/**
 * 
 * @author Adrian Lorenzo Mateo
 * @author Andres Ruiz Carrasco
 */
public class ClasificadorKNN extends Clasificador{

    
    private double datosEntrenamiento[][];
    private int kNN = 5;    

    
    
    public void setkNN(int kNN) {
        this.kNN = kNN;
    }

    /**
     * 
     * @param datosTrain 
     */
    @Override
    public void entrenamiento(Datos datosTrain) {
    
        datosEntrenamiento = datosTrain.getDatos();
    
    }
    /**
     * 
     * @param datosTest
     * @return 
     */
    @Override
    public ArrayList<Integer> clasifica(Datos datosTest) {

        ArrayList<Integer> res = new ArrayList<>();
        
        for(double instanciaTest[] : datosTest.getDatos()){
            
            ArrayList<Double> distancias = new ArrayList<>();
            
            for(double instanciaTrain[] : datosEntrenamiento){
                
                double distanciaEuclidea = 0;
                
                for(int i=0; i<instanciaTest.length; i++){
                
                    distanciaEuclidea += Math.pow((instanciaTest[i] - instanciaTrain[i]),2);
                
                }
                distanciaEuclidea = Math.sqrt(distanciaEuclidea);
                distancias.add(distanciaEuclidea);
            }
            ArrayList<Double> distanciasAuxiliar = new ArrayList<>(distancias);
            Collections.sort(distanciasAuxiliar);
            
            HashMap<Double,Double> mapaKNN = new HashMap<>();
            
            for(int i=0; i<kNN; i++){
                
                /*obtiene el indice en la matrix de los knn con menor distancia*/
                int indice = distancias.indexOf(distanciasAuxiliar.get(i));
                double clase = datosEntrenamiento[indice][datosTest.getCategorias().indexOf("class")];
                
                if(mapaKNN.containsKey(clase))
                    mapaKNN.put(clase, mapaKNN.get(clase)+1);
              
                else
                    mapaKNN.put(clase, 1.0);

            }
            for (Double clase : mapaKNN.keySet()) {
                
                mapaKNN.put(clase, mapaKNN.get(clase)/(double)kNN);
                
            }
            
            
                

        }
        
        return res;
    
    }
    
}
